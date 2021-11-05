package com.example.calendar.services;

import com.example.calendar.DTOs.StrsRemsDto;
import com.example.calendar.DTOs.StrsRemsMapper;
import com.example.calendar.entities.StrsRems;
import com.example.calendar.entities.StrsRemsRepository;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;
import java.util.stream.Collectors;

public class StrsRemsService {

    private StrsRemsRepository strs_remsRepository;

    private StrsRemsMapper strs_remsMapper;

    public StrsRemsService(StrsRemsMapper strs_remsMapper, StrsRemsRepository strs_remsRepository) {

        this.strs_remsMapper = strs_remsMapper;
        this.strs_remsRepository = strs_remsRepository;
    }

    public List<StrsRemsDto> findAll() {
        Iterable<StrsRems> all = this.strs_remsRepository.findAll();
        List<StrsRems> results = IterableUtils.toList(all);
        System.out.println("StrsRems:" + all);

        //ritorna una sola structure
        //return  structurMapper.toDto(structures);

        return results.stream().map(result -> strs_remsMapper.toDto(result)).collect(Collectors.toList());

    }

    public void save(StrsRems s) {
        this.strs_remsRepository.save(s);
    }

}
