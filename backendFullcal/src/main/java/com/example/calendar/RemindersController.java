package com.example.calendar;

import com.example.calendar.DTOs.NewReminderDto;
import com.example.calendar.DTOs.QuestionaryDto;
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
    public List<NewReminderDto> getAllNotApprovedStrsRmds() {
        List<NewReminderDto> all = this.str_rmdService.findAllNotApproved();
        return all;
    }

    @GetMapping("/notapprovedfromuserNEW")
    public List<NewReminderDto> getAllNotApprovedFromUserStrsRmds() {
        List<NewReminderDto> all = this.str_rmdService.findAllNotApprovedFromUser();
        return all;
    }

    /*@GetMapping("/getid")
    public Reminders getRemindersfromId(@Param("Id") final int idReminder) {
        Reminders reminder = this.remindersService.getReminderFromId(idReminder);
        return reminder;
    }*/

    @PostMapping("/deleteFromId")
    public void deleteReminder(@RequestBody final int srID) {
        this.str_rmdService.deleteReminders(srID);
    }

    @PostMapping("/userDeleteFromId")
    public void userDeleteReminder(@RequestBody final int srID) {
        this.str_rmdService.userDeleteReminders(srID);
    }

    @GetMapping("/deleteall")
    public void deleteReminder() {
        this.remindersService.deleteAll();
    }

    @PostMapping("/create")
    public NewReminderDto createReminder(@RequestBody final NewReminderDto newReminder) {
        System.out.println("body: " + newReminder.getReminderDto());

        return this.remindersService.save(newReminder);
    }

    @PostMapping("/confirmFromId")
    public void approveFromId(@RequestBody final int idStrmRems) {
        System.out.println("idReminder: "+idStrmRems);
        //this.remindersService.approveFromId(idReminder);
        this.str_rmdService.approveFromId(idStrmRems, "US12345");
    }

    @PostMapping("/adminConfirmFromId")
    public void adminApproveFromId(@RequestBody final int idStrmRems) {
        System.out.println("idReminder: "+idStrmRems);
        //this.remindersService.approveFromId(idReminder);
        this.str_rmdService.adminApproveFromId(idStrmRems);
    }

    @PostMapping("/deleteQuestionarieFromid")
    public void deleteQuestionarieFromid(@RequestBody final String questionareID) {
        this.str_rmdService.deleteQuestionarieFromid(questionareID);
    }

    @GetMapping("/colorazione")
    public String getColor(@RequestParam(value = "strmRemId", required = true) final int srID) {
        StrsRems sr = this.str_rmdService.getStrsRemsFromId(srID);
        return sr.getEvent_color();
    }

    @PostMapping("/modificaQuestionarie")
    public void modificaQuestionarie(@RequestBody final QuestionaryDto questionarieModified) {
        System.out.println("body: " + questionarieModified);
        this.str_rmdService.updateQuestionaries(questionarieModified);
    }
}
