package com.example.DB_start.service;

import com.example.DB_start.model.Project;
import com.example.DB_start.model.Task;
import com.example.DB_start.model.enums.TaskStatus;
import com.example.DB_start.repository.ProjectRepository;
import com.example.DB_start.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TasksRepository tasksRepository;
    private final ProjectRepository projectRepository;

    //Поиск всех задач
    public List<Task> findAllTasks(){
        return tasksRepository.findAll();
    }

    //Поиск задачи по id
    public Task findById(Long id){
        return tasksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача с данным id не найдена!"));
    }

    //Сохранение задачи
    public void saveTask(Task task, Long projectId){

        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new RuntimeException("Проект с данным id не найден!"));

        task.setProject(project);
        tasksRepository.save(task);
    }

    //Поиск задачи по id его проекта
    public List<Task> findByProjectId(Long projectId){

        return tasksRepository.findByProjectId(projectId);
    }

    //Поиск задачи по дате создания
    public List<Task> findByCreatedAt(LocalDate createdAt){
        return tasksRepository.findByCreatedAt(createdAt);
    }

    //Поиск задачи по дате изменения
    public List<Task> findByUpdatedAt(LocalDate updatedAt){
        return tasksRepository.findByUpdatedAt(updatedAt);
    }

    //Частичное обновление задачи
    public boolean updateTask(Task task){
        return tasksRepository.findById(task.getId())
                .map(existingTask -> {

                    String title = task.getTitle();
                    if(title != null && !title.isEmpty())
                        existingTask.setTitle(title);

                    String description = task.getDescription();
                    if(description != null && !description.isEmpty())
                        existingTask.setDescription(description);

                    TaskStatus status = task.getStatus();
                    if(status != null)
                        existingTask.setStatus(status);

                    Project project = task.getProject();
                    if(project != null)
                        existingTask.setProject(project);

                    existingTask.setUpdatedAt(LocalDate.now());

                    tasksRepository.save(existingTask);
                    return true;
                }).
                orElse(false);

    }

    //Удаление задачи
    public boolean deleteTask(Long id){

        if(tasksRepository.findById(id).isPresent()){
            tasksRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

    //Поиск задачи по статусу
    public List<Task> findByStatus(String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return tasksRepository.findByStatus(taskStatus);
    }

    //Поиск задачи по названию или описанию
    public List<Task> findByKeyword(String keyword){
        return tasksRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    //Постраничный поиск всех задач
    public Page<Task> findWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return tasksRepository.findAll(pageable);
    }

}
