package com.example.calendar.DTOs;

import com.example.calendar.entities.Reminder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReminderMapper {

    ReminderDto toDto(Reminder reminder);

}
