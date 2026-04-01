package com.itops.events.resource;

import com.itops.events.entity.Alarm;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.UUID;

@Path("/api/v1/alarms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlarmResource {

    private static final Logger logger = Logger.getLogger(AlarmResource.class);

    @GET
    public Response getAllAlarms() {
        var alarms = Alarm.listAll();
        return Response.ok(alarms).build();
    }

    @GET
    @Path("/{alarmId}")
    public Response getAlarm(@PathParam("alarmId") Long alarmId) {
        var alarm = Alarm.findById(alarmId);
        if (alarm == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(alarm).build();
    }

    @POST
    public Response createAlarm(Alarm alarm) {
        alarm.alarmId = UUID.randomUUID().toString();
        alarm.persist();
        logger.infof("Alarm created: %s", alarm.alarmId);
        return Response.status(Response.Status.CREATED).entity(alarm).build();
    }

    @PUT
    @Path("/{alarmId}")
    public Response updateAlarm(@PathParam("alarmId") Long alarmId, Alarm updatedAlarm) {
        var alarm = Alarm.findById(alarmId);
        if (alarm == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        alarm.alarmName = updatedAlarm.alarmName;
        alarm.description = updatedAlarm.description;
        alarm.severity = updatedAlarm.severity;
        alarm.status = updatedAlarm.status;
        alarm.assignedTo = updatedAlarm.assignedTo;
        alarm.persist();
        return Response.ok(alarm).build();
    }

    @PUT
    @Path("/{alarmId}/resolve")
    public Response resolveAlarm(@PathParam("alarmId") Long alarmId) {
        var alarm = Alarm.findById(alarmId);
        if (alarm == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        alarm.status = "RESOLVED";
        alarm.resolvedAt = LocalDateTime.now();
        alarm.persist();
        logger.infof("Alarm resolved: %s", alarm.alarmId);
        return Response.ok(alarm).build();
    }

    @GET
    @Path("/device/{deviceId}")
    public Response getAlarmsByDevice(@PathParam("deviceId") String deviceId) {
        var alarms = Alarm.list("deviceId", deviceId);
        return Response.ok(alarms).build();
    }

    @GET
    @Path("/status/{status}")
    public Response getAlarmsByStatus(@PathParam("status") String status) {
        var alarms = Alarm.list("status", status);
        return Response.ok(alarms).build();
    }

    @DELETE
    @Path("/{alarmId}")
    public Response deleteAlarm(@PathParam("alarmId") Long alarmId) {
        var deleted = Alarm.deleteById(alarmId);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
