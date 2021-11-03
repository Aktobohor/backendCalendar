package com.example.calendar.services;

import com.example.calendar.DTOs.StructurMapper;
import com.example.calendar.DTOs.StructureDto;
import com.example.calendar.entities.StructureRepository;
import com.example.calendar.entities.Structures;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StructuresService {


    private StructureRepository structureRepository;

    private StructurMapper structurMapper;

    public StructuresService(StructurMapper structurMapper, StructureRepository structureRepository) {

        this.structurMapper = structurMapper;
        this.structureRepository = structureRepository;
    }

    public List<StructureDto> findAll() {
        Iterable<Structures> all = this.structureRepository.findAll();
        List<Structures> results = IterableUtils.toList(all);
        System.out.println("aaaaaal:" + all);

        Structures structures = new Structures(1, 1, 1, 1);

        //ritorna una sola structure
        //return  structurMapper.toDto(structures);

        return results.stream().map(result -> structurMapper.toDto(result)).collect(Collectors.toList());


    }

    public void save(Structures s) {
        this.structureRepository.save(s);
    }
}
