package com.example.taxi_app_final.repository;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Driver;
import com.example.taxi_app_final.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, String> {

    Optional<Driver> findByFullName(String fullName);
    Optional<Driver> findById(Long id);
    void deleteById(Long id);
    List<Driver> findByCarId(Long id);

    List<Driver> findAllByStatus(DriverStatus status);

    @Query("SELECT d.car FROM Driver d WHERE d.car.capacity >= :minCapacity AND d.status = :driverStatus")
    List<Car> findAvailableCarsByCapacity(@Param("minCapacity") int minCapacity, @Param("driverStatus") DriverStatus driverStatus);
}
