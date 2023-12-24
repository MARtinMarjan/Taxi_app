package com.example.taxi_app_final.repository;

import com.example.taxi_app_final.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    Optional<Booking> findById(Long id);
    //Optional<Booking> findBy(String model);
    void deleteById(Long id);

}
