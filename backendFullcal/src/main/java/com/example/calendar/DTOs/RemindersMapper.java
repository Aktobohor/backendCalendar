package com.example.calendar.DTOs;

import com.example.calendar.entities.Reminders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RemindersMapper {

    ReminderDto toDto(Reminders reminders);

    Reminders toEntity(ReminderDto reminder);

}
