package com.demo.microservice.recommendation.repository;

import com.demo.microservice.recommendation.entity.Recommendation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query(value = "SELECT r FROM Recommendation  r left join fetch r.customers c WHERE c.recommendationActive = true and c.username=:username order by r.id")
    List<Recommendation> findByUsername(@Param("username") String username, Pageable pageable);

    Optional<Recommendation> findByText(String text);

}
