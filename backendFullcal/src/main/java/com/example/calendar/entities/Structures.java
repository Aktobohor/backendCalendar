package com.example.calendar.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Structures")
public class Structures {

    @Column("id")
    private @Id int id;
    @Column("idQuestionary")
    private String idQuestionary;
    @Column("idTask")
    private String idTask;
    @Column("idChallenges")
    private String idChallenges;
    @Column("idRandomTask")
    private String idRandomTask;


    public Structures(String idQuestionary, String idTask, String idChallenges, String idRandomTask) {
        this.idQuestionary = idQuestionary;
        this.idTask = idTask;
        this.idChallenges = idChallenges;
        this.idRandomTask = idRandomTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdQuestionary() {
        return idQuestionary;
    }

    public void setIdQuestionary(String idQuestionary) {
        this.idQuestionary = idQuestionary;
    }

    public String getIdTask() {
        return idTask;
    }

    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public String getIdChallenges() {
        return idChallenges;
    }

    public void setIdChallenges(String idChallenges) {
        this.idChallenges = idChallenges;
    }

    public String getIdRandomTask() {
        return idRandomTask;
    }

    public void setIdRandomTask(String idRandomTask) {
        this.idRandomTask = idRandomTask;
    }

    @Override
    public String toString() {
        return "Structures{" +
                "id=" + id +
                ", idQuestionary=" + idQuestionary +
                ", idTask=" + idTask +
                ", idChallenges=" + idChallenges +
                ", idRandomTask=" + idRandomTask +
                '}';
    }
}
