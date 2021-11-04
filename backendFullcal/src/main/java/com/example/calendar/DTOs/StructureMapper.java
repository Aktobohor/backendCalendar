package com.example.calendar.DTOs;

import com.example.calendar.entities.Structure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructureMapper {

    StructureDto toDto(Structure structure);


}
