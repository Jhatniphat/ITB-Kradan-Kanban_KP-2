package com.example.kradankanban_backend.services;

import com.example.kradankanban_backend.entities.TaskEntity;
import com.example.kradankanban_backend.exceptions.ItemNotFoundException;
import com.example.kradankanban_backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
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
        try {
            return repository.save(task);
        } catch (Exception e) {
            throw new ItemNotFoundException("Task title is incorrect !!!");
        }

    }

    @Transactional
    public void deleteTask(int id) {
        TaskEntity task = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("NOT FOUND"));
        repository.delete(task);
        try {
            repository.delete(task);
        } catch (Exception e) {
            throw new ItemNotFoundException("Task id is incorrect !!!");
        }
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
