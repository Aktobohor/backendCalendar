package com.example.calendar.services;

import com.example.calendar.DTOs.*;
import com.example.calendar.entities.Reminders;
import com.example.calendar.entities.RemindersRepository;
import com.example.calendar.entities.Structures;
import com.example.calendar.entities.StructuresRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemindersService {

    private RemindersRepository remindersRepository;
    private StructuresRepository structuresRepository;

    private RemindersMapper remindersMapper;

    private StructuresMapper structuresMapper;

    public RemindersService(RemindersMapper remindersMapper, StructuresMapper structuresMapper, RemindersRepository remindersRepository, StructuresRepository structuresRepository) {

        this.remindersMapper = remindersMapper;
        this.structuresMapper = structuresMapper;
        this.remindersRepository = remindersRepository;
        this.structuresRepository = structuresRepository;
    }

    public List<ReminderDto> findAll() {
        Iterable<Reminders> all = this.remindersRepository.findAll();
        List<Reminders> results = IterableUtils.toList(all);
        System.out.println("Reminders:" + all);

        return results.stream().map(result -> remindersMapper.toDto(result)).collect(Collectors.toList());


    }

    public NewReminderDTO save(NewReminderDTO newReminderDTO) {

        Structures structure = this.structuresMapper.toEntity(newReminderDTO.getStructureDto());
        Reminders reminder = this.remindersMapper.toEntity(newReminderDTO.getReminderDto());


        Structures savedStructure = this.structuresRepository.save(structure);
        Reminders savedReminder = this.remindersRepository.save(reminder);

        StructureDto savedStructureDto = this.structuresMapper.toDto(savedStructure);
        ReminderDto savedReminderDto = this.remindersMapper.toDto(savedReminder);

        newReminderDTO.setReminderDto(savedReminderDto);
        newReminderDTO.setStructureDto(savedStructureDto);
        return newReminderDTO;

    }


}
