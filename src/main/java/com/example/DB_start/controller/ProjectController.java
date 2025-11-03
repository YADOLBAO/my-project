package com.example.DB_start.controller;

import com.example.DB_start.model.Project;
import com.example.DB_start.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService service;

    //Сохранение проекта
    @PostMapping
    public String saveProject(@RequestBody Project project){
        service.saveProject(project);
        return "Проект успешно сохранен!";
    }

    //Поиск всех проектов
    @GetMapping
    public List<Project> findAllProjects(){
        return service.findAllProjects();
    }

    //Постраничный поиск всех проектов
    @GetMapping("/page")
    public Page<Project> findAllWithPagination(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size){
        return service.findAllWithPagination(page, size);
    }

    //Поиск по дате изменения
    @GetMapping("/updated")
    public List<Project> findByUpdatedAt(@RequestParam LocalDate updatedAt){
        return service.findByUpdatedAt(updatedAt);
    }

    //Поиск по id
    @GetMapping("/{id}")
    public Project findById(@PathVariable Long id){
        return service.findById(id);
    }

    //Поиск по статусу
    @GetMapping("/completed")
    public List<Project> findByCompleted(@RequestParam(defaultValue = "false") boolean completed){
        return service.findByCompleted(completed);
    }

    //Поиск по описанию или названию
    @GetMapping("/search")
    public List<Project> findByKeyword(@RequestParam String keyword){
        return service.findByKeyword(keyword);
    }

    //Поиск по дате создания
    @GetMapping("/created")
    public List<Project> findByCreatedAt(@RequestParam LocalDate createdAt){
        return service.findByCreatedAt(createdAt);
    }

    //Частичное обновление проекта
    @PatchMapping("/{id}")
    public String updateProject(@PathVariable Long id,
            @RequestBody Project project){
        project.setId(id);

        return service.updateProject(project)
                ? "Проект успешно обновлен"
                : "Проект с данным id не найден";
    }

    //Удаление проекта
    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id){
        if(service.deleteProject(id))
            return "Проект успешно удален!";
        else
            return "Проект с данным id не найден!";
    }

}
