package com.tecsup.petclinic.webs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindTypeById_NotFound_Web() throws Exception {
        mockMvc.perform(get("/types/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAllTypes_Web() throws Exception {
        mockMvc.perform(get("/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("cat")));
    }
}
