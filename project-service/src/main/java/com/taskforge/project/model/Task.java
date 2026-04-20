package com.taskforge.project.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private UUID assigneeId;
    private UUID reporterId;
    private Integer storyPoints;
    private LocalDateTime dueDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime completedAt;

    public Task() {}

    // Builder pattern manual
    private Task(Builder b) {
        this.title = b.title; this.description = b.description;
        this.status = b.status; this.priority = b.priority;
        this.project = b.project; this.assigneeId = b.assigneeId;
        this.reporterId = b.reporterId; this.storyPoints = b.storyPoints;
        this.dueDate = b.dueDate;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String title, description;
        private TaskStatus status;
        private TaskPriority priority;
        private Project project;
        private UUID assigneeId, reporterId;
        private Integer storyPoints;
        private LocalDateTime dueDate;

        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String d) { this.description = d; return this; }
        public Builder status(TaskStatus s) { this.status = s; return this; }
        public Builder priority(TaskPriority p) { this.priority = p; return this; }
        public Builder project(Project p) { this.project = p; return this; }
        public Builder assigneeId(UUID id) { this.assigneeId = id; return this; }
        public Builder reporterId(UUID id) { this.reporterId = id; return this; }
        public Builder storyPoints(Integer sp) { this.storyPoints = sp; return this; }
        public Builder dueDate(LocalDateTime d) { this.dueDate = d; return this; }
        public Task build() { return new Task(this); }
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public UUID getAssigneeId() { return assigneeId; }
    public void setAssigneeId(UUID assigneeId) { this.assigneeId = assigneeId; }
    public UUID getReporterId() { return reporterId; }
    public void setReporterId(UUID reporterId) { this.reporterId = reporterId; }
    public Integer getStoryPoints() { return storyPoints; }
    public void setStoryPoints(Integer storyPoints) { this.storyPoints = storyPoints; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
