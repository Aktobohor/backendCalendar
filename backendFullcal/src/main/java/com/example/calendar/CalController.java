package com.example.calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalController {

    @GetMapping("/")
    public String index() {
        System.out.println("Magic!");
        return "Greetings from Spring Boot!";
    }
}