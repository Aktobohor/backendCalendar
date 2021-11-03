package com.example.calendar.DTOs;

import com.example.calendar.entities.Structures;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructurMapper {

    StructureDto toDto(Structures structure);


}
