package com.iomoto.demo.vehicles;

import com.iomoto.demo.models.VehicleModel;
import com.iomoto.demo.repositories.VehicleRepository;
import com.iomoto.demo.services.VehicleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@DataMongoTest(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        MongoTemplate.class,
                        VehicleRepository.class,
                        VehicleService.class
                }
        )
)
@AutoConfigureDataMongo
public class VehicleServiceTest {

    @Autowired
    VehicleService vehicleService;

    @Container
    final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:4.0.10")
    );

    @BeforeEach
    void setUp() {
        mongoDBContainer.start();
    }

    @AfterEach
    void tearDown() {
        mongoDBContainer.stop();
    }

    @Test
    void testCrud() throws Exception {
        // test create
        // given
        var vehicle = new VehicleModel();
        vehicle.setName("abc");
        vehicle.setVin("123");
        vehicle.setLicensePlateNumber("xyz");
        var props = new HashMap<String, Object>();
        props.put("foo", "bar");
        vehicle.setProperties(props);

        // when
        var response = vehicleService.addVehicle(vehicle);

        // then
        assertEquals(response.getName(), vehicle.getName());
        assertNotNull(response.getId());

        // update
    }
}