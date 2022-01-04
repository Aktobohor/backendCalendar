package com.example.calendar.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Structures")
public class Structures {

    @Column("id")
    private @Id int id;
    @Column("idQuestionary")
    private int idQuestionary;
    @Column("idTask")
    private int idTask;
    @Column("idChallenges")
    private int idChallenges;
    @Column("idRandomTask")
    private int idRandomTask;


    public Structures(int idQuestionary, int idTask, int idChallenges, int idRandomTask) {
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

    public int getIdQuestionary() {
        return idQuestionary;
    }

    public void setIdQuestionary(int idQuestionary) {
        this.idQuestionary = idQuestionary;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public int getIdChallenges() {
        return idChallenges;
    }

    public void setIdChallenges(int idChallenges) {
        this.idChallenges = idChallenges;
    }

    public int getIdRandomTask() {
        return idRandomTask;
    }

    public void setIdRandomTask(int idRandomTask) {
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
