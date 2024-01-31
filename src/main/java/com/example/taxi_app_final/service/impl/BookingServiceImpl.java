package com.example.taxi_app_final.service.impl;

import com.example.taxi_app_final.model.Booking;
import com.example.taxi_app_final.model.BookingStatus;
import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.User;
import com.example.taxi_app_final.model.exceptions.InvalidBookingException;
import com.example.taxi_app_final.repository.BookingRepository;
import com.example.taxi_app_final.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Booking> saveBooking(String tripType, String pickupLocation, String dropOffLocation, LocalDateTime pickupDateTime, int passengers, LocalDateTime returnDateTime, User user) {
        return Optional.of(this.bookingRepository.save(new Booking(tripType,pickupLocation,dropOffLocation,pickupDateTime,passengers,returnDateTime,user)));
    }

    @Transactional
    @Override
    public void deleteBooking(Long id) {
        this.bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> findBookingsByUser(User user) {
        return bookingRepository.findAllByUser(user);
    }

    @Override
    public Booking accept(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new InvalidBookingException(id));
        booking.setStatus(BookingStatus.ACCEPTED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking cancel(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new InvalidBookingException(id));
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);    }
}


