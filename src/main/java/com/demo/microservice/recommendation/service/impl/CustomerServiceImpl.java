package com.demo.microservice.recommendation.service.impl;

import com.demo.microservice.recommendation.entity.Customer;
import com.demo.microservice.recommendation.entity.Recommendation;
import com.demo.microservice.recommendation.repository.CustomerRepository;
import com.demo.microservice.recommendation.repository.RecommendationRepository;
import com.demo.microservice.recommendation.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final String SEPERATOR = ",";
    private final Integer RECOMMENDATION_START_INDEX = 1;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RecommendationRepository recommendationRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setRecommendations(customer.getRecommendations().stream().map(recommendation -> recommendationRepository.save(recommendation)).collect(Collectors.toList()));
        return customerRepository.save(customer);
    }

    @Override
    public void processCsvFile(File csvFile) throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            List<Recommendation> recommendationList = new ArrayList<>();
            String[] split = line.split(SEPERATOR);
            Arrays.parallelSetAll(split, (i) -> split[i].replace("\"", ""));
            String username = split[0];
            Optional<Customer> customerByUsername = customerRepository.findByUsername(username);
            Customer customer;
            if (customerByUsername.isPresent()) {
                customer = customerByUsername.get();
            } else {
                customer = new Customer();
                customer.setUsername(username);
            }

            recommendationList = IntStream.range(0, split.length)
                    .filter(i -> i > RECOMMENDATION_START_INDEX)
                    .mapToObj(i -> split[i])
                    .map(rec -> {
                        Recommendation recommendation;
                        Optional<Recommendation> byText = recommendationRepository.findByText(rec);
                        if (byText.isPresent()) {
                            recommendation = byText.get();
                        } else {
                            recommendation = new Recommendation();
                            recommendation.setText(rec);
                        }
                        return recommendation;
                    }).collect(Collectors.toList());

            customer.setRecommendationActive(split[1].equalsIgnoreCase("true") ? true : false);
            customer.getRecommendations().clear();
            customer.getRecommendations().addAll(recommendationList);
            this.saveCustomer(customer);
        }
    }
}
