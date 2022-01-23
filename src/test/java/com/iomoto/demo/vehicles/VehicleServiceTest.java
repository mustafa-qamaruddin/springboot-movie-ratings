package com.iomoto.demo.vehicles;

import com.iomoto.demo.models.VehicleModel;
import com.iomoto.demo.repositories.VehicleRepository;
import com.iomoto.demo.services.VehicleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
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

        // test update

        // test delete

    }
}