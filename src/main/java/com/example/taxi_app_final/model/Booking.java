package com.example.taxi_app_final.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Booking_car")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tripType;
    private String pickupLocation;
    private String dropOffLocation;
    private LocalDateTime pickupDateTime;
    private int passengers;
    private LocalDateTime returnDateTime;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private Driver driver;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking(String tripType, String pickupLocation, String dropOffLocation, LocalDateTime pickupDateTime, int passengers, LocalDateTime returnDateTime, User user) {
        this.tripType = tripType;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.pickupDateTime = pickupDateTime;
        this.passengers = passengers;
        this.returnDateTime = returnDateTime;
        this.user = user;
        this.status = BookingStatus.REQUESTED;
    }
    public Booking(){}

}
