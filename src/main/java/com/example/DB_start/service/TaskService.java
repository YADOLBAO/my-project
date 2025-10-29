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

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TasksRepository tasksRepository;
    private final ProjectRepository projectRepository;

    public List<Task> findAllTasks(){
        return tasksRepository.findAll();
    }

    public void saveTask(Task task, Long projectId){

        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new RuntimeException("Проект с данным id не найден!"));

        task.setProject(project);
        tasksRepository.save(task);
    }

    public List<Task> findByProjectId(Long projectId){

        return tasksRepository.findByProjectId(projectId);
    }

    public boolean updateTask(Task task, Long id){
        return tasksRepository.findById(id)
                .map(existingTask -> {
                    if(task.getTitle() != null && !task.getTitle().isEmpty())
                        existingTask.setTitle(task.getTitle());

                    if(task.getDescription() != null && !task.getDescription().isEmpty())
                        existingTask.setDescription(task.getDescription());

                    existingTask.setCompleted(task.isCompleted());

                    if(task.getStatus() != null)
                        existingTask.setStatus(task.getStatus());

                    if(task.getProject() != null)
                        existingTask.setProject(task.getProject());

                    tasksRepository.save(existingTask);
                    return true;
                }).
                orElse(false);

    }

    public boolean deleteTask(Long id){

        if(tasksRepository.findById(id).isPresent()){
            tasksRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

    public List<Task> findByStatus(String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return tasksRepository.findByStatus(taskStatus);
    }

    public List<Task> findByKeyword(String keyword){
        return tasksRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    public Page<Task> findAllTasksWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return tasksRepository.findAll(pageable);
    }

}
