package com.example.DB_start.repository;

import com.example.DB_start.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    List<Project> findByCompleted(boolean completed);

    List<Project> findByCreatedAt(LocalDate ceratedAt);

    List<Project> findByUpdatedAt(LocalDate updatedAt);

}
