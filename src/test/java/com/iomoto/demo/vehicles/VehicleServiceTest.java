package com.iomoto.demo.vehicles;

import com.iomoto.demo.exceptions.ResourceNotFoundException;
import com.iomoto.demo.models.VehicleModel;
import com.iomoto.demo.services.VehicleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@DataMongoTest(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        VehicleService.class
                }
        )
)
@Testcontainers
@ContextConfiguration(initializers = VehicleServiceTest.Initializer.class)
public class VehicleServiceTest {

    @Autowired
    VehicleService vehicleService;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:4.0.10")
    );

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    configurableApplicationContext,
                    format(
                            "spring.data.mongodb.uri=mongodb://%s:%s/testDB",
                            mongoDBContainer.getContainerIpAddress(),
                            mongoDBContainer.getMappedPort(27017)
                    )
            );
        }
    }

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

        // test get
        // when
        response = vehicleService.getVehicleById(response.getId());

        // then
        assertEquals(response.getVin(), "123");

        // test get all
        // when
        var pageableResponse = vehicleService.getAllVehicles(PageRequest.of(0, 999));
        // then
        assertEquals(pageableResponse.stream().count(), 1);

        // test update
        // given
        var newProps = new HashMap<String, Object>();
        newProps.put("horsePower", 3.5);
        vehicle.setProperties(newProps);

        // when
        response = vehicleService.updateVehicle(vehicle);

        // then
        assertTrue(response.getProperties().containsKey("foo"));
        assertTrue(response.getProperties().containsKey("horsePower"));

        // test delete
        // when
        vehicleService.deleteVehicle(response.getId());
        pageableResponse = vehicleService.getAllVehicles(Pageable.ofSize(100));
        // then
        assertEquals(pageableResponse.getTotalElements(), 0);

        // test 404 on get
        // when
        var resourceNotFoundException = assertThrows(
                ResourceNotFoundException.class, () -> vehicleService.getVehicleById("-1")
        );
        // then
        assertEquals(resourceNotFoundException.getMessage(), "vehicle not found");

        // test 400 on update
        // given
        var invalidVehicle = new VehicleModel();

        // when
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> vehicleService.updateVehicle(invalidVehicle)
        );
        // then
        assertEquals(illegalArgumentException.getMessage(), "The given id must not be null!");

        // test 400 on delete
        // when
        illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> vehicleService.deleteVehicle("")
        );
        // then
        assertEquals(illegalArgumentException.getMessage(), "vehicleId must not be null|blank|empty");

//        // test invalid values
//        // when
//        exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> vehicleService.addVehicle(invalidVehicle)
//        );
//
//        String expectedMessage = "For input string";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//        // then
//        assertEquals(response.getName(), vehicle.getName());
//        assertNotNull(response.getId());
    }
}