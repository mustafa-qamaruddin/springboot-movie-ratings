package com.qubits.demo.repositories;

import com.qubits.demo.models.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<Customer, String> {
}
