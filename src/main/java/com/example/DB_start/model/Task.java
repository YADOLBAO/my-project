package com.example.DB_start.model;

import com.example.DB_start.model.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;
}
