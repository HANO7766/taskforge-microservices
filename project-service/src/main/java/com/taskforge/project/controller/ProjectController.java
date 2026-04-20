package com.taskforge.project.controller;

import com.taskforge.common.dto.ApiResponse;
import com.taskforge.project.model.Project;
import com.taskforge.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<Project>> createProject(@RequestBody Project project) {
        Project created = projectService.createProject(project);
        return ResponseEntity.ok(ApiResponse.success("Proyecto creado exitosamente", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Project>> getProject(@PathVariable("id") UUID id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success("Proyecto obtenido", project));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<Project>>> getProjectsByOwner(@PathVariable("ownerId") UUID ownerId) {
        List<Project> projects = projectService.getProjectsByOwner(ownerId);
        return ResponseEntity.ok(ApiResponse.success("Proyectos del owner", projects));
    }
}
