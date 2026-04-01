package com.itops.core.resource;

import io.quarkus.runtime.Quarkus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Path("/api/v1/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @GET
    public Response getHealth() {
        return Response.ok(new HealthStatus("UP", "Core Service is running")).build();
    }

    @GET
    @Path("/ready")
    public Response getReadiness() {
        return Response.ok(new HealthStatus("READY", "Core Service is ready to accept requests")).build();
    }

    @GET
    @Path("/live")
    public Response getLiveness() {
        return Response.ok(new HealthStatus("LIVE", "Core Service is alive")).build();
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