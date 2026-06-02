package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetCountByTypeDTO;
import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mappers.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void delete(Integer id) throws TypeNotFoundException {
        TypeDTO typeDTO = findById(id);
        typeRepository.delete(typeMapper.mapToEntity(typeDTO));
    }

    @Override
    public TypeDTO findById(Integer id) throws TypeNotFoundException {
        Optional<Type> type = typeRepository.findById(id);
        if (!type.isPresent()) {
            throw new TypeNotFoundException("Record not found...!");
        }
        return typeMapper.mapToDto(type.get());
    }

    @Override
    public List<TypeDTO> findAll() {
        return typeRepository.findAll()
                .stream()
                .map(typeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TypeDTO> findActiveTypes() {
        return typeRepository.findByActiveTrue()
                .stream()
                .map(typeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetCountByTypeDTO> getPetCountByType() {
        List<Object[]> rows = typeRepository.getPetCountByType();
        List<PetCountByTypeDTO> report = new ArrayList<>();

        for (Object[] row : rows) {
            report.add(PetCountByTypeDTO.builder()
                    .typeName((String) row[0])
                    .petCount(((Number) row[1]).longValue())
                    .build());
        }

        return report;
    }
}
