package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeDTO mapToDto(Type type);

    Type mapToEntity(TypeDTO typeDTO);
}
