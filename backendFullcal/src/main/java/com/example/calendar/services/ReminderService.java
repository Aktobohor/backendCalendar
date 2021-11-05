package com.example.calendar.services;

import com.example.calendar.DTOs.ReminderDto;
import com.example.calendar.DTOs.ReminderMapper;
import com.example.calendar.entities.Reminder;
import com.example.calendar.entities.ReminderRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {

    private ReminderRepository reminderRepository;

    private ReminderMapper reminderMapper;

    public ReminderService(ReminderMapper reminderMapper, ReminderRepository reminderRepository) {

        this.reminderMapper = reminderMapper;
        this.reminderRepository = reminderRepository;
    }

    public List<ReminderDto> findAll() {
        Iterable<Reminder> all = this.reminderRepository.findAll();
        List<Reminder> results = IterableUtils.toList(all);
        System.out.println("Reminders:" + all);

        //ritorna una sola structure
        //return  structurMapper.toDto(structures);

        return results.stream().map(result -> reminderMapper.toDto(result)).collect(Collectors.toList());


    }

    public void save(Reminder r) {
        this.reminderRepository.save(r);
    }


}
