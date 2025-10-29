package com.example.DB_start.controller;

import com.example.DB_start.model.Task;
import com.example.DB_start.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public List<Task> findAllTasks(){
        return service.findAllTasks();
    }

    @PostMapping
    public String saveTask(@RequestBody Task task,
                           @RequestParam Long projectId){

        service.saveTask(task, projectId);
        return "Задача успешно добавлена!";
    }

    @GetMapping("/findByProjectId")
    public List<Task> findByProjectId(@RequestParam Long projectId){
        return service.findByProjectId(projectId);
    }

    @PatchMapping
    public String updateTask(@RequestBody Task task,
                             @RequestParam Long id){
        if(service.updateTask(task, id))
            return "Задача успешно обновлена!";
        else
            return "Задача с данным id не найдена!";
    }

    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id){
        if(service.deleteTask(id))
            return "Задача успешно удалена!";
        else
            return "Задача с данным id не найдена!";
    }

    @GetMapping("/findByStatus")
    public List<Task> findByStatus(@RequestParam String status){
        return service.findByStatus(status);
    }

    @GetMapping("/findByKeyword")
    public List<Task> findByKeyword(@RequestParam String keyword){
        return service.findByKeyword(keyword);
    }

    @GetMapping("/findAllTasksWithPagination")
    public Page<Task> findAllTasksWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){

        return service.findAllTasksWithPagination(page, size);
    }
}
