package com.iomoto.demo.services;

import com.iomoto.demo.exceptions.ResourceNotFoundException;
import com.iomoto.demo.models.VehicleModel;
import com.iomoto.demo.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleModel addVehicle(VehicleModel _vehicle) {
        return vehicleRepository.save(_vehicle);
    }

    public VehicleModel updateVehicle(VehicleModel _vehicle) {
        Optional<VehicleModel> optionalVehicleModel = vehicleRepository.findById(_vehicle.getId());
        if (optionalVehicleModel.isEmpty()) {
            throw new ResourceNotFoundException("vehicle not found");
        }
        var oldProps = optionalVehicleModel.get().getProperties();
        var newProps = _vehicle.getProperties();
        oldProps.forEach((k, v) -> {
            if (!newProps.containsKey(k)) {
                newProps.put(k, v);
            }
        });
        return vehicleRepository.save(_vehicle);
    }

    public void deleteVehicle(String vehicleId) {
        if (vehicleId == null || vehicleId.isEmpty() || vehicleId.isBlank()) {
            throw new IllegalArgumentException("vehicleId must not be null|blank|empty");
        }
        Optional<VehicleModel> optionalVehicleModel = vehicleRepository.findById(vehicleId);
        if (!optionalVehicleModel.isPresent()) {
            throw new ResourceNotFoundException("vehicle not found");
        }
        vehicleRepository.deleteById(vehicleId);
    }

    public VehicleModel getVehicleById(String vehicleId) {
        if (vehicleId == null || vehicleId.isEmpty() || vehicleId.isBlank()) {
            throw new IllegalArgumentException("vehicleId must not be null|blank|empty");
        }

        Optional<VehicleModel> optionalVehicleModel = vehicleRepository.findById(vehicleId);
        if (optionalVehicleModel.isEmpty()) {
            throw new ResourceNotFoundException("vehicle not found");
        }
        return optionalVehicleModel.get();
    }

    public Page<VehicleModel> getAllVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

}
