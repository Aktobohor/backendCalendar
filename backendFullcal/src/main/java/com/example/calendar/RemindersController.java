package com.example.calendar;

import com.example.calendar.DTOs.NewReminderDTO;
import com.example.calendar.DTOs.ReminderDto;
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

    @PostMapping("")
    public NewReminderDTO createReminder(@RequestBody final NewReminderDTO newReminder) {
        System.out.println("body: " + newReminder.getReminderDto());

        return this.remindersService.save(newReminder);

    }
}