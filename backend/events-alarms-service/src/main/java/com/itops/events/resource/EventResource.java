package com.itops.events.resource;

import com.itops.events.entity.Event;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.UUID;

@Path("/api/v1/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    private static final Logger logger = Logger.getLogger(EventResource.class);

    @GET
    public Response getAllEvents() {
        var events = Event.listAll();
        return Response.ok(events).build();
    }

    @GET
    @Path("/{eventId}")
    public Response getEvent(@PathParam("eventId") Long eventId) {
        var event = Event.findById(eventId);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(event).build();
    }

    @POST
    public Response createEvent(Event event) {
        event.eventId = UUID.randomUUID().toString();
        event.persist();
        logger.infof("Event created: %s", event.eventId);
        return Response.status(Response.Status.CREATED).entity(event).build();
    }

    @PUT
    @Path("/{eventId}")
    public Response updateEvent(@PathParam("eventId") Long eventId, Event updatedEvent) {
        var event = Event.findById(eventId);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        event.deviceId = updatedEvent.deviceId;
        event.eventType = updatedEvent.eventType;
        event.description = updatedEvent.description;
        event.severity = updatedEvent.severity;
        event.status = updatedEvent.status;
        event.persist();
        return Response.ok(event).build();
    }

    @PUT
    @Path("/{eventId}/acknowledge")
    public Response acknowledgeEvent(@PathParam("eventId") Long eventId, AcknowledgeRequest request) {
        var event = Event.findById(eventId);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        event.acknowledged = true;
        event.acknowledgedBy = request.acknowledgedBy;
        event.acknowledgedAt = LocalDateTime.now();
        event.persist();
        logger.infof("Event acknowledged: %s by %s", event.eventId, request.acknowledgedBy);
        return Response.ok(event).build();
    }

    @GET
    @Path("/device/{deviceId}")
    public Response getEventsByDevice(@PathParam("deviceId") String deviceId) {
        var events = Event.list("deviceId", deviceId);
        return Response.ok(events).build();
    }

    @GET
    @Path("/severity/{severity}")
    public Response getEventsBySeverity(@PathParam("severity") String severity) {
        var events = Event.list("severity", severity);
        return Response.ok(events).build();
    }

    @DELETE
    @Path("/{eventId}")
    public Response deleteEvent(@PathParam("eventId") Long eventId) {
        var deleted = Event.deleteById(eventId);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    public static class AcknowledgeRequest {
        public String acknowledgedBy;
    }
}
