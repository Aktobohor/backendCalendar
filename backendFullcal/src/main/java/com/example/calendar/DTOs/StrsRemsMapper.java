package com.example.calendar.DTOs;

import com.example.calendar.entities.StrsRems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StrsRemsMapper {

    StrsRemsDto toDto(StrsRems strs_rems);

    StrsRems toEntity(StrsRemsDto strs_rems);


}
