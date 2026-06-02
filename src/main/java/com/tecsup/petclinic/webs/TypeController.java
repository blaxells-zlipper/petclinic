package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.services.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List<TypeDTO> findAll() {
        return typeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(typeService.findById(id));
        } catch (TypeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
