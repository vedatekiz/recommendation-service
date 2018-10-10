package com.demo.microservice.recommendation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RecommendationApplication.class)
@AutoConfigureMockMvc
public class RecommendationIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenListRecommendations_thenStatus200() throws Exception {
        String username = "1111";

        mvc.perform(get("/customers/" + username + "/games/recommendations").param("count", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseObject[0].text", is("bingo")));
    }

    @Test
    public void whenListRecommendationsOfNotExistCustomer_thenStatus404() throws Exception {
        String username = "34343434";

        mvc.perform(get("/customers/" + username + "/games/recommendations").param("count", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
