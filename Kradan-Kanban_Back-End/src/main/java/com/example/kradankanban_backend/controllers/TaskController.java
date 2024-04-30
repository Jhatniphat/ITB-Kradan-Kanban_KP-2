package com.example.kradankanban_backend.controllers;

import com.example.kradankanban_backend.dtos.TaskDTO;
import com.example.kradankanban_backend.entities.TaskEntity;
import com.example.kradankanban_backend.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins = "http://ip23kp2.sit.kmutt.ac.th:80")
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ServerProperties serverProperties;

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

    @GetMapping("/{id}") //Get Task By ID
    public TaskEntity getTaskById(@PathVariable int id) {
        return service.findById(id);
    }

//    @PostMapping("") //Add
//    public TaskEntity addTask(@RequestBody TaskEntity taskEntity) {
//        return service.addTask(taskEntity);
//    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody TaskEntity task) {
        TaskEntity createdTask = service.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable int id) {
        service.deleteTask(id);
    }

}
