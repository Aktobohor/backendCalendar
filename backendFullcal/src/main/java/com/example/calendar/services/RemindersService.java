package com.example.calendar.services;

import com.example.calendar.DTOs.*;
import com.example.calendar.entities.*;
import com.google.ical.compat.jodatime.LocalDateIteratorFactory;
import org.apache.commons.collections4.IterableUtils;
import org.joda.time.LocalDate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public Structures getStructureFromId(int idStrutures) {
        Optional<Structures> checkreminder = this.structuresRepository.findById(idStrutures);
        Structures structure;
        if(checkreminder.isPresent()){
            structure = checkreminder.get();
        }else{
            structure = null;
        }
        System.out.println("Reminder:" + structuresMapper.toString());
        return structure;
    }

    public void deleteStructureFromId(int idStrutures) {
        this.remindersRepository.deleteById(idStrutures);
    }

    public NewReminderDTO save(NewReminderDTO newReminderDTO) {

        Structures structure = this.structuresMapper.toEntity(newReminderDTO.getStructureDto());
        Reminders reminder = this.remindersMapper.toEntity(newReminderDTO.getReminderDto());

        Structures savedStructure = this.structuresRepository.save(structure);
        Reminders savedReminder = this.remindersRepository.save(reminder);

        //id structure statico per ora ma recuperato dalle info selezionate dal form del Front-end.
        StrsRems strsRems = new StrsRems(savedStructure.getId(), savedReminder.getId(), newReminderDTO.getStrsRemsDto().getCreator(), "N", newReminderDTO.getStrsRemsDto().getEvent_duration());

        StrsRems savedStrsRems = this.strsRemsRepository.save(strsRems);

        StructureDto savedStructureDto = this.structuresMapper.toDto(savedStructure);
        ReminderDto savedReminderDto = this.remindersMapper.toDto(savedReminder);
        StrsRemsDto savedStrsRemsDto = this.strsRemsMapper.toDto(savedStrsRems);

        newReminderDTO.setStructureDto(savedStructureDto);
        newReminderDTO.setReminderDto(savedReminderDto);
        newReminderDTO.setStrsRemsDto(savedStrsRemsDto);

        return newReminderDTO;
    }

    public void approveFromId(int idReminder) {
        StrsRems strsRemsById = this.strsRemsRepository.findByReminderId(idReminder);
        //strsRemsById.setApproved("Y");
        //this.strsRemsRepository.save(strsRemsById);

        Reminders rem = getReminderFromId(idReminder);
        Structures str = getStructureFromId(strsRemsById.getId_structure());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.set("email", "admin@example.com");
        header.set("password","superPss");
        HttpEntity<?> request = new HttpEntity<>(header);
        int counter = 0;
        try {
            // Print out each date in the series.
            for (LocalDate date : LocalDateIteratorFactory.createLocalDateIterable(rem.getR_string_rule().split("\n")[1], new org.joda.time.LocalDate(rem.getR_dt_start().getYear(), rem.getR_dt_start().getMonthValue(), rem.getR_dt_start().getDayOfMonth()), true)) {
                counter ++;
                System.out.println(date);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date parsedDate = dateFormat.parse(date.toString()+" "+rem.getR_byhour()+":"+rem.getR_byminute()+":"+rem.getR_byseconds());
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                UriComponentsBuilder urlTemplate = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires")
                        .queryParam("id","Recurrencequest"+counter)
                        .queryParam("date",timestamp.toString())
                        .queryParam("description","Question")
                        .queryParam("duration",strsRemsById.getEvent_duration())
                        .queryParam("name",rem.getR_title()+counter)
                        .queryParam("ordering","asis1")
                        .queryParam("questionid",str.getIdQuestionary())
                        .queryParam("status","stopped")
                        .queryParam("target","/topics/wenet")
                        .queryParam("timeinterval",0);

                restTemplate.exchange(urlTemplate.build().encode().toUri(), HttpMethod.POST , request, String.class);

            }

        }catch (Exception e){
            System.out.println("ParsingError creazione RRULE: "+e.getMessage());
        }





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
