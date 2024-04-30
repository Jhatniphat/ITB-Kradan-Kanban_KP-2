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
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "assignees")
    private String assignees;
    @Basic
    @Column(name = "status")
    private Object status;
    @Basic
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    @Basic
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "updatedOn")
    private LocalDateTime updatedOn;

}
