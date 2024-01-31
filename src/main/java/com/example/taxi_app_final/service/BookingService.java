package com.example.taxi_app_final.service;

import com.example.taxi_app_final.model.Booking;
import com.example.taxi_app_final.model.User;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Optional<Booking> saveBooking(String tripType, String pickupLocation, String dropOffLocation, LocalDateTime pickupDateTime, int passengers, LocalDateTime returnDateTime, User user);
    void deleteBooking(Long id);
    List<Booking> findBookingsByUser(User user);

    Booking accept(Long id);
    Booking cancel(Long id);


}
