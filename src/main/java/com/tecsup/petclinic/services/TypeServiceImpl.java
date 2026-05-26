package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mappers.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeDTO create(TypeDTO typeDTO) {
        Type createdType = typeRepository.save(typeMapper.mapToEntity(typeDTO));
        return typeMapper.mapToDto(createdType);
    }

    @Override
    public TypeDTO update(TypeDTO typeDTO) {
        Type updatedType = typeRepository.save(typeMapper.mapToEntity(typeDTO));
        return typeMapper.mapToDto(updatedType);
    }

    @Override
    public TypeDTO findById(Integer id) throws TypeNotFoundException {
        Optional<Type> type = typeRepository.findById(id);
        if (!type.isPresent()) {
            throw new TypeNotFoundException("Record not found...!");
        }
        return typeMapper.mapToDto(type.get());
    }
}
