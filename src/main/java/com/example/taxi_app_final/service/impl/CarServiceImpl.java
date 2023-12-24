package com.example.taxi_app_final.service.impl;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Driver;
import com.example.taxi_app_final.model.exceptions.CarNotFoundException;
import com.example.taxi_app_final.repository.CarRepository;
import com.example.taxi_app_final.repository.DriverRepository;
import com.example.taxi_app_final.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;

    public CarServiceImpl(CarRepository carRepository, DriverRepository driverRepository) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Car> findAll() {
        return this.carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return this.carRepository.findById(id);
    }

    @Override
    public Optional<Car> findByName(String model) {
        return this.carRepository.findByModel(model);
    }

    @Override
    public Optional<Car> save(String model, String licencePlate, String color, int year, int capacity, int bag, Long pricePerKm) {
        return Optional.of(this.carRepository.save(new Car(model,licencePlate,color,year,capacity,bag,pricePerKm)));
    }

    @Transactional
    public void deleteCar(Long id){
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        List<Driver> drivers = driverRepository.findByCarId(car.getId());
        driverRepository.deleteAll(drivers);
        carRepository.delete(car);
    }

    @Override
    public Optional<Car> edit(Long id, String model, String licencePlate, String color, int year, int capacity, int bag, Long pricePerKm) {
        Car car = this.carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        car.setModel(model);
        car.setLicensePlate(licencePlate);
        car.setColor(color);
        car.setYear(year);
        car.setCapacity(capacity);
        car.setBag(bag);
        car.setPricePerKm(pricePerKm);

        return Optional.of(this.carRepository.save(car));
    }

    @Override
    public void deleteById(Long id) {
        this.carRepository.deleteById(id);
    }
}
