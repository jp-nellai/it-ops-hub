package com.itops.events.entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "events")
public class Event extends PanacheEntity {
    @Column(nullable = false)
    public String eventId;
    @Column(nullable = false)
    public String deviceId;
    @Column(nullable = false)
    public String eventType;
    @Column
    public String eventSource;
    @Column(columnDefinition = "TEXT")
    public String description;
    @Column
    public String severity;
    @Column
    public LocalDateTime occurredAt;
    @Column
    public LocalDateTime createdAt;
    @Column
    public Boolean acknowledged;
    @Column
    public String acknowledgedBy;
    @Column
    public LocalDateTime acknowledgedAt;
    @Column
    public String status;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.acknowledged = false;
        if (this.occurredAt == null) {
            this.occurredAt = LocalDateTime.now();
        }
    }
}