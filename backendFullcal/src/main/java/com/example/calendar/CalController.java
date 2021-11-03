package com.example.calendar;

import com.example.calendar.DTOs.StructureDto;
import com.example.calendar.entities.Structures;
import com.example.calendar.services.StructuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class CalController {

    @Autowired
    private StructuresService structuresService;

    /*CalController(StructuresService structuresService){

        this.structuresService = structuresService;
    }*/

    int a = 1;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAllReminders")
    public List<StructureDto> getAllReminders(@RequestParam ("title") String title,
                                              @RequestParam ("freq") String freq,
                                              @RequestParam ("interval") String interval,
                                              @RequestParam ("byweekday") String byweekday,
                                              @RequestParam ("dtstart") String dtstart,
                                              @RequestParam ("until") String until) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Saluto", "Ciao Ciao "+title);
        map.put("freq", freq);

        System.out.println("prova "+a+"\ntitle:" +title+"\nfreq:" +freq+"\ninterval:" +interval
                +"\nbyweekday:" +byweekday+"\ndtstart:" +dtstart+"\nuntil:" +until);

        Structures s = new Structures(3,4,5,65);
        this.structuresService.save(s);

        List<StructureDto> all = this.structuresService.findAll();
        System.out.println("alalal");

        return all;
    }
}