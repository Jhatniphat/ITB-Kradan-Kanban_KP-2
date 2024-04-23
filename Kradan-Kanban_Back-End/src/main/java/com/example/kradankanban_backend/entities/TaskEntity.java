package com.example.kradankanban_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "task", schema = "intergrate-kp-2", catalog = "")
public class TaskEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "taskId")
    private int taskId;
    @Basic
    @Column(name = "taskTitle")
    private String taskTitle;
    @Basic
    @Column(name = "taskDescription")
    private String taskDescription;
    @Basic
    @Column(name = "taskAssignees")
    private String taskAssignees;
    @Basic
    @Column(name = "taskStatus")
    private Object taskStatus;
    @Basic
    @Column(name = "createdOn")
    private Timestamp createdOn;
    @Basic
    @Column(name = "updatedOn")
    private Timestamp updatedOn;

}
