package com.example.calendar.entities;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewRemindersRepository extends CrudRepository<NewReminder, Integer>{

    @Query(value = "SELECT * FROM Reminders as r join StrsRems as sr on r.id = sr.id_reminder and sr.approved = 'N' join Structures as st on st.id = sr.id_structure;")
    List<NewReminder> findAllNotApprovedfullData();

    @Query(value = "SELECT * FROM Reminders as r join StrsRems as sr on r.id = sr.id_reminder and sr.approved = 'Y' join Structures as st on st.id = sr.id_structure;")
    List<NewReminder> findAllApprovedfullData();

    @Query(value = "SELECT * FROM Reminders as r join StrsRems as sr on r.id = sr.id_reminder and sr.approved = 'Y' join Structures as st on st.id = sr.id_structure WHERE Reminders.id in :ids;")
    List<NewReminder> findAllFromfullDataIDs( @Param("ids") List<Integer> allStrsRmsApproved);
}
