package com.example.calendar.DTOs;

import com.example.calendar.entities.Structures;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructuresMapper {

    StructureDto toDto(Structures structures);

    Structures toEntity(StructureDto structure);


}
