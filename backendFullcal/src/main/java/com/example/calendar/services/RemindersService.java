package com.example.calendar.services;

import com.example.calendar.DTOs.RemindersDto;
import com.example.calendar.DTOs.RemindersMapper;
import com.example.calendar.entities.Reminders;
import com.example.calendar.entities.RemindersRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemindersService {

    private RemindersRepository remindersRepository;

    private RemindersMapper remindersMapper;

    public RemindersService(RemindersMapper remindersMapper, RemindersRepository remindersRepository) {

        this.remindersMapper = remindersMapper;
        this.remindersRepository = remindersRepository;
    }

    public List<RemindersDto> findAll() {
        Iterable<Reminders> all = this.remindersRepository.findAll();
        List<Reminders> results = IterableUtils.toList(all);
        System.out.println("Reminders:" + all);

        //ritorna una sola structure
        //return  structurMapper.toDto(structures);

        return results.stream().map(result -> remindersMapper.toDto(result)).collect(Collectors.toList());


    }

    public void save(Reminders r) {
        this.remindersRepository.save(r);
    }


}
