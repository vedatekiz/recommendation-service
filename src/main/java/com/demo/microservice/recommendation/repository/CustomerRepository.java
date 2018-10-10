package com.demo.microservice.recommendation.repository;

import com.demo.microservice.recommendation.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsernameAndRecommendationActive(String username, Boolean recommendationActive);
    Optional<Customer> findByUsername(String username);

}
