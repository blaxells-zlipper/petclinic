package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetCountByTypeDTO;
import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

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

        TypeDTO createdType = typeService.create(typeDTO);

        assertNotNull(createdType.getId());

        try {
            TypeDTO persistedType = typeService.findById(createdType.getId());

            assertEquals("ferret", persistedType.getName());
            assertEquals("Domestic ferret", persistedType.getDescription());
            assertTrue(persistedType.getActive());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }
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

        TypeDTO createdType = typeService.create(typeDTO);
        createdType.setDescription("Updated description");
        createdType.setActive(false);

        TypeDTO updatedType = typeService.update(createdType);

        try {
            TypeDTO persistedType = typeService.findById(updatedType.getId());

            assertEquals("Updated description", persistedType.getDescription());
            assertFalse(persistedType.getActive());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindTypeById() {
        try {
            TypeDTO typeDTO = typeService.findById(1);

            assertEquals(1, typeDTO.getId());
            assertEquals("cat", typeDTO.getName());
            assertEquals("Domestic feline", typeDTO.getDescription());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetPetCountByType() {
        List<PetCountByTypeDTO> report = typeService.getPetCountByType();

        PetCountByTypeDTO catReport = report.stream()
                .filter(row -> "cat".equals(row.getTypeName()))
                .findFirst()
                .orElseThrow();

        assertEquals(4L, catReport.getPetCount());
    }

    @Test
    public void testFindActiveTypes() {
        List<TypeDTO> activeTypes = typeService.findActiveTypes();

        assertEquals(7, activeTypes.size());
        assertTrue(activeTypes.stream().allMatch(TypeDTO::getActive));
        assertTrue(activeTypes.stream().noneMatch(type -> "snake".equals(type.getName())));
    }

    @Test
    public void testDeleteType() {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("axolotl")
                .description("Aquatic salamander")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(15)
                .careLevel("high")
                .build();

        TypeDTO createdType = typeService.create(typeDTO);

        try {
            typeService.delete(createdType.getId());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(TypeNotFoundException.class, () -> typeService.findById(createdType.getId()));
    }

    @Test
    public void testGetPetCountByType_ExcludeInactive() {
        List<PetCountByTypeDTO> report = typeService.getPetCountByType();

        assertTrue(report.stream().noneMatch(row -> "snake".equals(row.getTypeName())));
    }
}
