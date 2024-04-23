package com.example.kradankanban_backend.controllers;

import com.example.kradankanban_backend.entities.TaskEntity;
import com.example.kradankanban_backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping("")
    public List<TaskEntity> findAll(){
        return service.findAll();
    }
}
