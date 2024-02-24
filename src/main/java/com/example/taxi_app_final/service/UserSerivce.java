package com.example.taxi_app_final.service;
import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Role;
import com.example.taxi_app_final.model.User;
import java.util.List;
import java.util.Optional;

public interface UserSerivce {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);

//    UserDetails loadUserByUsername(String s);

    User loadUserByUsername(String s);

    //methods we need for the driver

    List<User> findAllDrivers();
    Optional<User> findById(Long id);

    List<User> findDriverByStatus();

    List<Car> findCarsForPassengers(int capacity);

    User setStatus(Long id);

    Optional<User> findByCar(Car car);

    User update(Long id, String name, String surname, String username);

}
