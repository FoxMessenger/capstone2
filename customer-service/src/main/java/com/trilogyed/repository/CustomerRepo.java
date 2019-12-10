package com.trilogyed.repository;

import com.trilogyed.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);

}