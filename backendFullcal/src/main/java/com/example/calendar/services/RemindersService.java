package com.example.calendar.services;

import com.example.calendar.DTOs.*;
import com.example.calendar.entities.*;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RemindersService {

    private RemindersRepository remindersRepository;
    private StructuresRepository structuresRepository;
    private StrsRemsRepository strsRemsRepository;

    private RemindersMapper remindersMapper;
    private StructuresMapper structuresMapper;
    private StrsRemsMapper strsRemsMapper;

    public RemindersService(RemindersMapper remindersMapper, StructuresMapper structuresMapper, RemindersRepository remindersRepository, StructuresRepository structuresRepository, StrsRemsRepository strsRemsRepository, StrsRemsMapper strsRemsMapper) {

        this.remindersMapper = remindersMapper;
        this.structuresMapper = structuresMapper;
        this.remindersRepository = remindersRepository;
        this.structuresRepository = structuresRepository;
        this.strsRemsRepository = strsRemsRepository;
        this.strsRemsMapper = strsRemsMapper;
    }

    public List<ReminderDto> findAll() {
        Iterable<Reminders> all = this.remindersRepository.findAll();
        List<Reminders> results = IterableUtils.toList(all);
        System.out.println("Reminders:" + all);

        return results.stream().map(result -> remindersMapper.toDto(result)).collect(Collectors.toList());
    }

    public void deleteAll() {
        this.remindersRepository.deleteAll();
        this.strsRemsRepository.deleteAll();
    }

    public Reminders getReminderFromId(int idReminder) {
        Optional<Reminders> checkreminder = this.remindersRepository.findById(idReminder);
        Reminders reminder;
        if(checkreminder.isPresent()){
            reminder = checkreminder.get();
        }else{
            reminder = null;
        }
        System.out.println("Reminder:" + remindersMapper.toString());
        return reminder;
    }

    public void deleteReminderFromId(int idReminder) {
        this.remindersRepository.deleteById(idReminder);
    }

    public NewReminderDTO save(NewReminderDTO newReminderDTO) {
        Structures structure = this.structuresMapper.toEntity(newReminderDTO.getStructureDto());
        Reminders reminder = this.remindersMapper.toEntity(newReminderDTO.getReminderDto());
        StrsRems strsRems = this.strsRemsMapper.toEntity(newReminderDTO.getStrsRemsDto());

        Structures savedStructure = this.structuresRepository.save(structure);
        Reminders savedReminder = this.remindersRepository.save(reminder);
        StrsRems savedStrsRems = this.strsRemsRepository.save(strsRems);

        StructureDto savedStructureDto = this.structuresMapper.toDto(savedStructure);
        ReminderDto savedReminderDto = this.remindersMapper.toDto(savedReminder);
        StrsRemsDto savedStrsRemsDto = this.strsRemsMapper.toDto(savedStrsRems);

        newReminderDTO.setReminderDto(savedReminderDto);
        newReminderDTO.setStructureDto(savedStructureDto);
        newReminderDTO.setStrsRemsDto(savedStrsRemsDto);

        return newReminderDTO;
    }



}
