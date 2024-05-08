package com.example.kradankanban_backend.services;

import com.example.kradankanban_backend.entities.StatusEntity;
import com.example.kradankanban_backend.entities.TaskEntity;
import com.example.kradankanban_backend.exceptions.ItemNotFoundException;
import com.example.kradankanban_backend.repositories.StatusRepository;
import com.example.kradankanban_backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    StatusRepository repository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    public List<StatusEntity> getAll() {
        return repository.findAll();
    }

    public StatusEntity getById(int id) {
        return repository.findById(id).orElseThrow( () -> new ItemNotFoundException("No status found with id: " + id) );
    }

    // * addStatus
    @Transactional
    public StatusEntity addStatus(StatusEntity status) {
        if (status.getStatusName().trim().isEmpty() || status.getStatusName() == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Status Name is null !!!");
        }
        if (status.getStatusName().trim().length() > 50) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST , "Status Name length should be less than 20 !!!");
        }
        if (status.getStatusDescription().trim().length() > 200) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST , "Status Description length should be less than 200 !!!");
        }
        // ? เช็คว่ามี statusName นั้นหรือยัง
        if (repository.existsByStatusName(status.getStatusName())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST , "Status Name already exists !!!");
        }
        try {
            return repository.save(status);
        } catch (Exception e) {
            throw new ItemNotFoundException("Database Exception");
        }
    }

    // * editStatus
    @Transactional
    public StatusEntity editStatus(int id, StatusEntity status) {
        if (status.getStatusName().trim().isEmpty() || status.getStatusName() == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Status Name is null !!!");
        }
        if (status.getStatusName().trim().length() > 50) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST , "Status Name length should be less than 20 !!!");
        }
        if (status.getStatusDescription().trim().length() > 200) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST , "Status Description length should be less than 200 !!!");
        }
        try {
            String oldStatusName = repository.findById(id).orElseThrow().getStatusName();
            if( !status.getStatusName().trim().equals(oldStatusName) ) {
                taskRepository.updateTaskStatus(oldStatusName , status.getStatusName());
            }
            status.setId(id);
            return repository.save(status);
        } catch (Exception e) {
            throw new ItemNotFoundException("Database Exception");
        }
    }

    // * deleteStatus
    public StatusEntity deleteStatus(int id) {
        StatusEntity status = repository.findById(id).orElseThrow( () -> new ItemNotFoundException("No status found with id: " + id) );
        if (status.getStatusName() == "No Status") {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Cannot delete 'No Status'!!!");
        }
        try {
            repository.delete(status);
            return status;
        }catch (Exception e){
            throw new ItemNotFoundException("Database Exception");
        }
    }
    // * transferStatus
    public StatusEntity transferStatus (int oldId, int newId){
        if (oldId == newId) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST , "Old Status Id is equal to new Status Id !!!");
        }
        if (!repository.existsById(oldId)) {
            throw new ItemNotFoundException("No status found with id: " + oldId);
        }
        if (!repository.existsById(newId)) {
            throw new ItemNotFoundException("No status found with id: " + newId);
        }
        StatusEntity oldStatus = repository.findById(oldId).orElseThrow();
        String newStatusName = repository.findById(newId).orElseThrow().getStatusName();
        try {
            taskRepository.updateTaskStatus(oldStatus.getStatusName() , newStatusName);
            repository.delete(oldStatus);
            return oldStatus;
        }catch (Exception e){
            throw new ItemNotFoundException("Database Exception");
        }
    }
}
