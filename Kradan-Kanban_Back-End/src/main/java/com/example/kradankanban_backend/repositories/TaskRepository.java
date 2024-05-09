package com.example.kradankanban_backend.repositories;

import com.example.kradankanban_backend.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update TaskEntity set status = :newStatus where status like :oldStatus")
    void updateTaskStatus(@Param("oldStatus") String oldStatus,@Param("newStatus") String newStatus);

    boolean existsByStatus(String status);
}
