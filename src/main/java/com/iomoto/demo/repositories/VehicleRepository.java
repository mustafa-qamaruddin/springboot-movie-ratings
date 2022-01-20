package com.iomoto.demo.repositories;

import com.iomoto.demo.models.VehicleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<VehicleModel, String> {
    public Page<VehicleModel> findAll(Pageable pageable);
}