package com.taskforge.project.service;

import com.taskforge.project.client.UserServiceClient;
import com.taskforge.project.event.TaskEvent;
import com.taskforge.project.event.TaskEventPublisher;
import com.taskforge.project.model.Project;
import com.taskforge.project.model.Task;
import com.taskforge.project.repository.ProjectRepository;
import com.taskforge.project.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskEventPublisher taskEventPublisher;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private TaskService taskService;

    private Project sampleProject;
    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleProject = new Project();
        sampleProject.setId(UUID.randomUUID());
        sampleProject.setName("Test Project");

        sampleTask = new Task();
        sampleTask.setId(UUID.randomUUID());
        sampleTask.setTitle("Test Task");
        sampleTask.setAssigneeId(UUID.randomUUID());
    }

    @Test
    void createTask_Success_PublishesEvent() {
        // Arrange
        when(projectRepository.findById(sampleProject.getId())).thenReturn(Optional.of(sampleProject));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // Act
        Task createdTask = taskService.createTask(sampleProject.getId(), sampleTask);

        // Assert
        assertNotNull(createdTask);
        assertEquals(sampleProject, createdTask.getProject());
        
        // Verifica que se haya guardado
        verify(taskRepository, times(1)).save(sampleTask);
        
        // Verifica que se haya publicado el evento (Patrón Observer)
        verify(taskEventPublisher, times(1)).publishTaskEvent(any(TaskEvent.class));
    }

    @Test
    void createTask_ProjectNotFound_ThrowsException() {
        // Arrange
        when(projectRepository.findById(sampleProject.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            taskService.createTask(sampleProject.getId(), sampleTask);
        });

        // Verifica que NO se guardó ni se publicó evento
        verify(taskRepository, never()).save(any(Task.class));
        verify(taskEventPublisher, never()).publishTaskEvent(any(TaskEvent.class));
    }
}
