package com.iomoto.demo.controllers;

import com.iomoto.demo.models.VehicleModel;
import com.iomoto.demo.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(
            path = "/add",
            produces = {"application/json", "application/xml"}
    )
    public ResponseEntity<VehicleModel> addvehicle(@RequestBody @Valid VehicleModel _vehicle) {
        VehicleModel vehicle = vehicleService.addVehicle(_vehicle);
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping(
            path = "/update",
            produces = {"application/json", "application/xml"}
    )
    public ResponseEntity<VehicleModel> updatevehicle(
            @RequestBody @Valid VehicleModel _vehicle) {
        VehicleModel vehicle = vehicleService.updateVehicle(_vehicle);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping(
            path = "/{vehicleId}/delete",
            produces = {"application/json", "application/xml"}
    )
    public void deletevehicle(@PathVariable(name = "vehicleId") String vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
    }

    @GetMapping(
            path = "/{vehicleId}",
            produces = {"application/json", "application/xml"}
    )
    public ResponseEntity<VehicleModel> getvehicle(
            @PathVariable(name = "vehicleId") String vehicleId) {
        VehicleModel vehicle = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping(
            path = "/all",
            produces = {"application/json", "application/xml"}
    )
    public ResponseEntity<Page<VehicleModel>> getvehicles(@PageableDefault(page = 0,
            size = 30) @SortDefault.SortDefaults({@SortDefault(sort = "modified",
            direction = Sort.Direction.DESC)}) Pageable pageable) {
        Page<VehicleModel> vehicles = vehicleService.getAllVehicles(pageable);
        return ResponseEntity.ok(vehicles);
    }
}