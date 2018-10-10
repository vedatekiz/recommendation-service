package com.demo.microservice.recommendation;

import com.demo.microservice.recommendation.entity.Customer;
import com.demo.microservice.recommendation.entity.Recommendation;
import com.demo.microservice.recommendation.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RecommendationApplication implements CommandLineRunner {

    @Autowired
    CustomerService customerService;

    public static void main(String[] args) {
        SpringApplication.run(RecommendationApplication.class, args);
    }

    //Adds initial customer and recommendations
    @Override
    public void run(String... args) throws Exception {
        List<Recommendation> recommendations = new ArrayList<>();

        Recommendation recommendation = new Recommendation();
        recommendation.setText("bingo");
        recommendations.add(recommendation);

        recommendation = new Recommendation();
        recommendation.setText("cashwheel");
        recommendations.add(recommendation);

        recommendation = new Recommendation();
        recommendation.setText("sudoku");
        recommendations.add(recommendation);

        Customer customer = new Customer();
        customer.setUsername("1111");
        customer.getRecommendations().addAll(recommendations);
        customerService.saveCustomer(customer);

        customer = new Customer();
        customer.setUsername("2222");
        customerService.saveCustomer(customer);

    }
}
