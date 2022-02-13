package com.example.calendar.entities;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrsRemsRepository extends CrudRepository<StrsRems, Integer> {

    @Query("SELECT * from StrsRems WHERE StrsRems.id_reminder = :id ")
    StrsRems findByReminderId(@Param("id") int id);

    @Query("SELECT * " +
            "FROM  StrsRems " +
            "join Reminders on Reminders.id = StrsRems.id_reminder and StrsRems.approved = 'Y' " +
            "join Structures on Structures.id = StrsRems.id_structure ")
    List<StrsRems> allStrsRmsApproved();

    @Query("SELECT * FROM  StrsRems WHERE approved = 'N' ")
    List<StrsRems> allStrsRmsNotApproved();

    @Query("SELECT * FROM  StrsRems WHERE approved = 'Y' and user_approvation = 'N' ")
    List<StrsRems> allStrsRmsNotApprovedFromUser();
}
