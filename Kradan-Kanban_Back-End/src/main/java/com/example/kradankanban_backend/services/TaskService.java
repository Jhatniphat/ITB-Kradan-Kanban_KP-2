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
    @Autowired
    private TaskRepository taskRepository;

    public List<TaskEntity> findAll() {
        return repository.findAll();
    }

    public TaskEntity findById(int id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Task ID "+ id +" does not exist !!!"){
        });
    }

    @Transactional //Delete by id service
    public void deleteTask(int id) {
        TaskEntity task = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Task ID "+ id +" does not exist !!!"){
        });
        repository.delete(task);
    }

    @Transactional //Add new Task
    public TaskEntity addTask(TaskEntity task) { return repository.save(task); }

    @Transactional
    public TaskEntity updateTask(int id, TaskEntity task) {
        Integer taskId = task.getTaskId();
        if (taskId != null) {
            if (!taskId.equals(id)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                        "Conflict Office code  !!! (" + id + " vs "
                                + task.getTaskId() + ")");
            }
        }
        TaskEntity existingTask = repository.findById(id).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND , "Task ID "+ taskId + " does not exist !!!")
        );
        return repository.save(task);
    }


}
