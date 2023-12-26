package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.model.Booking;
import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.User;
import com.example.taxi_app_final.service.BookingService;
import com.example.taxi_app_final.service.CarService;
import com.example.taxi_app_final.service.UserSerivce;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final UserSerivce userSerivce;
    private final CarService carService;

    public BookingController(BookingService bookingService, UserSerivce userSerivce, CarService carService) {
        this.bookingService = bookingService;
        this.userSerivce = userSerivce;
        this.carService = carService;
    }

    @GetMapping
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        model.addAttribute("bodyContent", "bookinghtml");
        return "master-template";
    }

    @GetMapping("/form")
    public String showBookingForm(Model model) {
        //List<Car> availableCars = carService.getAvailableCars();
        //model.addAttribute("cars", availableCars);
        return "main";
    }

    /*@PostMapping("/create")
    public String createBooking(@ModelAttribute BookingForm bookingForm, Model model) {
        Optional<Booking> newBooking = bookingService.saveBooking(
                bookingForm.getTripType(),
                bookingForm.getPickupLocation(),
                bookingForm.getDropOffLocation(),
                bookingForm.getPickupDateTime(),
                bookingForm.getPassengers(),
                bookingForm.getReturnDateTime(),
                bookingForm.getUser()
        );

        // Handle the case where the booking creation fails (if needed)

        return "redirect:/bookings/list";
    }*/

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings";
    }


    /*@PostMapping
    public String bookTaxi(@RequestParam String tripType,
                           @RequestParam String pickupLocation,
                           @RequestParam String dropOffLocation,
                           @RequestParam LocalDateTime pickupDateTime,
                           @RequestParam int passengers,
                           @RequestParam(required = false) LocalDateTime returnDateTime,
                           HttpSession session) {

        // Assuming you have a User model and a UserService to get the current user
        User currentUser = userSerivce.getCurrentUser(); // You need to implement this method

        Booking booking = new Booking();
        booking.setTripType(tripType);
        booking.setPickupLocation(pickupLocation);
        booking.setDropOffLocation(dropOffLocation);
        booking.setPickupDateTime(pickupDateTime);
        booking.setPassengers(passengers);
        booking.setReturnDateTime(returnDateTime);
        booking.setUser(currentUser); // Set the current user

        bookingService.saveBooking(tripType,pickupLocation,dropOffLocation,pickupDateTime,passengers,returnDateTime,currentUser);

        // Redirect to a success page or any other appropriate page
        return "redirect:/success";
    }*/
}
