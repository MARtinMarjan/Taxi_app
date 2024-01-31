package com.example.taxi_app_final.service.impl;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Driver;
import com.example.taxi_app_final.model.DriverStatus;
import com.example.taxi_app_final.model.exceptions.DriverNotFoundException;
import com.example.taxi_app_final.repository.DriverRepository;
import com.example.taxi_app_final.service.DriverService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> findById(Long id) {
        return driverRepository.findById(id);
    }

    @Override
    public Optional<Driver> findByFullName(String fullName) {
        return this.driverRepository.findByFullName(fullName);
    }

    @Override
    public Optional<Driver> save(String FullName, Car car) {
        return Optional.of(this.driverRepository.save(new Driver(FullName, car)));
    }

    @Override
    public Optional<Driver> edit(Long id, String FullName, Car car) {
        Driver driver = this.driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(id));
        driver.setFullName(FullName);
        driver.setCar(car);
        return Optional.of(this.driverRepository.save(driver));
    }

    @Override
    public void deleteById(Long id) {
        this.driverRepository.deleteById(id);
    }

    @Override
    public List<Driver> findDriverByStatus() {
        return driverRepository.findAllByStatus(DriverStatus.AVAILABLE);
    }

    @Override
    public List<Car> findCarsForPassengers(int capacity) {
        return driverRepository.findAvailableCarsByCapacity(capacity, DriverStatus.AVAILABLE);
    }

    @Override
    public Driver setStatus(Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(RuntimeException::new);
        driver.setStatus(DriverStatus.UNAVAILABLE);
        return driverRepository.save(driver);
    }
}
