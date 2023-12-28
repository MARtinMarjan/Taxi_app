package com.example.taxi_app_final.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private String pickupLocation;
    private String dropOffLocation;
    private Long duration;
    private Integer kilometers;
    private LocalDateTime pickupDateTime;
    private int passengers;
    private LocalDateTime returnDateTime;
    private String tripType;

    public BookingDto(String pickupLocation, String dropOffLocation, Long duration, Integer kilometers) {
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.duration = duration;
        this.kilometers = kilometers;
    }

    public BookingDto(String tripType, String pickupLocation, String dropOffLocation, Long duration, Integer kilometers, LocalDateTime pickupDateTime, int passengers, LocalDateTime returnDateTime) {
        this.tripType = tripType;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.duration = duration;
        this.kilometers = kilometers;
        this.pickupDateTime = pickupDateTime;
        this.passengers = passengers;
        this.returnDateTime = returnDateTime;
    }
}
