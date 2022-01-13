package com.example.calendar.services;

import com.example.calendar.DTOs.*;
import com.example.calendar.entities.*;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StrsRemsService {

    private NewRemindersRepository newRemindersRepository;
    private RemindersRepository remindersRepository;
    private StructuresRepository structuresRepository;
    private StrsRemsRepository strsRemsRepository;

    private NewReminderMapper newRemindersMapper;
    private RemindersMapper remindersMapper;
    private StructuresMapper structuresMapper;
    private StrsRemsMapper strsRemsMapper;

    public StrsRemsService(StrsRemsMapper strs_remsMapper, StrsRemsRepository strs_remsRepository, RemindersMapper remindersMapper, StructuresMapper structuresMapper, RemindersRepository remindersRepository, StructuresRepository structuresRepository, StrsRemsRepository strsRemsRepository, StrsRemsMapper strsRemsMapper) {

        this.remindersMapper = remindersMapper;
        this.structuresMapper = structuresMapper;
        this.remindersRepository = remindersRepository;
        this.structuresRepository = structuresRepository;
        this.strsRemsRepository = strsRemsRepository;
        this.strsRemsMapper = strsRemsMapper;
    }

    public List<StrsRemsDto> findAll() {
        Iterable<StrsRems> all = this.strsRemsRepository.findAll();
        List<StrsRems> results = IterableUtils.toList(all);
        System.out.println("StrsRems:" + all);

        //ritorna una sola structure
        //return  structurMapper.toDto(structures);

        return results.stream().map(result -> strsRemsMapper.toDto(result)).collect(Collectors.toList());

    }

    public void save(StrsRems s) {
        this.strsRemsRepository.save(s);
    }

    //recupero tutte le info degli eventi ricorrenti avendo una lista
    // con le info delle 3 tabelle contenute negli appositi oggetti
    public List<NewReminderDTO> findAllNotApproved() {
        // ARGOMENTO decide cosa recuperare
        return CreateNewReminderDtoList(this.strsRemsRepository.allStrsRmsNotApproved());
    }

    /**
     * metodo che genera la struttura di dati che viene gestita dal frontend
     * @param allNotApproved Dati che interessano e devono essere messi nella struttura NewReminderDTO
     * @return List<NewReminderDTO> dati che sono utlizzabili dal front-end
     */
    List<NewReminderDTO> CreateNewReminderDtoList(List<StrsRems> allNotApproved){
        List<NewReminderDTO> completeReinders = new ArrayList<>();
        for (StrsRems strm :allNotApproved) {
            Optional<Reminders> rem = this.remindersRepository.findById(strm.getId_reminder());
            Optional<Structures> st = this.structuresRepository.findById(strm.getId_structure());
            if(rem.isPresent() && st.isPresent()) {
                NewReminderDTO nr = new NewReminderDTO();
                nr.setReminderDto(this.remindersMapper.toDto(rem.get()));
                nr.setStructureDto(this.structuresMapper.toDto(st.get()));
                nr.setStrsRemsDto(this.strsRemsMapper.toDto(strm));

                completeReinders.add(nr);
            }
        }
        return completeReinders;
    }

}
