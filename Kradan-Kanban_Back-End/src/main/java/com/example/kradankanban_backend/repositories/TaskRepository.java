package com.example.kradankanban_backend.repositories;

import com.example.kradankanban_backend.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
//    @Query("select p from Product p where p.price >= :lowerPrice and p.price < :upperPrice and p.productName like :name")
//    List<Product> findByPriceAndName(Double lowerPrice, Double upperPrice, String name);
//    List<TaskEntity> updateTaskEntitiesByStatusBefore(String statusBefore , TaskEntity task);
    @Query("update TaskEntity set status = :newStatus where status like :oldStatus")
    List<TaskEntity> updateTaskStatus( String oldStatus,  String newStatus);

    boolean existsByStatus(String status);
}
