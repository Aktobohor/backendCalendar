package com.example.calendar.DTOs;

import com.example.calendar.entities.NewReminder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewReminderMapper {

    NewReminderDTO toDto(NewReminder newReminders);

    NewReminder toEntity(NewReminderDTO reminder);
}
