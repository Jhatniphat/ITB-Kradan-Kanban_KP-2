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
    
    @Column(name = "taskTitle")
    private String taskTitle;
    
    @Column(name = "taskDescription")
    private String taskDescription;
    
    @Column(name = "taskAssignees")
    private String taskAssignees;
    
    
    @Column(name = "taskStatus")
    private Object taskStatus;
    
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "createdOn", insertable = false, updatable = false)
    private LocalDateTime createdOn;
    
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "updatedOn", insertable = false, updatable = false)
    private LocalDateTime updatedOn;

}
