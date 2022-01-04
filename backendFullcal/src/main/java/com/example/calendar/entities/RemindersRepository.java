package com.example.calendar.entities;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemindersRepository extends CrudRepository<Reminders, Integer> {

    @Query(value = "SELECT * FROM Reminders as r join StrsRems as sr on r.id = sr.id_reminder and sr.approved = 'N' ")
    List<Reminders> findAllNotApproved();

    @Query(value = "SELECT * FROM Reminders as r join StrsRems as sr on r.id = sr.id_reminder and sr.approved = 'Y' ")
    List<Reminders> findAllApproved();

    @Query(value = "SELECT * FROM Reminders WHERE Reminders.id in :ids")
    List<Reminders> findAllFromIDs( @Param("ids") List<Integer> allStrsRmsApproved);
}
