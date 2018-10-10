package com.demo.microservice.recommendation.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer",
        uniqueConstraints = @UniqueConstraint(name = "username_uc", columnNames = "username"))
public class Customer extends AbstractPersistable<Long> {

    @Size(min = 4, max = 10, message = "Minimum username length: 4 characters, maximum username length 10 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Column
    Boolean recommendationActive = true;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "customer_recommendations",
            joinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "recommendation_id", referencedColumnName = "id")}
    )
    private List<Recommendation> recommendations = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public Boolean getRecommendationActive() {
        return recommendationActive;
    }

    public void setRecommendationActive(Boolean recommendationActive) {
        this.recommendationActive = recommendationActive;
    }
}



