package com.example.kradankanban_backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


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
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC+0")
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    @Basic
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "updatedOn")
    private LocalDateTime updatedOn;

}
