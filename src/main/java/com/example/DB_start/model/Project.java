package com.example.DB_start.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private LocalDate createdAt = LocalDate.now();
    private boolean completed;
    private LocalDate updatedAt = LocalDate.now();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

}
