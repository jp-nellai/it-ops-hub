package com.itops.core.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.micrometer.core.instrument.MeterRegistry;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/api/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {
    private final MeterRegistry meterRegistry;

    public HealthResource(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GET
    public RestResponse<HealthStatus> getHealth() {
        meterRegistry.counter("health.checks.total").increment();
        return RestResponse.ok(new HealthStatus("UP", "Core Service is healthy"));
    }

    public static class HealthStatus {
        public String status;
        public String message;

        public HealthStatus(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}