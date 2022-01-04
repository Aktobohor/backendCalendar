package com.example.calendar.entities;

public class NewReminder {
    private Reminders reminder;
    private Structures structure;
    private StrsRems strsRems;

    public NewReminder(Reminders reminder, Structures structure, StrsRems strsRems){
        this.reminder = reminder;
        this.structure = structure;
        this.strsRems = strsRems;
    }

    public Reminders getReminderDto() {
        return reminder;
    }

    public void setReminderDto(Reminders reminder) {
        this.reminder = reminder;
    }

    public Structures getStructureDto() {
        return structure;
    }

    public void setStructureDto(Structures structure) {
        this.structure = structure;
    }

    public StrsRems getStrsRemsDto() {
        return strsRems;
    }

    public void setStrsRemsDto(StrsRems strsRems) {
        this.strsRems = strsRems;
    }

    @Override
    public String toString() {
        return "NewReminder{" +
                "Reminders=" + reminder +
                ", Structures=" + structure +
                ", StrsRems=" + strsRems +
                '}';
    }
}
