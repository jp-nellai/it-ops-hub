package com.itops.metrics.resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/metrics")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MetricsResource {

    private static final Logger logger = Logger.getLogger(MetricsResource.class);

    @Inject
    MeterRegistry meterRegistry;

    private static final Map<String, SystemMetric> metrics = new HashMap<>();

    static {
        metrics.put("cpu_usage", new SystemMetric("cpu_usage", 45.2, "percent"));
        metrics.put("memory_usage", new SystemMetric("memory_usage", 72.5, "percent"));
        metrics.put("disk_usage", new SystemMetric("disk_usage", 88.3, "percent"));
        metrics.put("network_in", new SystemMetric("network_in", 125.6, "Mbps"));
        metrics.put("network_out", new SystemMetric("network_out", 87.4, "Mbps"));
    }

    @GET
    public Response getAllMetrics() {
        logger.info("Fetching all metrics");
        return Response.ok(metrics.values()).build();
    }

    @GET
    @Path("/{metricName}")
    public Response getMetric(@PathParam("metricName") String metricName) {
        SystemMetric metric = metrics.get(metricName);
        if (metric == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(metric).build();
    }

    @POST
    public Response recordMetric(SystemMetric metric) {
        metric.timestamp = LocalDateTime.now();
        metrics.put(metric.name, metric);
        logger.infof("Metric recorded: %s = %f", metric.name, metric.value);
        meterRegistry.gauge(metric.name, metric.value);
        return Response.status(Response.Status.CREATED).entity(metric).build();
    }

    @GET
    @Path("/prometheus")
    @Produces("text/plain")
    public Response getPrometheus() {
        StringBuilder sb = new StringBuilder();
        metrics.forEach((name, metric) -> {
            sb.append(String.format("# HELP %s %s\n", name, metric.unit));
            sb.append(String.format("# TYPE %s gauge\n", name));
            sb.append(String.format("%s %f\n", name, metric.value));
        });
        return Response.ok(sb.toString()).build();
    }

    @GET
    @Path("/device/{deviceId}")
    public Response getDeviceMetrics(@PathParam("deviceId") String deviceId) {
        logger.infof("Fetching metrics for device: %s", deviceId);
        List<SystemMetric> deviceMetrics = new ArrayList<>();
        metrics.forEach((name, metric) -> {
            if (name.contains(deviceId.toLowerCase())) {
                deviceMetrics.add(metric);
            }
        });
        return Response.ok(deviceMetrics).build();
    }

    @GET
    @Path("/health/system")
    public Response getSystemHealth() {
        double cpuUsage = metrics.get("cpu_usage").value;
        double memoryUsage = metrics.get("memory_usage").value;
        double diskUsage = metrics.get("disk_usage").value;

        String health = "HEALTHY";
        if (cpuUsage > 90 || memoryUsage > 90 || diskUsage > 95) {
            health = "CRITICAL";
        } else if (cpuUsage > 75 || memoryUsage > 75 || diskUsage > 85) {
            health = "WARNING";
        }

        return Response.ok(new HealthStatus(health, cpuUsage, memoryUsage, diskUsage)).build();
    }

    public static class SystemMetric {
        public String name;
        public double value;
        public String unit;
        public LocalDateTime timestamp;

        public SystemMetric() {}

        public SystemMetric(String name, double value, String unit) {
            this.name = name;
            this.value = value;
            this.unit = unit;
            this.timestamp = LocalDateTime.now();
        }
    }

    public static class HealthStatus {
        public String status;
        public double cpuUsage;
        public double memoryUsage;
        public double diskUsage;

        public HealthStatus(String status, double cpu, double memory, double disk) {
            this.status = status;
            this.cpuUsage = cpu;
            this.memoryUsage = memory;
            this.diskUsage = disk;
        }
    }
}