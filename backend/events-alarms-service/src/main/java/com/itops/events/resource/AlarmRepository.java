package com.itops.events.resource;

import com.itops.events.entity.Alarm;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlarmRepository implements PanacheRepository<Alarm> {
}
