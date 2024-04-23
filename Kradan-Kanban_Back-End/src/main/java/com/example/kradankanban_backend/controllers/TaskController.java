package com.example.kradankanban_backend.controllers;

import com.example.kradankanban_backend.dtos.TaskDTO;
import com.example.kradankanban_backend.entities.TaskEntity;
import com.example.kradankanban_backend.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Tasks")
public class TaskController {
    @Autowired
    private TaskService service;
    @Autowired
    private ModelMapper modelMapper;

    //    @GetMapping("")
//    public List<TaskEntity> findAll(){
//        return service.findAll();
//    }
    @GetMapping("") //DTO
    public ResponseEntity<Object> getAllTasks() {
        List<TaskEntity> tasks = service.findAll();
        List<TaskDTO> taskDTOS = tasks.stream().map(p -> modelMapper.map(p, TaskDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOS);
    }
}
