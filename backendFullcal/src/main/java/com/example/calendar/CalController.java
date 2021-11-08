package com.example.calendar;

import com.example.calendar.DTOs.StructuresDto;
import com.example.calendar.entities.Reminders;
import com.example.calendar.entities.StrsRems;
import com.example.calendar.services.RemindersService;
import com.example.calendar.services.StrsRemsService;
import com.example.calendar.services.StructuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class CalController {

    @Autowired
    private StructuresService structuresService;
    @Autowired
    private RemindersService remindersService;
    @Autowired
    private StrsRemsService str_rmdService;

    int a = 1;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAllReminders")
    public List<StructuresDto> getAllReminders(@RequestParam ("title") String title,
                                              @RequestParam ("dtstart") java.sql.Date dtstart,
                                              @RequestParam ("freq") String freq,
                                              @RequestParam ("interval") String interval,
                                              @RequestParam ("byweekday") String byweekday,
                                              @RequestParam ("until") String until,
                                              @RequestParam ("tzid") String tzid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Saluto", "Ciao Ciao "+title);
        map.put("freq", freq+"");

        System.out.println("prova "+a+"\ntitle:" +title+"\nfreq:" +freq+"\ninterval:" +interval
                +"\nbyweekday:" +byweekday+"\ndtstart:" +dtstart.toString()+"\nuntil:" +until.toString()+"\ntzid:"+tzid);

        //Structures s = new Structures(3,4,5,65);
        //this.structuresService.save(s);

        Double i =0.0;
        if(!interval.equals("")){
            i = Double.valueOf(interval);
        }
        //Reminders r = new Reminders(title, freq, dtstart, i, 1, 3, until, tzid, "", "", "","", byweekday, "", "", "");
        //this.remindersService.save(r);

        //DateTime attuale
        java.util.Date d = new java.util.Date();
        java.sql.Date sqlD = new java.sql.Date(d.getTime());

        StrsRems sr = new StrsRems(10/*s.getId()*/, 1/*r.getId()*/, "Mdezu@a.it", sqlD);
        this.str_rmdService.save(sr);

        List<StructuresDto> all = this.structuresService.findAll();
        System.out.println("alalal");

        return all;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/post")
    @ResponseBody
    public Reminders postResponseController(@RequestBody Reminders body) {
        System.out.println("body: "+body.getR_title());

        Reminders r = new Reminders(body.getR_title(), body.getR_freq(), body.getR_dt_start(), body.getR_interval(), 1, 3, body.getR_until(), body.getR_tzid(), "","",  body.getR_byweekday(), "", "", "","","");
        this.remindersService.save(r);


        return r;
    }
}
