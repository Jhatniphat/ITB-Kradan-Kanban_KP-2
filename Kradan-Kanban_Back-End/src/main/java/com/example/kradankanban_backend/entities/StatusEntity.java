package com.example.kradankanban_backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "status", schema = "intergrate-kp-2")
public class StatusEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "statusName")
    private String name;

    @Column(name = "statusDescription")
    private String description;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "createdOn", insertable = false, updatable = false)
    private LocalDateTime createdOn;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    @Column(name = "updatedOn", insertable = false, updatable = false)
    private LocalDateTime updatedOn;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status" , cascade = CascadeType.ALL)
    private Set<TaskEntity> recordings = new HashSet<>();
}
