package com.demo.microservice.recommendation.controller;

import com.demo.microservice.recommendation.boundary.ApiResponse;
import com.demo.microservice.recommendation.entity.Customer;
import com.demo.microservice.recommendation.exception.CustomerNotFoundException;
import com.demo.microservice.recommendation.mapper.RecommendationMapper;
import com.demo.microservice.recommendation.repository.CustomerRepository;
import com.demo.microservice.recommendation.repository.RecommendationRepository;
import com.demo.microservice.recommendation.service.CustomerService;
import com.demo.microservice.recommendation.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("customers")
@CrossOrigin("*")
public class CustomerController {

    private final Integer DEFAULT_COUNT = 3;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    RecommendationRepository recommendationRepository;

    @Autowired
    FileUploadService fileUploadService;

    @GetMapping(path = "/{username}/games/recommendations")
    public ResponseEntity<ApiResponse> getRecommendationsOfCustomer(@PathVariable String username, @RequestParam(value = "count", required = false) Integer count) throws CustomerNotFoundException {
        Assert.notNull(username, "Customer username should not be empty");
        Optional<Customer> byUsernameAndRecommendationActive = customerRepository.findByUsernameAndRecommendationActive(username, true);
        if (!byUsernameAndRecommendationActive.isPresent()) {
            throw new CustomerNotFoundException("Customer does not exist or recommendations not active");
        }
        if (Objects.isNull(count)) {
            count = DEFAULT_COUNT;
        }

        return new ResponseEntity<>(new ApiResponse("Recommendation List Returned", RecommendationMapper.makeRecommendationDTOList(recommendationRepository.findByUsername(username, new PageRequest(0, count)))), HttpStatus.OK);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ApiResponse> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File toFile = fileUploadService.convertMultiPartToFile(file);
        customerService.processCsvFile(toFile);

        ApiResponse apiResponse = new ApiResponse("File Uploaded Successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
