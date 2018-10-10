package com.demo.microservice.recommendation.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recommendation extends AbstractPersistable<Long> {

    @Column
    private String text;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_recommendations",
            joinColumns = {@JoinColumn(name = "recommendation_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")}
    )
    private List<Customer> customers = new ArrayList<>();


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
