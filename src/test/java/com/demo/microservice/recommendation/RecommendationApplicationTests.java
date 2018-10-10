package com.demo.microservice.recommendation;

import com.demo.microservice.recommendation.entity.Customer;
import com.demo.microservice.recommendation.entity.Recommendation;
import com.demo.microservice.recommendation.repository.RecommendationRepository;
import com.demo.microservice.recommendation.service.CustomerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationApplication.class)
public class RecommendationApplicationTests {

	@Autowired
	CustomerService customerService;

	@Autowired
	RecommendationRepository recommendationRepository;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testWhenNewCustomerCreated(){
		List<Recommendation> recommendations = new ArrayList<>();
		String usernameToBeSaved = "1212";

		Recommendation recommendation = new Recommendation();
		recommendation.setText("sudoku");
		recommendations.add(recommendation);

		Customer customer = new Customer();
		customer.setUsername(usernameToBeSaved);
		customer.getRecommendations().addAll(recommendations);
		Customer savedCustomer = customerService.saveCustomer(customer);

		Assert.assertEquals(usernameToBeSaved, savedCustomer.getUsername() );
	}

	@Test
	public void testRecommendationWhenNewCustomerCreated(){
		List<Recommendation> recommendations = new ArrayList<>();
		String usernameToBeSaved = "1213";

		Recommendation recommendation = new Recommendation();
		recommendation.setText("bingo");
		recommendations.add(recommendation);

		Customer customer = new Customer();
		customer.setUsername(usernameToBeSaved);
		customer.getRecommendations().addAll(recommendations);
		customerService.saveCustomer(customer);

		List<Recommendation> byUsername = recommendationRepository.findByUsername(usernameToBeSaved, new PageRequest(0, 1000));

		Assert.assertEquals(1, byUsername.size());
	}

	@Test
	public void testWhenNewPassiveCustomerCreated(){
		List<Recommendation> recommendations = new ArrayList<>();
		String usernameToBeSaved = "1214";

		Recommendation recommendation = new Recommendation();
		recommendation.setText("bingo");
		recommendations.add(recommendation);

		Customer customer = new Customer();
		customer.setRecommendationActive(false);
		customer.setUsername(usernameToBeSaved);
		customer.getRecommendations().addAll(recommendations);
		customerService.saveCustomer(customer);

		List<Recommendation> byUsername = recommendationRepository.findByUsername(usernameToBeSaved, new PageRequest(0, 1000));

		Assert.assertEquals(0, byUsername.size());
	}

	@Test
	public void testWhenFileUpload() throws IOException {
		String usernameInCsvFile = "1215";
		int recommendCountInCsvFile = 3;
		File csvFile = ResourceUtils.getFile("src/test/resources/recommend.csv");
		customerService.processCsvFile(csvFile);

		List<Recommendation> byUsername = recommendationRepository.findByUsername(usernameInCsvFile, new PageRequest(0, 1000));

		Assert.assertEquals(recommendCountInCsvFile, byUsername.size());
	}

}
