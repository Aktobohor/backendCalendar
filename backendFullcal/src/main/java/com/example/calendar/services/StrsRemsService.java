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

    public void approveFromId(int idStrmRems) {
        Optional<StrsRems> strsRemsById = this.strsRemsRepository.findById(idStrmRems);
        strsRemsById.get().setApproved("Y");
        this.strsRemsRepository.save(strsRemsById.get());

        Reminders rem = getReminderFromId(strsRemsById.get().getId_reminder());
        Structures str = getStructureFromId(strsRemsById.get().getId_structure());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.set("email", "admin@example.com");
        header.set("password", "superPss");
        HttpEntity<?> request = new HttpEntity<>(header);
        int counter = 0;
        try {
            // Print out each date in the series.
            for (LocalDate date : LocalDateIteratorFactory.createLocalDateIterable(rem.getR_string_rule().split("\n")[1], new org.joda.time.LocalDate(rem.getR_dt_start().getYear(), rem.getR_dt_start().getMonthValue(), rem.getR_dt_start().getDayOfMonth()), true)) {
                counter++;
                System.out.println(date);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date parsedDate = dateFormat.parse(date.toString() + " " + rem.getR_byhour() + ":" + rem.getR_byminute() + ":" + rem.getR_byseconds());
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                String ID = "Recurrence" + strsRemsById.get().getId() + "|" + counter;

                UriComponentsBuilder insert = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires")
                        .queryParam("id", ID)
                        .queryParam("date", timestamp.toString())
                        .queryParam("description", "Question")
                        .queryParam("duration", strsRemsById.get().getEvent_duration())
                        .queryParam("name", rem.getR_title() + counter)
                        .queryParam("ordering", "asis1")
                        .queryParam("questionid", str.getIdQuestionary())
                        .queryParam("status", "stopped")
                        .queryParam("target", "/topics/wenet")
                        .queryParam("timeinterval", 1600);

                restTemplate.exchange(insert.build().encode().toUri(), HttpMethod.POST, request, String.class);
            }

            UriComponentsBuilder reload = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/reload/");
            restTemplate.exchange(reload.build().encode().toUri(), HttpMethod.GET, request, String.class);


            counter = 0;
            //TODO: USARE LA LISTA INVECE CHE UN FOR SULLE DATE :) 
            for (LocalDate date : LocalDateIteratorFactory.createLocalDateIterable(rem.getR_string_rule().split("\n")[1], new org.joda.time.LocalDate(rem.getR_dt_start().getYear(), rem.getR_dt_start().getMonthValue(), rem.getR_dt_start().getDayOfMonth()), true)) {
                counter++;
                System.out.println(date);

                String ID = "Recurrence" + strsRemsById.get().getId() + "|" + counter;


                UriComponentsBuilder start = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/jobs/start/" + ID);

                restTemplate.exchange(start.build().encode().toUri(), HttpMethod.GET, request, String.class);

            }

        } catch (Exception e) {
            System.out.println("ParsingError creazione RRULE: " + e.getMessage());
        }
    }

    public Reminders getReminderFromId(int idReminder) {
        Optional<Reminders> checkreminder = this.remindersRepository.findById(idReminder);
        Reminders reminder;
        reminder = checkreminder.orElse(null);
        System.out.println("Reminder:" + remindersMapper.toString());
        return reminder;
    }

    public Structures getStructureFromId(int idStrutures) {
        Optional<Structures> checkStructure = this.structuresRepository.findById(idStrutures);
        Structures structure;
        structure = checkStructure.orElse(null);
        System.out.println("Reminder:" + structuresMapper.toString());
        return structure;
    }

    public StrsRems getStrsRemsFromId(int srID) {
        Optional<StrsRems> checkStrmRems = this.strsRemsRepository.findById(srID);
        StrsRems sr;
        sr = checkStrmRems.orElse(null);
        System.out.println("Reminder:" + structuresMapper.toString());
        return sr;
    }
}
