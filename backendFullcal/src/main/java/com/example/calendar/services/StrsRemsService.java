package com.example.calendar.services;

import com.example.calendar.DTOs.*;
import com.example.calendar.entities.*;
import com.google.ical.compat.jodatime.LocalDateIteratorFactory;
import org.apache.commons.collections4.IterableUtils;
import org.joda.time.LocalDate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    private HttpHeaders getHttpHeaders(){
        HttpHeaders header = new HttpHeaders();
        header.set("email", "admin@example.com");
        header.set("password", "superPss");
        return header;
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
    public List<NewReminderDto> findAllNotApproved() {
        // ARGOMENTO decide cosa recuperare
        return CreateNewReminderDtoList(this.strsRemsRepository.allStrsRmsNotApproved());
    }

    /**
     * metodo che genera la struttura di dati che viene gestita dal frontend
     * @param allNotApproved Dati che interessano e devono essere messi nella struttura NewReminderDTO
     * @return List<NewReminderDTO> dati che sono utlizzabili dal front-end
     */
    List<NewReminderDto> CreateNewReminderDtoList(List<StrsRems> allNotApproved){
        List<NewReminderDto> completeReinders = new ArrayList<>();
        for (StrsRems strm :allNotApproved) {
            Optional<Reminders> rem = this.remindersRepository.findById(strm.getId_reminder());
            Optional<Structures> st = this.structuresRepository.findById(strm.getId_structure());
            if(rem.isPresent() && st.isPresent()) {
                NewReminderDto nr = new NewReminderDto();
                nr.setReminderDto(this.remindersMapper.toDto(rem.get()));
                nr.setStructureDto(this.structuresMapper.toDto(st.get()));
                nr.setStrsRemsDto(this.strsRemsMapper.toDto(strm));

                completeReinders.add(nr);
            }
        }
        return completeReinders;
    }

    /**
     * metodo che approva la ricorrenza inserita e carica su Ilog le ricorrenze che saranno schadulate
     * in attesa di essere inviate agli utenti
     * @param idStrmRems id della tabella join tra structures e reminder
     */
    public void approveFromId(int idStrmRems) {
        Optional<StrsRems> strsRemsById = this.strsRemsRepository.findById(idStrmRems);
        strsRemsById.get().setApproved("Y");
        this.strsRemsRepository.save(strsRemsById.get());

        Reminders rem = getReminderFromId(strsRemsById.get().getId_reminder());
        Structures str = getStructureFromId(strsRemsById.get().getId_structure());
        RestTemplate restTemplate = new RestTemplate();

        List<String>QuestionariesIDs = new ArrayList<>();
        addQuestionariesInEsecuzione(QuestionariesIDs);

        HttpEntity<?> request = new HttpEntity<>(getHttpHeaders());
        int counter = 0;

        try {
            //in base alla RRule estraggo una lista di date per inserire le questionaries sul Back-end ILOG.
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
                        .queryParam("name", rem.getR_title())
                        .queryParam("ordering", "asis1")
                        .queryParam("questionid", str.getIdQuestionary())
                        .queryParam("status", "stopped")
                        .queryParam("target", "/topics/wenet")
                        .queryParam("timeinterval", 1600);
                restTemplate.exchange(insert.build().encode().toUri(), HttpMethod.POST, request, String.class);
                QuestionariesIDs.add(ID);

            }

            //Ricarico la lista dei job in seguito all'inserimento delle nuove questionaries su ILOG
            UriComponentsBuilder reload = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/reload/");
            restTemplate.exchange(reload.build().encode().toUri(), HttpMethod.GET, request, String.class);

            //Avvio le questionaries inserite sopra ciclando la lista di ID compilata nell'inserimento delle questionaries su ILOG
            for (String ID : QuestionariesIDs) {
                UriComponentsBuilder start = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/jobs/start/" + ID);
                restTemplate.exchange(start.build().encode().toUri(), HttpMethod.GET, request, String.class);
            }

        } catch (Exception e) {
            System.out.println("ParsingError creazione RRULE: " + e.getMessage());
        }
    }

    private void addQuestionariesInEsecuzione(List<String> questionariesIDs) {
        //recupero le questionaries a running e le aggiungo all'oggetto ricevuto come argomento.
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> request = new HttpEntity<>(getHttpHeaders());
        UriComponentsBuilder reload = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires");
        ResponseEntity<String> entity = restTemplate.exchange(reload.build().encode().toUri(), HttpMethod.GET, request, String.class);
        String body = entity.getBody();
        body = body.replace("[","");
        body = body.replace("]","");
        body = body.replace("{","");
        String[] a = body.split("}");
        String idQuestionRunning;
        for (String b : a){
            idQuestionRunning = "";
            String[] tmp = b.split(",");
            for(String j : tmp){
                j = j.replace("\"","");
                String[] i = j.split(":");
                if(i[0].equals("id")){
                    idQuestionRunning = i[1];
                }
                if(i[0].equals("status")){
                    if(i[1].equals("running")){
                        questionariesIDs.add(idQuestionRunning);
                    }
                }
            }
        }
    }

    public void deleteQuestionarieFromid(String ID){

        //1)stoppo il questionaries che è già schedulato
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> request = new HttpEntity<>(getHttpHeaders());
        UriComponentsBuilder stopQID = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/jobs/stop/" + ID);
        restTemplate.exchange(stopQID.build().encode().toUri(), HttpMethod.GET, request, String.class);

        //2)elimino il questionaries che è stato stoppato
        restTemplate = new RestTemplate();
        request = new HttpEntity<>(getHttpHeaders());
        UriComponentsBuilder deleteQID = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/" + ID);
        restTemplate.exchange(deleteQID.build().encode().toUri(), HttpMethod.DELETE, request, String.class);

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

    public void updateQuestionaries( QuestionaryDto questionaryModified) {


        //1)stoppo il questionaries che è già schedulato
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> request = new HttpEntity<>(getHttpHeaders());
        UriComponentsBuilder stopQID = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/jobs/stop/" + questionaryModified.getId());
        restTemplate.exchange(stopQID.build().encode().toUri(), HttpMethod.GET, request, String.class);

        List<String>QuestionariesIDs = new ArrayList<>();
        addQuestionariesInEsecuzione(QuestionariesIDs);

        //format della data
        Timestamp timestamp = new java.sql.Timestamp(questionaryModified.getDate().getTime());

        //2)Aggiorno il questionaries che è stato stoppato
        restTemplate = new RestTemplate();
        request = new HttpEntity<>(getHttpHeaders());
        UriComponentsBuilder deleteQID = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/")
                .queryParam("id", questionaryModified.getId())
                .queryParam("date", timestamp.toString())
                .queryParam("description", questionaryModified.getDescription())
                .queryParam("duration", questionaryModified.getDuration())
                .queryParam("name", questionaryModified.getName())
                .queryParam("ordering", questionaryModified.getOrdering())
                .queryParam("questionid", questionaryModified.getQuestionid())
                .queryParam("status", questionaryModified.getStatus())
                .queryParam("target", questionaryModified.getTarget())
                .queryParam("timeinterval", questionaryModified.getTimeinterval());
        restTemplate.exchange(deleteQID.build().encode().toUri(), HttpMethod.PUT, request, String.class);
        QuestionariesIDs.add(questionaryModified.getId());

        //Ricarico la lista dei job in seguito all'inserimento delle nuove questionaries su ILOG
        UriComponentsBuilder reload = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/reload/");
        restTemplate.exchange(reload.build().encode().toUri(), HttpMethod.GET, request, String.class);

        //Avvio le questionaries inserite sopra ciclando la lista di ID compilata nell'inserimento delle questionaries su ILOG
        for (String ID : QuestionariesIDs) {
            UriComponentsBuilder start = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/jobs/start/" + ID);
            restTemplate.exchange(start.build().encode().toUri(), HttpMethod.GET, request, String.class);
        }
        //Avvio la questionaries aggiornata sopra
        /*UriComponentsBuilder start = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/questionnaires/jobs/start/" + questionaryModified.getId());
        restTemplate.exchange(start.build().encode().toUri(), HttpMethod.GET, request, String.class);*/

    }

    public void deleteReminders(int srID) {
        Optional<StrsRems> checkStrmRems = this.strsRemsRepository.findById(srID);
        StrsRems sr;
        sr = checkStrmRems.orElse(null);
        //elimino row strsRems
        this.strsRemsRepository.deleteById(sr.getId());
        //elimino row reminders
        this.remindersRepository.deleteById(sr.getId_reminder());

    }
}
