package com.example.taxi_app_final.repository;

import com.example.taxi_app_final.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, String> {

    Optional<Driver> findByFullName(String fullName);
    Optional<Driver> findById(Long id);
    void deleteById(Long id);
    List<Driver> findByCarId(Long id);
}
