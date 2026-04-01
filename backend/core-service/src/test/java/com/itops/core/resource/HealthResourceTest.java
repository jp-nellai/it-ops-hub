package com.itops.core.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthResourceTest {

    private HealthResource healthResource;

    @BeforeEach
    void setUp() {
        healthResource = new HealthResource();
    }

    @Test
    void testHealthCheck() {
        String result = healthResource.healthCheck();
        assertEquals("Healthy", result, "Health check should return 'Healthy'");
    }

    @Test
    void testHealthEndpointIsAccessible() {
        // Code to test if the health endpoint is accessible
        // This could involve calling the endpoint and ensuring a successful response
    }

    // Add more tests as needed
}