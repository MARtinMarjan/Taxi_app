package com.example.taxi_app_final.repository;

import com.example.taxi_app_final.model.Car;

import com.example.taxi_app_final.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {


    Optional<Car> findById(Long id);
    Optional<Car> findByModel(String model);
    void deleteById(Long id);

}
