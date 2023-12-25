package com.example.taxi_app_final.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private int rating;

    @OneToOne
    private Car car;

    public Driver(){}
    public Driver(String fullName, Car car) {
        this.fullName = fullName;
        this.car = car;
    }

}
