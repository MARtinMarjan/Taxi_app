package com.example.taxi_app_final.service;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    List<Driver> findAll();

    Optional<Driver> findById(Long id);

    Optional<Driver> findByFullName(String fullName);

    Optional<Driver> save(String fullName, Car car);

    //Optional<Car> save(ProductDto productDto);

    Optional<Driver> edit(Long id, String fullName, Car car);

    //Optional<Product> edit(Long id, ProductDto productDto);

    void deleteById(Long id);

    List<Driver> findDriverByStatus();

    List<Car> findCarsForPassengers(int capacity);

    Driver setStatus(Long id);

}
