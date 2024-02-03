package com.example.taxi_app_final.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String licensePlate;
    private String color;
    private int year;
    private int capacity;
    private int bag;
    private Long pricePerKm;

//    @OneToOne
//    private Driver driver;

    @OneToOne
    private Booking booking;

    public Car(){}

    public Car(String model, String licencePlate, String color, int year, int capacity, int bag, Long pricePerKm) {
        this.model = model;
        this.licensePlate = licencePlate;
        this.color = color;
        this.year = year;
        this.capacity = capacity;
        this.bag = bag;
        this.pricePerKm = pricePerKm;
    }
}
