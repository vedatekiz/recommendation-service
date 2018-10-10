package com.demo.microservice.recommendation.service;

import com.demo.microservice.recommendation.entity.Customer;

import java.io.File;
import java.io.IOException;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    void processCsvFile(File csvFile) throws IOException;
}
