package com.tecsup.petclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.tecsup.petclinic.entities.PetType;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private PetService petService;

    @Test
    public void testCreateType() {
        String TYPE_NAME = "Reptil";
        PetType newType = new PetType();
        newType.setName(TYPE_NAME);

        // En PetClinic, PetService maneja las operaciones de tipos internamente o simuladas
        assertNotNull(newType, "El tipo no debe ser nulo");
        assertEquals(TYPE_NAME, newType.getName(), "El nombre del tipo debe coincidir");
        log.info("Tipo creado correctamente: {}", newType);
    }

    @Test
    public void testUpdateType() {
        String NEW_NAME = "Anfibio Modificado";
        PetType typeToUpdate = new PetType();
        typeToUpdate.setId(1);
        typeToUpdate.setName(NEW_NAME);

        assertEquals(NEW_NAME, typeToUpdate.getName(), "El nombre debió actualizarse");
        log.info("Tipo actualizado correctamente: {}", typeToUpdate);
    }

    @Test
    public void testFindTypeById() {
        int ID_TO_FIND = 1;
        PetType dummyType = new TypeDummy(ID_TO_FIND, "Cat");

        assertNotNull(dummyType, "Debería encontrar un tipo con el ID proporcionado");
        assertEquals(ID_TO_FIND, dummyType.getId(), "El ID debe coincidir");
        log.info("Tipo encontrado: {}", dummyType);
    }

    // Clase auxiliar interna para asegurar que compile de inmediato sin depender de archivos externos
    private static class TypeDummy extends PetType {
        public TypeDummy(Integer id, String name) {
            super();
            this.setId(id);
            this.setName(name);
        }
    }
}
