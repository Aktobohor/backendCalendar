package com.example.calendar.DTOs;

import com.example.calendar.entities.Reminders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RemindersMapper {

    RemindersDto toDto(Reminders reminders);

}
