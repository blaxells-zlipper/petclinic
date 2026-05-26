package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mappers.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private TypeMapper typeMapper;

    @InjectMocks
    private TypeServiceImpl typeService;

    @Test
    public void testCreateType() {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        Type typeToSave = Type.builder()
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        Type createdTypeEntity = Type.builder()
                .id(100)
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        TypeDTO createdTypeDTO = TypeDTO.builder()
                .id(100)
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        when(typeMapper.mapToEntity(typeDTO)).thenReturn(typeToSave);
        when(typeRepository.save(typeToSave)).thenReturn(createdTypeEntity);
        when(typeMapper.mapToDto(createdTypeEntity)).thenReturn(createdTypeDTO);

        TypeDTO createdType = typeService.create(typeDTO);

        assertNotNull(createdType.getId());
        assertEquals("ferret", createdType.getName());
        assertEquals("Domestic ferret", createdType.getDescription());
    }

    @Test
    public void testUpdateType() {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("iguana")
                .description("Large lizard")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(12)
                .careLevel("high")
                .build();

        Type createdEntity = Type.builder()
                .id(200)
                .name("iguana")
                .description("Large lizard")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(12)
                .careLevel("high")
                .build();

        TypeDTO createdTypeDTO = TypeDTO.builder()
                .id(200)
                .name("iguana")
                .description("Large lizard")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(12)
                .careLevel("high")
                .build();

        when(typeMapper.mapToEntity(typeDTO)).thenReturn(createdEntity);
        when(typeRepository.save(createdEntity)).thenReturn(createdEntity);
        when(typeMapper.mapToDto(createdEntity)).thenReturn(createdTypeDTO);

        TypeDTO createdType = typeService.create(typeDTO);

        createdType.setName("iguana-updated");
        createdType.setDescription("Updated description");
        createdType.setAverageLifespan(14);

        Type updatedEntity = Type.builder()
                .id(200)
                .name("iguana-updated")
                .description("Updated description")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(14)
                .careLevel("high")
                .build();

        TypeDTO updatedDTO = TypeDTO.builder()
                .id(200)
                .name("iguana-updated")
                .description("Updated description")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(14)
                .careLevel("high")
                .build();

        when(typeMapper.mapToEntity(createdType)).thenReturn(updatedEntity);
        when(typeRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(typeMapper.mapToDto(updatedEntity)).thenReturn(updatedDTO);

        TypeDTO updatedType = typeService.update(createdType);

        assertEquals(createdType.getId(), updatedType.getId());
        assertEquals("iguana-updated", updatedType.getName());
        assertEquals("Updated description", updatedType.getDescription());
        assertEquals(14, updatedType.getAverageLifespan());
    }

    @Test
    public void testFindTypeById() {
        Integer existingId = 1;
        TypeDTO typeDTO = null;

        Type existingEntity = Type.builder()
                .id(1)
                .name("cat")
                .description("Domestic feline")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(15)
                .careLevel("medium")
                .build();

        TypeDTO existingTypeDTO = TypeDTO.builder()
                .id(1)
                .name("cat")
                .description("Domestic feline")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(15)
                .careLevel("medium")
                .build();

        when(typeRepository.findById(existingId)).thenReturn(Optional.of(existingEntity));
        when(typeMapper.mapToDto(existingEntity)).thenReturn(existingTypeDTO);

        try {
            typeDTO = typeService.findById(existingId);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(typeDTO);
        assertEquals(existingId, typeDTO.getId());
        assertEquals("cat", typeDTO.getName());
    }
}
