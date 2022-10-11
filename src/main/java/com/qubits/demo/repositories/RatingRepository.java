package com.qubits.demo.repositories;

import com.qubits.demo.models.Rating;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RatingRepository extends PagingAndSortingRepository<Rating, String> {
}
