package com.iomoto.demo.controllers;

import com.iomoto.demo.models.VehicleModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

import java.util.HashMap;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String resourceUrl = "http://localhost:" + port + "/api/v1/vehicles";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCrud() {
        // create
        var vehicle = new VehicleModel();
        vehicle.setName("abc");
        vehicle.setVin("123");
        vehicle.setLicensePlateNumber("xyz");
        var props = new HashMap<String, Object>();
        props.put("foo", "bar");
        vehicle.setProperties(props);
        var response = restTemplate.postForEntity(
                resourceUrl + "/add", new HttpEntity<>(vehicle), VehicleModel.class
        );
        assert response.getStatusCode().is2xxSuccessful();
        assert Objects.equals(response.getBody().getName(), "abc");
        assert Objects.equals(response.getBody().getVin(), "123");
        assert Objects.equals(response.getBody().getLicensePlateNumber(), "xyz");
        assert response.getBody().getProperties().containsKey("foo");
        assert response.getBody().getProperties().containsKey("bar");
        assert Objects.nonNull(response.getBody().getId());
        assert !response.getBody().getId().isBlank();
        assert !response.getBody().getId().isEmpty();
        // update
    }
}