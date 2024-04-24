package com.example.kradankanban_backend.services;

import com.example.kradankanban_backend.dtos.TaskDTO;
import com.example.kradankanban_backend.entities.TaskEntity;
import com.example.kradankanban_backend.exceptions.ItemNotFoundException;
import com.example.kradankanban_backend.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
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
}
