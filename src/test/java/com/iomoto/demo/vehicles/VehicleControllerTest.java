package com.iomoto.demo.vehicles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iomoto.demo.controllers.GlobalExceptionHandlerController;
import com.iomoto.demo.controllers.VehicleController;
import com.iomoto.demo.exceptions.ResourceNotFoundException;
import com.iomoto.demo.models.VehicleModel;
import com.iomoto.demo.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class VehicleControllerTest {
    @Mock
    VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    private MockMvc mockMvc;

    private JacksonTester<VehicleModel> jsonVehicleModel;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController)
                .setControllerAdvice(new GlobalExceptionHandlerController())
                .build();
        assertThat(vehicleController).isNotNull();
        assertThat(jsonVehicleModel).isNotNull();
    }

    @Test
    void testCrud() throws Exception {
        // given
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setId("abc");
        vehicleModel.setName("abc");
        vehicleModel.setVin("123");
        vehicleModel.setLicensePlateNumber("xyz");
        var jsonPayload = jsonVehicleModel.write(vehicleModel).getJson();
        // test create
        String resourceUrl = "/vehicles/";
        mockMvc.perform(
                post(resourceUrl + "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload)
        ).andExpect(status().isOk()).andReturn();
        // test create invalid
        // inavlid
        VehicleModel invalidVehicleModel = new VehicleModel();
        jsonPayload = jsonVehicleModel.write(invalidVehicleModel).getJson();
        mockMvc.perform(
                post(resourceUrl + "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload)
        ).andExpect(status().isBadRequest()).andReturn();
        // test update
        // valid
        jsonPayload = jsonVehicleModel.write(vehicleModel).getJson();
        mockMvc.perform(
                put(resourceUrl + "update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload)
        ).andExpect(status().isOk()).andReturn();
        // inavlid
        jsonPayload = jsonVehicleModel.write(invalidVehicleModel).getJson();
        mockMvc.perform(
                put(resourceUrl + "update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload)
        ).andExpect(status().isBadRequest()).andReturn();
        // get
        // inavlid
        when(vehicleService.getVehicleById("   ")).thenThrow(
                new IllegalArgumentException("vehicleId must not be null|blank|empty")
        );
        mockMvc.perform(
                get(resourceUrl + "/   ")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
        // not found
        when(vehicleService.getVehicleById("007")).thenThrow(
                new ResourceNotFoundException("vehicle not found")
        );
        mockMvc.perform(
                get(resourceUrl + "/007")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
        // valid
        when(vehicleService.getVehicleById("abc")).thenReturn(vehicleModel);
        mockMvc.perform(
                get(resourceUrl + "/abc")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        // delete
        // inavlid
        doThrow(new IllegalArgumentException("vehicleId must not be null|blank|empty"))
                .when(vehicleService).deleteVehicle("   ");
        mockMvc.perform(
                delete(resourceUrl + "/   /delete")
        ).andExpect(status().isBadRequest()).andReturn();
        // not found
        doThrow(new ResourceNotFoundException("vehicle not found")).when(vehicleService).deleteVehicle("007");
        mockMvc.perform(
                delete(resourceUrl + "/007/delete")
        ).andExpect(status().isNotFound()).andReturn();
        // valid
        mockMvc.perform(
                delete(resourceUrl + "/abc/delete")
        ).andExpect(status().isOk()).andReturn();
    }
}
