package com.itops.core.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/api/v1/devices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeviceResource {

    private static final List<Device> devices = new ArrayList<>();

    static {
        devices.add(new Device(UUID.randomUUID().toString(), "Server-01", "LINUX", "ACTIVE"));
        devices.add(new Device(UUID.randomUUID().toString(), "Server-02", "WINDOWS", "ACTIVE"));
        devices.add(new Device(UUID.randomUUID().toString(), "Router-01", "NETWORK_DEVICE", "INACTIVE"));
    }

    @GET
    public Response getAllDevices() {
        return Response.ok(devices).build();
    }

    @GET
    @Path("/{deviceId}")
    public Response getDevice(@PathParam("deviceId") String deviceId) {
        return devices.stream()
                .filter(d -> d.id.equals(deviceId))
                .findFirst()
                .map(d -> Response.ok(d).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createDevice(Device device) {
        device.id = UUID.randomUUID().toString();
        devices.add(device);
        return Response.status(Response.Status.CREATED).entity(device).build();
    }

    @PUT
    @Path("/{deviceId}")
    public Response updateDevice(@PathParam("deviceId") String deviceId, Device device) {
        return devices.stream()
                .filter(d -> d.id.equals(deviceId))
                .findFirst()
                .map(d -> {
                    d.name = device.name;
                    d.type = device.type;
                    d.status = device.status;
                    return Response.ok(d).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{deviceId}")
    public Response deleteDevice(@PathParam("deviceId") String deviceId) {
        boolean removed = devices.removeIf(d -> d.id.equals(deviceId));
        return removed ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    public static class Device {
        public String id;
        public String name;
        public String type;
        public String status;

        public Device() {}

        public Device(String id, String name, String type, String status) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.status = status;
        }
    }
}