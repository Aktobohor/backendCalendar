package com.example.calendar.DTOs;

import java.time.LocalDateTime;

public class StrsRemsDto {

    private int id;
    private int id_structure;
    private int id_reminder;
    private String creator;
    private LocalDateTime timestamp;
    private int event_duration;
    private String event_color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_structure() {
        return id_structure;
    }

    public void setId_structure(int id_structure) {
        this.id_structure = id_structure;
    }

    public int getId_reminder() {
        return id_reminder;
    }

    public void setId_reminder(int id_reminder) {
        this.id_reminder = id_reminder;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getEvent_duration() {
        return event_duration;
    }

    public void setEvent_duration(int eventd_duration) {
        this.event_duration = eventd_duration;
    }

    public String getEvent_color() {
        return event_color;
    }

    public void setEvent_color(String event_color) {
        this.event_color = event_color;
    }
}
