package com.example.calendar.DTOs;

import java.time.LocalDateTime;

public class ReminderDto {

    private int id;
    private String r_title;
    private String r_freq;
    private LocalDateTime r_dt_start;
    private double r_interval;
    private int r_wkst;
    private int r_count;
    private LocalDateTime r_until;
    private String r_tzid;
    private String r_bysetpos;
    private String r_bymonth;
    private String r_byyearday;
    private String r_byweekno;
    private String r_byweekday;
    private String r_byhour;
    private String r_byminute;
    private String r_byseconds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getR_freq() {
        return r_freq;
    }

    public void setR_freq(String r_freq) {
        this.r_freq = r_freq;
    }

    public LocalDateTime getR_dt_start() {
        return r_dt_start;
    }

    public void setR_dt_start(LocalDateTime r_dt_start) {
        this.r_dt_start = r_dt_start;
    }

    public double getR_interval() {
        return r_interval;
    }

    public void setR_interval(double r_interval) {
        this.r_interval = r_interval;
    }

    public int getR_wkst() {
        return r_wkst;
    }

    public void setR_wkst(int r_wkst) {
        this.r_wkst = r_wkst;
    }

    public int getR_count() {
        return r_count;
    }

    public void setR_count(int r_count) {
        this.r_count = r_count;
    }

    public LocalDateTime getR_until() {
        return r_until;
    }

    public void setR_until(LocalDateTime r_until) {
        this.r_until = r_until;
    }

    public String getR_tzid() {
        return r_tzid;
    }

    public void setR_tzid(String r_tzid) {
        this.r_tzid = r_tzid;
    }

    public String getR_bysetpos() {
        return r_bysetpos;
    }

    public void setR_bysetpos(String r_bysetpos) {
        this.r_bysetpos = r_bysetpos;
    }

    public String getR_bymonth() {
        return r_bymonth;
    }

    public void setR_bymonth(String r_bymonth) {
        this.r_bymonth = r_bymonth;
    }

    public String getR_byyearday() {
        return r_byyearday;
    }

    public void setR_byyearday(String r_byyearday) {
        this.r_byyearday = r_byyearday;
    }

    public String getR_byweekno() {
        return r_byweekno;
    }

    public void setR_byweekno(String r_byweekno) {
        this.r_byweekno = r_byweekno;
    }

    public String getR_byweekday() {
        return r_byweekday;
    }

    public void setR_byweekday(String r_byweekday) {
        this.r_byweekday = r_byweekday;
    }

    public String getR_byhour() {
        return r_byhour;
    }

    public void setR_byhour(String r_byhour) {
        this.r_byhour = r_byhour;
    }

    public String getR_byminute() {
        return r_byminute;
    }

    public void setR_byminute(String r_byminute) {
        this.r_byminute = r_byminute;
    }

    public String getR_byseconds() {
        return r_byseconds;
    }

    public void setR_byseconds(String r_byseconds) {
        this.r_byseconds = r_byseconds;
    }

    public String getR_title() {
        return r_title;
    }

    public void setR_title(String r_title) {
        this.r_title = r_title;
    }

}
