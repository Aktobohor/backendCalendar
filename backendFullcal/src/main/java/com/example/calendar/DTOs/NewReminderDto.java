package com.example.calendar.DTOs;

public class NewReminderDto {
    ReminderDto reminderDto;
    StructureDto structureDto;
    StrsRemsDto strsRemsDto;

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

    public StrsRemsDto getStrsRemsDto() {
        return strsRemsDto;
    }

    public void setStrsRemsDto(StrsRemsDto strsRemsDto) {
        this.strsRemsDto = strsRemsDto;
    }
}
