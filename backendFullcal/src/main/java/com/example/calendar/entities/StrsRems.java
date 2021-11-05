package com.example.calendar.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Date;

public class StrsRems {

    @Column("id")
    private @Id int id;
    @Column("id_structure")
    private  int id_structure;
    @Column("id_reminder")
    private int id_reminder;
    @Column("creator")
    private String creator;
    @Column("timestamp")
    private  Date timestamp;

    public StrsRems(int id_structure, int id_reminder, String creator, Date timestamp){
        this.id_structure = id_structure;
        this.id_reminder = id_reminder;
        this.creator = creator;
        this.timestamp = timestamp;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Reminders{" +
                "id=" + id +
                ", id_structure=" + id_structure +
                ", id_reminder=" + id_reminder +
                ", creator=" + creator +
                ", timestamp=" + timestamp +
                '}';
    }
}
