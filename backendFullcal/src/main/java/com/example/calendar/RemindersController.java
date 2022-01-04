package com.example.calendar;

import com.example.calendar.DTOs.NewReminderDTO;
import com.example.calendar.DTOs.ReminderDto;
import com.example.calendar.DTOs.StrsRemsDto;
import com.example.calendar.DTOs.StructureDto;
import com.example.calendar.entities.Reminders;
import com.example.calendar.entities.StrsRems;
import com.example.calendar.entities.Structures;
import com.example.calendar.services.RemindersService;
import com.example.calendar.services.StrsRemsService;
import com.example.calendar.services.StructuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/approvedfulldata")
    public List<NewReminderDTO> findAllApprovedfullData() {
        List<StrsRemsDto> allSR = this.str_rmdService.findAll();
        List<ReminderDto> allRems = this.remindersService.findAll();
        List<StructureDto> allStr = this.structuresService.findAll();

        List<NewReminderDTO> all = this.remindersService.findAllApprovedfullData();
        for (StrsRemsDto a: allSR) {
            StructureDto sDto = allStr.stream().f
        }



        return all;
    }

    @GetMapping("/notapproved")
    public List<ReminderDto> getAllNotApprovedReminders() {
        List<ReminderDto> all = this.remindersService.findAllNotApproved();
        return all;
    }

    @GetMapping("/getid")
    public Reminders getRemindersfromId(@Param("Id") final int idReminder) {
        Reminders reminder = this.remindersService.getReminderFromId(idReminder);
        return reminder;
    }

    @GetMapping("/delete/id")
    public void deleteReminder(@Param("Id") final int idReminder) {
        this.remindersService.deleteReminderFromId(idReminder);
    }

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
    public void approveFromId(@RequestBody final int idReminder) {
        System.out.println("idReminder: "+idReminder);
        this.remindersService.approveFromId(idReminder);
    }

}
