package com.example.kradankanban_backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import java.time.LocalDateTime;
import java.util.TimeZone;


@Getter
@Setter
@Entity
@Table(name = "task", schema = "intergrate-kp-2", catalog = "")
public class TaskEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "assignees")
    private String assignees;

    @Column(name = "status")
    private Object status = "NO_STATUS";

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "createdOn", insertable = false, updatable = false)
    private LocalDateTime createdOn;
    
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "updatedOn", insertable = false, updatable = false)
    private LocalDateTime updatedOn;

    public void setTitle(String title) {
        if (title != null) {
            title = title.trim();
        }
        this.title = title;
    }

    public void setDescription(String description) {
        if (description != null) {
            description = description.trim();
        }
        this.description = description;
    }

    public void setAssignees(String assignees) {
        if (assignees != null) {
            assignees = assignees.trim();
        }
        this.assignees = assignees;
    }

    public void setStatus(Object status) {
        if (status == null) {
            status = "NO_STATUS";
        }
        this.status = status;
    }

}
