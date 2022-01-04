package com.example.calendar.entities;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructuresRepository extends CrudRepository<Structures, Integer> {

    @Query(value = "SELECT * FROM Reminders WHERE Reminders.id in :ids")
    List<Structures> findAllFromIDs( @Param("ids") List<Integer> allStrsRmsApproved);
}
