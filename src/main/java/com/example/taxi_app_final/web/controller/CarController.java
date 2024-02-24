package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Role;
import com.example.taxi_app_final.model.User;
import com.example.taxi_app_final.service.CarService;
import com.example.taxi_app_final.service.UserSerivce;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final UserSerivce userSerivce;

    public CarController(CarService carService, UserSerivce userSerivce) {
        this.carService = carService;
        this.userSerivce = userSerivce;
    }

    @GetMapping()
    public String displayCars(Model model) {
//        List<Car> cars = carService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userSerivce.loadUserByUsername(username);
        List<Car> cars;
        Boolean hasCar = user.getCar() != null;

        if (user.getRole() == Role.ROLE_ADMIN && user.getCar() != null) {
            // If the user is a driver and has a car associated with them, show only their car
            cars = List.of(user.getCar());
        } else if(user.getRole() == Role.ROLE_USER) {
            // Otherwise, show all cars
            cars = carService.findAll();
        }else{
            cars=null;
        }
        model.addAttribute("hasCar",hasCar);
        model.addAttribute("cars", cars);
        model.addAttribute("bodyContent", "carList");
        return "master-template";
    }


    @PostMapping("/delete/{id}")
    @Transactional
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/cars";
    }

    @GetMapping("/edit-form/{id}")
    public String editCarPage(@PathVariable Long id, Model model) {
        if (this.carService.findById(id).isPresent()) {
            Car car = this.carService.findById(id).get();
            model.addAttribute("car", car);
            model.addAttribute("bodyContent","addcar");
            return "master-template";
        }
    return "redirect:/car?error=CarNotFound";
}

    @GetMapping("/add-car")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCarPage(Model model) {
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("bodyContent","addcar");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveCar(
            @RequestParam(required = false) Long id,
            @RequestParam String model,
            @RequestParam String licensePlate,
            @RequestParam String color,
            @RequestParam int year,
            @RequestParam int capacity,
            @RequestParam int bag,
            @RequestParam Long pricePerKm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = getUserIdByUsername(userDetails.getUsername());
        if (id != null) {
            this.carService.edit(id, model, licensePlate, color, year, capacity, bag, pricePerKm);
        } else {
            this.carService.save(model, licensePlate, color, year, capacity, bag, pricePerKm, userId);
        }
        return "redirect:/cars";
    }

    private Long getUserIdByUsername(String username) {
        User user = userSerivce.loadUserByUsername(username);
        return user.getId(); // Assuming getId() returns the user's ID
    }


    @PostMapping("/cars/filter")
    public String filterCars(@RequestParam("tripType") String tripType,
                             @RequestParam("pickupLocation") String pickupLocation,
                             @RequestParam("dropOffLocation") String dropOffLocation,
                             @RequestParam("pickupDateTime") LocalDateTime pickupDateTime,
                             @RequestParam("passengers") int passengers,
                             @RequestParam(value = "returnDateTime", required = false) LocalDateTime returnDateTime,
                             Model model) {
        List<Car> filteredCars = carService.getFilteredCars(tripType, pickupLocation, dropOffLocation, pickupDateTime, passengers, returnDateTime);
        model.addAttribute("cars", filteredCars);
        model.addAttribute("bodyContent", "carList");
        return "master-template";
    }


}
