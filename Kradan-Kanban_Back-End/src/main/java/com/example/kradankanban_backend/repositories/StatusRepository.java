package com.example.kradankanban_backend.repositories;

import com.example.kradankanban_backend.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {

    boolean existsByName(String name);
}
