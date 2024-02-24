package com.example.taxi_app_final.repository;

import com.example.taxi_app_final.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);

    //these below are from the driver repository

    Optional<User> findByRoleAndId(Role role, Long id);

    List<User> findByRole(Role role);

    Optional<User> findByCarId(Long id);

    List<User> findAllByStatusAndRole(DriverStatus status, Role role);

    Optional<User> findByCar(Car car);

    User findById(Long id);

    @Query("SELECT d.car FROM User d WHERE d.car.capacity >= :minCapacity AND d.status = :driverStatus")
    List<Car> findAvailableCarsByCapacity(@Param("minCapacity") int minCapacity, @Param("driverStatus") DriverStatus driverStatus);
}
