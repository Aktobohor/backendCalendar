package com.example.calendar.DTOs;

import com.example.calendar.entities.Structures;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructuresMapper {

    StructuresDto toDto(Structures structures);


}
