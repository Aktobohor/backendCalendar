package com.example.calendar.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Date;

public class Reminder {

    @Column("id")
    private @Id int id;
    @Column("r_freq")
    private  int r_freq;
    @Column("r_dt_start")
    private Date r_dt_start;
    @Column("r_interval")
    private double r_interval;
    @Column("r_wkst")
    private  int r_wkst;
    @Column("r_count")
    private  int r_count;
    @Column("r_until")
    private Date r_until;
    @Column("r_tzid")
    private  String r_tzid;
    @Column("r_bysetpos")
    private  String r_bysetpos;
    @Column("r_bymonth")
    private  String r_bymonth;
    @Column("r_byyearday")
    private  String r_byyearday;
    @Column("r_byweekno")
    private  String r_byweekno;
    @Column("r_byweekday")
    private  String r_byweekday;
    @Column("r_byhour")
    private  String r_byhour;
    @Column("r_byminute")
    private  String r_byminute;
    @Column("r_byseconds")
    private  String r_byseconds;

    public Reminder(int r_freq, Date r_dt_start, double r_interval, int r_wkst, int r_count,
                    Date r_until, String r_tzid, String r_bysetpos, String r_bymonth,
                    String r_byyearday, String r_byweekno, String r_byweekday, String r_byhour,
                    String r_byminute, String r_byseconds){
        this.r_freq = r_freq;
        this.r_dt_start = r_dt_start;
        this.r_interval = r_interval;
        this.r_wkst = r_wkst;
        this.r_count = r_count;
        this.r_until = r_until;
        this.r_tzid = r_tzid;
        this.r_bysetpos = r_bysetpos;
        this.r_bymonth = r_bymonth;
        this.r_byyearday = r_byyearday;
        this.r_byweekno = r_byweekno;
        this.r_byweekday = r_byweekday;
        this.r_byhour = r_byhour;
        this.r_byminute = r_byminute;
        this.r_byseconds = r_byseconds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getR_freq() {
        return r_freq;
    }

    public void setR_freq(int r_freq) {
        this.r_freq = r_freq;
    }

    public Date getR_dt_start() {
        return r_dt_start;
    }

    public void setR_dt_start(Date r_dt_start) {
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

    public Date getR_until() {
        return r_until;
    }

    public void setR_until(Date r_until) {
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

    @Override
    public String toString() {
        return "Reminders{" +
                "id=" + id +
                ", r_freq=" + r_freq +
                ", r_dt_start=" + r_dt_start +
                ", r_interval=" + r_interval +
                ", r_wkst=" + r_wkst +
                ", r_until=" + r_until +
                ", r_bysetpos=" + r_bysetpos +
                ", r_bymonth=" + r_bymonth +
                ", r_byyearday=" + r_byyearday +
                ", r_byweekno=" + r_byweekno +
                ", r_byweekday=" + r_byweekday +
                ", r_byhour=" + r_byhour +
                ", r_byminute=" + r_byminute +
                ", r_byseconds=" + r_byseconds +
                '}';
    }
}
