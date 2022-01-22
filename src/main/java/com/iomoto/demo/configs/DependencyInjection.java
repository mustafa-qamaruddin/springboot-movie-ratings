package com.iomoto.demo.configs;

import com.iomoto.demo.services.VehicleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjection {
    @Bean
    public VehicleService vehicleService() {
        return new VehicleService();
    }
}
