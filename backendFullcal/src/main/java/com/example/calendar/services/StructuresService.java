package com.example.calendar.services;

import com.example.calendar.DTOs.StructureDto;
import com.example.calendar.DTOs.StructuresMapper;
import com.example.calendar.entities.Structures;
import com.example.calendar.entities.StructuresRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StructuresService {


    private StructuresRepository structuresRepository;

    private StructuresMapper structuresMapper;

    public StructuresService(StructuresMapper structuresMapper, StructuresRepository structuresRepository) {

        this.structuresMapper = structuresMapper;
        this.structuresRepository = structuresRepository;
    }

    public List<StructureDto> findAll() {
        Iterable<Structures> all = this.structuresRepository.findAll();
        List<Structures> results = IterableUtils.toList(all);
        System.out.println("aaaaaal:" + all);

        //ritorna una sola structures
        //return  structurMapper.toDto(structures);

        return results.stream().map(result -> structuresMapper.toDto(result)).collect(Collectors.toList());
    }

    public void save(Structures s) {
        this.structuresRepository.save(s);
    }
}
