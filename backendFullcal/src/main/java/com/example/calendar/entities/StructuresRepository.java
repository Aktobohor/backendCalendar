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

    @Query(value = "SELECT * FROM Structures WHERE Structures.idQuestionary = :qId")
    Structures findFromQuestionId( @Param("qId") String qId);

    @Query(value = "SELECT * FROM Structures WHERE Structures.idTask = :tId")
    Structures findFromTaskId( @Param("tId") String tId);

    @Query(value = "SELECT * FROM Structures WHERE Structures.idChallenges = :cId")
    Structures findFromChallengeId( @Param("cId") String cId);

    @Query(value = "SELECT * FROM Structures WHERE Structures.idRandomTask = :rId")
    Structures findFromRandomTaskId( @Param("rId") String rId);
}
