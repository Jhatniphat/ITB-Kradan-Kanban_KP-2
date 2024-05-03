package com.example.kradankanban_backend.services;

import com.example.kradankanban_backend.dtos.DetailTaskDTO;
import com.example.kradankanban_backend.dtos.SimpleTaskDTO;
import com.example.kradankanban_backend.entities.TaskEntity;
import com.example.kradankanban_backend.exceptions.ItemNotFoundException;
import com.example.kradankanban_backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public List<TaskEntity> findAll() {
        return repository.findAll();
    }

    public TaskEntity findById(int id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Task ID "+ id +" does not exist !!!"){
        });
    }

    @Transactional
    public TaskEntity addTask(TaskEntity task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new ItemNotFoundException("Task title is null !!!");
        }
        if (task.getTitle().length() > 100) {
            throw new ItemNotFoundException("Task title length should be less than 100 !!!");
        }
        if (task.getDescription() != null && task.getDescription().length() > 500) {
            throw new ItemNotFoundException("Task description length should be less than 500 !!!");
        }
        if (task.getAssignees() != null && task.getAssignees().length() > 30) {
            throw new ItemNotFoundException("Task assignees length should be less than 30 !!!");
        }
        try {
            return repository.save(task);
        } catch (Exception e) {
            throw new ItemNotFoundException("Database Exception");
        }

    }

    @Transactional
    public SimpleTaskDTO deleteTask(int id) {
        TaskEntity task = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("NOT FOUND"));
        SimpleTaskDTO detailTaskDTO = modelMapper.map(task, SimpleTaskDTO.class);
        repository.delete(task);
        return detailTaskDTO;
    }

    @Transactional
    public TaskEntity editTask(int id, TaskEntity newTask) {
        TaskEntity task = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("NOT FOUND"));
        if (newTask.getTitle() == null || newTask.getTitle().isEmpty()) {
            throw new ItemNotFoundException("NOT FOUND");
        } else {
            newTask.setId(id);
            return repository.save(newTask);
        }
    }
}
