package com.taskforge.project.service;

import com.taskforge.project.client.UserServiceClient;
import com.taskforge.project.event.TaskEvent;
import com.taskforge.project.event.TaskEventPublisher;
import com.taskforge.project.model.Project;
import com.taskforge.project.model.Task;
import com.taskforge.project.model.TaskStatus;
import com.taskforge.project.repository.ProjectRepository;
import com.taskforge.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskEventPublisher taskEventPublisher;

    @Autowired
    private UserServiceClient userServiceClient; // Para verificar asignados

    @Transactional
    public Task createTask(UUID projectId, Task task) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        
        task.setProject(project);

        // Validar si hay usuario asignado (simulando Feign Client)
        if (task.getAssigneeId() != null) {
            try {
                // Aquí en ambiente real validamos: boolean exists = userServiceClient.checkUserExists(task.getAssigneeId());
                System.out.println("Validando usuario asignado via Feign (simulado): " + task.getAssigneeId());
            } catch(Exception e) {
                System.err.println("Error al contactar User Service: " + e.getMessage());
            }
        }

        Task savedTask = taskRepository.save(task);

        // Patrón Observer: Publicar evento
        TaskEvent event = TaskEvent.builder()
                .taskId(savedTask.getId())
                .assigneeId(savedTask.getAssigneeId())
                .taskTitle(savedTask.getTitle())
                .eventType("CREATED")
                .build();
                
        taskEventPublisher.publishTaskEvent(event);

        return savedTask;
    }

    @Transactional
    public Task updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        
        task.setStatus(newStatus);
        
        if (newStatus == TaskStatus.DONE) {
            task.setCompletedAt(LocalDateTime.now());
        }

        Task updatedTask = taskRepository.save(task);

        TaskEvent event = TaskEvent.builder()
                .taskId(updatedTask.getId())
                .assigneeId(updatedTask.getAssigneeId())
                .taskTitle(updatedTask.getTitle())
                .eventType("STATUS_UPDATED_TO_" + newStatus.name())
                .build();
                
        taskEventPublisher.publishTaskEvent(event);

        return updatedTask;
    }
}
