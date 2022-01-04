package com.example.calendar.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("StrsRems")
public class StrsRems {

    @Column("id")
    private @Id
    int id;
    @Column("id_structure")
    private int id_structure;
    @Column("id_reminder")
    private int id_reminder;
    @Column("creator")
    private String creator;
    @Column("approved")
    private String approved;
    @Column("timestamp")
    private LocalDateTime timestamp;
    @Column("event_duration")
    private int event_duration;

    public StrsRems(int id_structure, int id_reminder, String creator, String approved, int event_duration) {
        this.id_structure = id_structure;
        this.id_reminder = id_reminder;
        this.creator = creator;
        this.approved = approved;
        this.event_duration = event_duration;
        this.timestamp = java.time.LocalDateTime.now();//new Date(Calendar.getInstance().getTime().getTime());
    }

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

    @Override
    public String toString() {
        return "StrsRems{" +
                "id=" + id +
                ", id_structure=" + id_structure +
                ", id_reminder=" + id_reminder +
                ", creator=" + creator +
                ", timestamp=" + timestamp +
                ", event_duration=" + event_duration +
                '}';
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public int getEvent_duration() {
        return event_duration;
    }

    public void setEvent_duration(int event_duration) {
        this.event_duration = event_duration;
    }
}
