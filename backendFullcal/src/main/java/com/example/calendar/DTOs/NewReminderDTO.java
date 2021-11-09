package com.example.calendar.DTOs;

public class NewReminderDTO {
    ReminderDto reminderDto;
    StructureDto structureDto;

    public ReminderDto getReminderDto() {
        return reminderDto;
    }

    public void setReminderDto(ReminderDto reminderDto) {
        this.reminderDto = reminderDto;
    }

    public StructureDto getStructureDto() {
        return structureDto;
    }

    public void setStructureDto(StructureDto structureDto) {
        this.structureDto = structureDto;
    }
}
