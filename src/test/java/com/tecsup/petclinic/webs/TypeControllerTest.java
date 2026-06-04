package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.TypeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAllTypes() throws Exception {
        mockMvc.perform(get("/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].name", is("cat")));
    }

    @Test
    public void testFindTypeOK() throws Exception {
        mockMvc.perform(get("/types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("cat")));
    }

    @Test
    @Test
    public void testFindTypeKO() throws Exception {
        // 1. Simulamos una petición GET a una ruta con un ID que NO existe (666)
        mockMvc.perform(get("/types/666"))
                // 2. Verificamos que el sistema responda con un estado 404 (Not Found)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateType() throws Exception {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(typeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("ferret")));
    }

    @Test
    public void testGetPetCountByType() throws Exception {
        mockMvc.perform(get("/types/report/pet-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeName", is("cat")))
                .andExpect(jsonPath("$[0].petCount", is(4)));
    }

    @Test
    public void testDeleteType() throws Exception {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("axolotl")
                .description("Aquatic salamander")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(15)
                .careLevel("high")
                .build();

        MvcResult result = mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(typeDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode createdType = objectMapper.readTree(result.getResponse().getContentAsString());

        mockMvc.perform(delete("/types/" + createdType.get("id").asInt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindActiveTypes() throws Exception {
        mockMvc.perform(get("/types").param("active", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[*].active", everyItem(is(true))))
                .andExpect(jsonPath("$[*].name", not(hasItem("snake"))));
    }

    @Test
    public void testGetPetCountByType_ExcludeInactive() throws Exception {
        mockMvc.perform(get("/types/report/pet-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].typeName", not(hasItem("snake"))));
    }

    @Test
    public void testDeleteTypeKO() throws Exception {
        mockMvc.perform(delete("/types/1000"))
                .andExpect(status().isNotFound());
    }
}
