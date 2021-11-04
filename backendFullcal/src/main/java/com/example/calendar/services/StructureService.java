package com.example.calendar.services;

import com.example.calendar.DTOs.StructureMapper;
import com.example.calendar.DTOs.StructureDto;
import com.example.calendar.entities.StructureRepository;
import com.example.calendar.entities.Structure;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StructureService {


    private StructureRepository structureRepository;

    private StructureMapper structureMapper;

    public StructureService(StructureMapper structureMapper, StructureRepository structureRepository) {

        this.structureMapper = structureMapper;
        this.structureRepository = structureRepository;
    }

    public List<StructureDto> findAll() {
        Iterable<Structure> all = this.structureRepository.findAll();
        List<Structure> results = IterableUtils.toList(all);
        System.out.println("aaaaaal:" + all);

        Structure structures = new Structure(1, 1, 1, 1);

        //ritorna una sola structure
        //return  structurMapper.toDto(structures);

        return results.stream().map(result -> structureMapper.toDto(result)).collect(Collectors.toList());


    }

    public void save(Structure s) {
        this.structureRepository.save(s);
    }
}
