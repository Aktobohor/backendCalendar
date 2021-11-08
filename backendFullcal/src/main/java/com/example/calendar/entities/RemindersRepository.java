package com.example.calendar.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindersRepository extends CrudRepository<Reminders, Integer> {



}
