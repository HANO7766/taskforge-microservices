package com.taskforge.notification.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    private Boolean read = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Notification() {}

    private Notification(Builder b) {
        this.userId = b.userId;
        this.title = b.title;
        this.message = b.message;
        this.type = b.type;
        this.read = b.read;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private UUID userId;
        private String title, message;
        private NotificationType type;
        private Boolean read = false;

        public Builder userId(UUID id) { this.userId = id; return this; }
        public Builder title(String t) { this.title = t; return this; }
        public Builder message(String m) { this.message = m; return this; }
        public Builder type(NotificationType t) { this.type = t; return this; }
        public Builder read(Boolean r) { this.read = r; return this; }
        public Notification build() { return new Notification(this); }
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }
    public Boolean getRead() { return read; }
    public void setRead(Boolean read) { this.read = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
