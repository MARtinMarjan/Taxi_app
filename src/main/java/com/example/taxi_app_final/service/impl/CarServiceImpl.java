package com.example.taxi_app_final.service.impl;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.User;
import com.example.taxi_app_final.model.exceptions.CarNotFoundException;
import com.example.taxi_app_final.repository.CarRepository;
import com.example.taxi_app_final.repository.UserRepository;
import com.example.taxi_app_final.service.CarService;
import com.example.taxi_app_final.service.UserSerivce;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserSerivce userSerivce;
    private final UserRepository userRepository;

    public CarServiceImpl(CarRepository carRepository, UserSerivce userSerivce, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userSerivce = userSerivce;
        this.userRepository = userRepository;
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
    public Optional<Car> save(String model, String licencePlate, String color, int year, int capacity, int bag, Long pricePerKm, Long userId) {
        Optional<User> userOptional = userSerivce.findById(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            Car car = new Car(model,licencePlate,color,year,capacity,bag,pricePerKm);
            user.setCar(car);
            this.carRepository.save(car);
            userRepository.save(user);
            return Optional.of(car);
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteCar(Long id){
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        User user = userRepository.findByCarId(id).orElseThrow(RuntimeException::new);
        user.setCar(null);
//        List<Driver> drivers = driverRepository.findByCarId(car.getId());
//        driverRepository.deleteAll(drivers);
        carRepository.delete(car);
    }

    @Override
    public List<Car> getFilteredCars(String tripType, String pickupLocation, String dropOffLocation, LocalDateTime pickupDateTime, int passengers, LocalDateTime returnDateTime) {
        return null;
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
