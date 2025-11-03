package com.example.DB_start.controller;

import com.example.DB_start.model.Task;
import com.example.DB_start.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    //Поиск всех задач
    @GetMapping
    public List<Task> findAllTasks(){
        return service.findAllTasks();
    }

    //Поиск задачи по id
    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id){
        return service.findById(id);
    }

    //Сохранение задачи
    @PostMapping
    public String saveTask(@RequestBody Task task,
                           @RequestParam Long projectId){

        service.saveTask(task, projectId);
        return "Задача успешно добавлена!";
    }

    //Поиск задач по проекту
    @GetMapping("/project")
    public List<Task> findByProjectId(@RequestParam Long projectId){
        return service.findByProjectId(projectId);
    }

    //Поиск по дате создания
    @GetMapping("/created")
    public List<Task> findByCreatedAt(@RequestParam LocalDate createdAt){
        return service.findByCreatedAt(createdAt);
    }

    //Поиск по дате изменения
    @GetMapping("/updated")
    public List<Task> findByUpdatedAt(@RequestParam LocalDate updatedAt){
        return service.findByUpdatedAt(updatedAt);
    }

    //Частичное обновление задачи
    @PatchMapping("/{id}")
    public String updateTask(@RequestBody Task task,
                             @PathVariable Long id){
        task.setId(id);

        return service.updateTask(task)
                ? "Задача успешно изменена"
                : "Задача с данным id не найдена";
    }

    //Удаление задачи
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        if(service.deleteTask(id))
            return "Задача успешно удалена!";
        else
            return "Задача с данным id не найдена!";
    }

    //Поиск задачи по статусу
    @GetMapping("/status")
    public List<Task> findByStatus(@RequestParam String status){
        return service.findByStatus(status);
    }

    //Поиск задачи по описанию или названию
    @GetMapping("/keyword")
    public List<Task> findByKeyword(@RequestParam String keyword){
        return service.findByKeyword(keyword);
    }

    //Постраничный поиск задач
    @GetMapping("/page")
    public Page<Task> findWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){

        return service.findWithPagination(page, size);
    }
}
