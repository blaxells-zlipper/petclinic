package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;

public interface TypeService {

    TypeDTO create(TypeDTO typeDTO);

    TypeDTO update(TypeDTO typeDTO);

    TypeDTO findById(Integer id) throws TypeNotFoundException;
}
