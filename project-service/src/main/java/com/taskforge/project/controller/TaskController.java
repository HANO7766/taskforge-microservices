package com.taskforge.project.controller;

import com.taskforge.common.dto.ApiResponse;
import com.taskforge.project.model.Task;
import com.taskforge.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(@PathVariable("projectId") UUID projectId, @RequestBody Task task) {
        Task created = taskService.createTask(projectId, task);
        return ResponseEntity.ok(ApiResponse.success("Tarea creada exitosamente", created));
    }
}
