package com.taskforge.project.service;

import com.taskforge.project.model.Project;
import com.taskforge.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {
        if (projectRepository.existsByCode(project.getCode())) {
            throw new RuntimeException("El código de proyecto ya existe");
        }
        return projectRepository.save(project);
    }

    public List<Project> getProjectsByOwner(UUID ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    public Project getProjectById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }
}
