package com.example.DB_start.repository;

import com.example.DB_start.model.Task;
import com.example.DB_start.model.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long ProjectId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    Page<Task> findAll(Pageable pageable);
}
