package com.app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "created_at", updatable = false)

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
