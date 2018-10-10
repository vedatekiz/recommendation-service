package com.demo.microservice.recommendation.mapper;

import com.demo.microservice.recommendation.boundary.RecommendationDTO;
import com.demo.microservice.recommendation.entity.Recommendation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationMapper {

    public static RecommendationDTO makeRecommendationDTO(Recommendation recommendation) {
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        recommendationDTO.setText(recommendation.getText());
        return recommendationDTO;
    }

    public static List<RecommendationDTO> makeRecommendationDTOList(Collection<Recommendation> recommendations) {
        return recommendations.stream()
                .map(RecommendationMapper::makeRecommendationDTO)
                .collect(Collectors.toList());
    }
}


