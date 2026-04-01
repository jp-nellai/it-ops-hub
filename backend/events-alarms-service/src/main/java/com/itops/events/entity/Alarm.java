package com.itops.events.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alarms")
public class Alarm extends PanacheEntity {

    @Column(nullable = false)
    public String alarmId;

    @Column(nullable = false)
    public String deviceId;

    @Column(nullable = false)
    public String alarmName;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column
    public String severity;

    @Column
    public LocalDateTime triggerTime;

    @Column
    public LocalDateTime createdAt;

    @Column
    public LocalDateTime resolvedAt;

    @Column
    public String status;

    @Column
    public String assignedTo;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.triggerTime == null) {
            this.triggerTime = LocalDateTime.now();
        }
    }
}
