package com.example.taxi_app_final.repository;

import com.example.taxi_app_final.bootstrap.DataHolder;
import com.example.taxi_app_final.model.BookingDto;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class inMemoryBookingRepository {
    public List<BookingDto> findAll(){
        return DataHolder.bookingDtos;
    }
    public Optional<BookingDto> findByPickupAndDropOff(String pickup, String dropoff){
        return DataHolder.bookingDtos.stream().filter(b->b.getPickupLocation().equals(pickup) && b.getDropOffLocation().equals(dropoff)).findFirst();
    }
    public BookingDto save(String tripType, String pickupLocation, String dropOffLocation, Long duration, Integer kilometers, LocalDateTime pickupDateTime, int passengers, LocalDateTime returnDateTime){
        BookingDto booking = new BookingDto(tripType,pickupLocation, dropOffLocation, duration,kilometers, pickupDateTime,passengers,returnDateTime);
        DataHolder.bookingDtos.add(booking);
        return booking;
    }
}
