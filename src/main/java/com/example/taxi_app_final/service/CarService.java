package com.example.taxi_app_final.service;

import com.example.taxi_app_final.model.Car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAll();

    Optional<Car> findById(Long id);

    Optional<Car> findByName(String model);

    Optional<Car> save(String model, String licencePlate, String color, int year, int capacity, int bag, Long pricePerKm,Long userId);

    //Optional<Car> save(ProductDto productDto);

    Optional<Car> edit(Long id, String model, String licencePlate, String color, int year, int capacity, int bag, Long pricePerKm);

    //Optional<Product> edit(Long id, ProductDto productDto);

    public void deleteCar(Long id);

    public List<Car> getFilteredCars(String tripType, String pickupLocation, String dropOffLocation, LocalDateTime pickupDateTime, int passengers, LocalDateTime returnDateTime);

    void deleteById(Long id);

}
