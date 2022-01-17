package com.example.calendar;

import com.example.calendar.DTOs.NewReminderDTO;
import com.example.calendar.DTOs.ReminderDto;
import com.example.calendar.entities.StrsRems;
import com.example.calendar.services.RemindersService;
import com.example.calendar.services.StrsRemsService;
import com.example.calendar.services.StructuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/reminders", produces = "application/json")
public class RemindersController {

    int a = 1;
    @Autowired
    private StructuresService structuresService;
    @Autowired
    private RemindersService remindersService;
    @Autowired
    private StrsRemsService str_rmdService;

    @GetMapping("")
    public List<ReminderDto> getAllReminders() {
        List<ReminderDto> all = this.remindersService.findAll();
        return all;
    }

    @GetMapping("/approved")
    public List<ReminderDto> getAllApprovedReminders() {
        List<ReminderDto> all = this.remindersService.findAllApproved();
        return all;
    }

    @GetMapping("/notapproved")
    public List<ReminderDto> getAllNotApprovedReminders() {
        List<ReminderDto> all = this.remindersService.findAllNotApproved();
        return all;
    }

    @GetMapping("/notapprovedNEW")
    public List<NewReminderDTO> getAllNotApprovedStrsRmds() {
        List<NewReminderDTO> all = this.str_rmdService.findAllNotApproved();
        return all;
    }

    /*@GetMapping("/getid")
    public Reminders getRemindersfromId(@Param("Id") final int idReminder) {
        Reminders reminder = this.remindersService.getReminderFromId(idReminder);
        return reminder;
    }*/

    /*@GetMapping("/deleteFromId")
    public void deleteReminder(@RequestBody final int idReminder) {
        this.remindersService.deleteReminderFromId(idReminder);
    }*/

    @GetMapping("/deleteall")
    public void deleteReminder() {
        this.remindersService.deleteAll();
    }

    @PostMapping("/create")
    public NewReminderDTO createReminder(@RequestBody final NewReminderDTO newReminder) {
        System.out.println("body: " + newReminder.getReminderDto());

        return this.remindersService.save(newReminder);
    }

    @PostMapping("/confirmFromId")
    public void approveFromId(@RequestBody final int idStrmRems) {
        System.out.println("idReminder: "+idStrmRems);
        //this.remindersService.approveFromId(idReminder);
        this.str_rmdService.approveFromId(idStrmRems);
    }

    @GetMapping("/colorazione")
    public String getRemindersfromId(@RequestParam(value = "strmRemId", required = true) final int srID) {
        StrsRems sr = this.str_rmdService.getStrsRemsFromId(srID);
        return sr.getEvent_color();
    }

}
