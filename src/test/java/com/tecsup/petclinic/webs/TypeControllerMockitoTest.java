package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.services.TypeService;
import com.tecsup.petclinic.util.TObjectCreator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TypeControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TypeService typeService;

    @Test
    public void testFindAllTypes() throws Exception {

        Mockito.when(typeService.findAll()).thenReturn(TObjectCreator.getTypes());

        mockMvc.perform(get("/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].name", is("cat")));
    }

    @Test
    public void testFindTypeOK() throws Exception {
        Mockito.when(typeService.findById(1)).thenReturn(TObjectCreator.getTypeCat());

        mockMvc.perform(get("/types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("cat")));
    }

    @Test
    public void testFindTypeKO() throws Exception {
        Mockito.when(typeService.findById(666)).thenThrow(new TypeNotFoundException("Record not found...!"));

        mockMvc.perform(get("/types/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateType() throws Exception {

        TypeDTO typeDTO = TObjectCreator.newTypeDTO();
        TypeDTO createdTypeDTO = TObjectCreator.newTypeDTOCreated();


        Mockito.when(typeService.create(any(TypeDTO.class))).thenReturn(createdTypeDTO);

        mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(typeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1000)))
                .andExpect(jsonPath("$.name", is("ferret")));
    }

    @Test
    public void testGetPetCountByType() throws Exception {
        Mockito.when(typeService.getPetCountByType()).thenReturn(TObjectCreator.getPetCountByType());

        mockMvc.perform(get("/types/report/pet-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeName", is("cat")))
                .andExpect(jsonPath("$[0].petCount", is(4)));
    }


    @Test
    public void testDeleteType() throws Exception {
        TypeDTO createdTypeDTO = TObjectCreator.newTypeDTOCreatedForDelete();

        Mockito.when(typeService.create(any(TypeDTO.class))).thenReturn(createdTypeDTO);
        Mockito.doNothing().when(typeService).delete(createdTypeDTO.getId());

        mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TObjectCreator.newTypeDTOForDelete())))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/types/" + createdTypeDTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindActiveTypes() throws Exception {
        Mockito.when(typeService.findActiveTypes()).thenReturn(TObjectCreator.getActiveTypes());

        mockMvc.perform(get("/types").param("active", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[*].active", everyItem(is(true))))
                .andExpect(jsonPath("$[*].name", not(hasItem("snake"))));
    }

    @Test
    public void testGetPetCountByType_ExcludeInactive() throws Exception {
        Mockito.when(typeService.getPetCountByType()).thenReturn(TObjectCreator.getPetCountByType());

        mockMvc.perform(get("/types/report/pet-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].typeName", not(hasItem("snake"))));
    }

    @Test
    public void testDeleteTypeKO() throws Exception {
        Mockito.doThrow(new TypeNotFoundException("Record not found...!")).when(typeService).delete(eq(1000));

        mockMvc.perform(delete("/types/1000"))
                .andExpect(status().isNotFound());
    }
}
