package com.example.DB_start.service;

import com.example.DB_start.model.Project;
import com.example.DB_start.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    //Сохранение проекта
    public void saveProject(Project project) {
        repository.save(project);
    }

    //Поиск всех проектов
    public List<Project> findAllProjects(){
        return repository.findAll();
    }

    //Постраничный поиск всех проектов
    public Page<Project> findAllWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    //Поиск проекта по id
    public Project findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Проект с данным id не найден!"));
    }

    //Поиск проекта по статусу
    public List<Project> findByCompleted(boolean completed){
        return repository.findByCompleted(completed);
    }

    //Поиск проекта по описанию или названию
    public List<Project> findByKeyword(String keyword){
        return repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    //Поиск по дате создания
    public List<Project> findByCreatedAt(LocalDate createdAt){
        return repository.findByCreatedAt(createdAt);
    }

    //Поиск проекта по дате изменения
    public List<Project> findByUpdatedAt(LocalDate updatedAt){
        return repository.findByUpdatedAt(updatedAt);
    }

    //Частичное обновление проекта
    public boolean updateProject(Project project){
        return repository.findById(project.getId())
                .map(existingProject -> {
                    String name = project.getName();
                    if(name != null && !name.isEmpty())
                        existingProject.setName(name);

                    String description = project.getDescription();
                    if(description != null && !description.isEmpty())
                        existingProject.setDescription(description);

                    existingProject.setUpdatedAt(LocalDate.now());
                    repository.save(existingProject);
                    return true;
                }).
                orElse(false);
    }

    //Удаление проекта
    public boolean deleteProject(Long id) {

        return repository.findById(id)
                .map(p -> {
                    repository.delete(p);
                    return true;
                })
                .orElse(false);

    }
}
