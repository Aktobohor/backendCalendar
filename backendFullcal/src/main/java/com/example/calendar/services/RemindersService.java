package com.example.calendar.services;

import com.example.calendar.DTOs.*;
import com.example.calendar.entities.*;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemindersService {

    private NewRemindersRepository newRemindersRepository;
    private RemindersRepository remindersRepository;
    private StructuresRepository structuresRepository;
    private StrsRemsRepository strsRemsRepository;

    private NewReminderMapper newRemindersMapper;
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

    public List<NewReminderDto> findAllApprovedfullData() {
        List<NewReminder> allApproved = this.newRemindersRepository.findAllApprovedfullData();
        return allApproved.stream().map(result -> newRemindersMapper.toDto(result)).collect(Collectors.toList());
    }

    public List<ReminderDto> findAllApproved() {
        List<Reminders> allApproved = this.remindersRepository.findAllApproved();
        return allApproved.stream().map(result -> remindersMapper.toDto(result)).collect(Collectors.toList());
    }

    public List<ReminderDto> findAllNotApproved() {
        List<Reminders> allNotApproved = this.remindersRepository.findAllNotApproved();
        return allNotApproved.stream().map(result -> remindersMapper.toDto(result)).collect(Collectors.toList());

    }

    public void deleteAll() {
        this.remindersRepository.deleteAll();
        this.strsRemsRepository.deleteAll();
    }

    public void deleteStructureFromId(int idStrutures) {
        this.remindersRepository.deleteById(idStrutures);
    }

    public NewReminderDto save(NewReminderDto newReminderDTO) {

        Structures structure = this.structuresMapper.toEntity(newReminderDTO.getStructureDto());
        Reminders reminder = this.remindersMapper.toEntity(newReminderDTO.getReminderDto());

        //Se la structures è gia presente recupero l'id di quella struttura.
        Structures savedStructure = null;
        if(structure.getIdQuestionary() != null){
            savedStructure = this.structuresRepository.findFromQuestionId(structure.getIdQuestionary());
        }
        /*if(structure.getIdChallenges() != null){ //RICERCA STRUCTURES IN BASE ALLA DOMANDA SELEZIONATA.
            this.structuresRepository.findFromChallengeId(structure.getIdChallenges());
        }
        if(structure.getIdRandomTask() != null){
            this.structuresRepository.findFromRandomTaskId(structure.getIdRandomTask());
        }
        if(structure.getIdTask() != null){
            this.structuresRepository.findFromTaskId(structure.getIdTask());
        }*/
        //solo se non è stata trovata la structures allora la inserisco.
        if(savedStructure == null) {
            savedStructure = this.structuresRepository.save(structure);
        }
        //inserisco la RRule nel database per ricreare le ricorrenze.
        Reminders savedReminder = this.remindersRepository.save(reminder);

        //id structure statico per ora ma recuperato dalle info selezionate dal form del Front-end.
        StrsRems strsRems = new StrsRems(savedStructure.getId(), savedReminder.getId(), newReminderDTO.getStrsRemsDto().getCreator(), "N", "N", newReminderDTO.getStrsRemsDto().getEvent_duration(), newReminderDTO.getStrsRemsDto().getEvent_color());

        StrsRems savedStrsRems = this.strsRemsRepository.save(strsRems);

        StructureDto savedStructureDto = this.structuresMapper.toDto(savedStructure);
        ReminderDto savedReminderDto = this.remindersMapper.toDto(savedReminder);
        StrsRemsDto savedStrsRemsDto = this.strsRemsMapper.toDto(savedStrsRems);

        newReminderDTO.setStructureDto(savedStructureDto);
        newReminderDTO.setReminderDto(savedReminderDto);
        newReminderDTO.setStrsRemsDto(savedStrsRemsDto);

        return newReminderDTO;
    }


    public List<NewReminderDto> getAllDataNotApproved(){
        //prendere i dati delle 3 tabelle e restituirli.
        return null;
    }

    public List<NewReminderDto> getAllDataFromIDs(){
        //prendere i dati delle 3 tabelle e restituirli.
        return null;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<NewReminderDto> getAllDataApproved(){
        //prendere i dati delle 3 tabelle e restituirli.
        List<StrsRemsDto> allStrsRmsApprovedDto = this.findAllStrsRemsApproved();
        List<ReminderDto> allRemindersApprovedDto = this.findRemindersFromForeignKeys(allStrsRmsApprovedDto);
        List<StructureDto> allStructursApprovedDto = this.findStructursFromForeignKeys(allStrsRmsApprovedDto);

        return null;
    }

    public List<StrsRemsDto> findAllStrsRemsApproved() {
        List<StrsRems> allStrsRmsApproved = this.strsRemsRepository.allStrsRmsApproved();
        return allStrsRmsApproved.stream().map(result -> strsRemsMapper.toDto(result)).collect(Collectors.toList());
    }

    public List<ReminderDto> findRemindersFromForeignKeys(List<StrsRemsDto> allStrsRmsApproved) {
        List<Integer> listReminderIds = this.getIdList(allStrsRmsApproved, "Reminders");
        List<Reminders> remindersApproved = this.remindersRepository.findAllFromIDs(listReminderIds);
        return remindersApproved.stream().map(result -> remindersMapper.toDto(result)).collect(Collectors.toList());
    }

    public List<StructureDto> findStructursFromForeignKeys(List<StrsRemsDto> allStrsRmsApproved) {
        List<Integer> listReminderIds = this.getIdList(allStrsRmsApproved, "Structures");
        List<Structures> structuresApproved = this.structuresRepository.findAllFromIDs(listReminderIds);
        return structuresApproved.stream().map(result -> structuresMapper.toDto(result)).collect(Collectors.toList());
    }

    public List<Integer> getIdList(List<StrsRemsDto> list, String Table){
        List<Integer> idList = new ArrayList<>();
        switch(Table){
            case "Reminders":{
                for (StrsRemsDto a: list) {
                    idList.add(a.getId_reminder());
                }
                break;
            }
            case "Structures":{
                for (StrsRemsDto a: list) {
                    idList.add(a.getId_structure());
                }
                break;
            }
        }
        return idList;
    }
}
