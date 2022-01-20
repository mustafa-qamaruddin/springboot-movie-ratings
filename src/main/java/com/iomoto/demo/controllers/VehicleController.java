package com.iomoto.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/vehicles")
public class vehicleController {

    @Autowired
    private vehicleService VehicleService;

    @PostMapping(path = "/add")
    public ResponseEntity<vehicleDto> addvehicle(@RequestBody @Valid vehicleNewDto vehicleNewDto) {
        vehicleDto vehicle = vehicleService.addvehicle(vehicleNewDto);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<vehicleDto> updatevehicle(
            @RequestBody @Valid vehicleExistingDto vehicleExistingDto) {
        vehicleDto vehicle = vehicleService.updatevehicle(vehicleExistingDto);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping(path = "/{vehicleId}/delete")
    public void deletevehicle(@PathVariable(name = "vehicleId") String vehicleId) {
        vehicleService.deletevehicle(vehicleId);
    }

    @GetMapping(path = "/{vehicleId}")
    public ResponseEntity<vehicleDto> getvehicle(
            @PathVariable(name = "vehicleId") String vehicleId) {
        vehicleDto vehicle = vehicleService.getvehicleById(vehicleId);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Page<vehicleDto>> getvehicles(@PageableDefault(page = 0,
            size = 30) @SortDefault.SortDefaults({@SortDefault(sort = "modified",
            direction = Sort.Direction.DESC)}) Pageable pageable) {
        Page<vehicleDto> vehicles = vehicleService.getAllvehicles(pageable);
        return ResponseEntity.ok(vehicles);
    }
}