package com.example.calendar.utils;

import com.example.calendar.DTOs.NewReminderDto;
import com.example.calendar.DTOs.ReminderDto;
import com.google.ical.compat.jodatime.LocalDateIterable;
import com.google.ical.compat.jodatime.LocalDateIteratorFactory;
import com.google.ical.values.DateValueImpl;
import com.google.ical.values.Frequency;
import com.google.ical.values.RRule;
import org.joda.time.LocalDate;

import java.time.LocalDateTime;

public class DateGenerator {

    private Frequency getFrequencyFromString(String freq) {
        switch (freq) {
            case "RRule.DAILY": {
                return Frequency.DAILY;
            }
            case "RRule.WEEKLY": {
                return Frequency.WEEKLY;
            }
            case "RRule.MONTHLY": {
                return Frequency.MONTHLY;
            }
            case "RRule.YEARLY": {
                return Frequency.YEARLY;
            }
            default: {
                return Frequency.HOURLY;
            }
        }
    }

    public LocalDateIterable dateListFromNewRRuleInReminderDTO(NewReminderDto newReminder){

        ReminderDto r = newReminder.getReminderDto();

        LocalDateTime inizio = r.getR_dt_start();
        LocalDateTime fine = r.getR_until();

        LocalDate start = new LocalDate(inizio.getYear(), inizio.getMonthValue(), inizio.getDayOfMonth());

        RRule rrule2 = new RRule();
        rrule2.setFreq(getFrequencyFromString(r.getR_freq()));
        rrule2.setInterval(r.getR_interval());
        rrule2.setUntil(new DateValueImpl(fine.getYear(), fine.getMonthValue(), fine.getDayOfMonth()));
        System.out.println("dataInizio "+start);

        String ical = rrule2.toIcal();
        System.out.println("RRule "+ical);
        LocalDateIterable dateListFromRRule = null;
        try{
            dateListFromRRule  = LocalDateIteratorFactory.createLocalDateIterable(ical, start, true);
            for (LocalDate date : dateListFromRRule) {
                System.out.println(date);
            }
        }catch (java.text.ParseException e){
            System.out.println("Errore"+e.getMessage());
        }
        return dateListFromRRule;
    }

}
