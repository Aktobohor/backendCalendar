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

    public List<NewReminderDTO> findAllApprovedfullData() {
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
        //non va inserita perchè devo ricevere l'id della struttura già presdente
        //Structures structure = this.structuresMapper.toEntity(newReminderDTO.getStructureDto());

        Reminders reminder = this.remindersMapper.toEntity(newReminderDTO.getReminderDto());

        //Structures savedStructure = this.structuresRepository.save(structure);
        Reminders savedReminder = this.remindersRepository.save(reminder);

        //id structure statico per ora ma recuperato dalle info selezionate dal form del Front-end.
        StrsRems strsRems = new StrsRems(26, savedReminder.getId(), newReminderDTO.getStrsRemsDto().getCreator(), "N", newReminderDTO.getStrsRemsDto().getEvent_duration());

        StrsRems savedStrsRems = this.strsRemsRepository.save(strsRems);

        //StructureDto savedStructureDto = this.structuresMapper.toDto(savedStructure);
        ReminderDto savedReminderDto = this.remindersMapper.toDto(savedReminder);
        StrsRemsDto savedStrsRemsDto = this.strsRemsMapper.toDto(savedStrsRems);

        newReminderDTO.setReminderDto(savedReminderDto);
        //newReminderDTO.setStructureDto(savedStructureDto);
        newReminderDTO.setStrsRemsDto(savedStrsRemsDto);

        return newReminderDTO;
    }

    public void approveFromId(int idReminder) {
        StrsRems strsRemsById = this.strsRemsRepository.findByReminderId(idReminder);
        strsRemsById.setApproved("Y");

        this.strsRemsRepository.save(strsRemsById);
    }


    public List<NewReminderDTO> getAllDataNotApproved(){
        //prendere i dati delle 3 tabelle e restituirli.
        return null;
    }

    public List<NewReminderDTO> getAllDataFromIDs(){
        //prendere i dati delle 3 tabelle e restituirli.
        return null;
    }





//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<NewReminderDTO> getAllDataApproved(){
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
