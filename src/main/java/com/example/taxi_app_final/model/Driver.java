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

//    private int rating;

    @OneToOne
    private Car car;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    public Driver(){}
    public Driver(String fullName, Car car) {
        this.fullName = fullName;
        this.car = car;
        this.status = DriverStatus.AVAILABLE;
    }

}
