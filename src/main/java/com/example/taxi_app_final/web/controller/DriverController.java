package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Role;
import com.example.taxi_app_final.model.User;
import com.example.taxi_app_final.service.CarService;
import com.example.taxi_app_final.service.UserSerivce;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final UserSerivce userSerivce;
    private final CarService carService;

    public DriverController(UserSerivce userSerivce, CarService carService) {
        this.userSerivce = userSerivce;
        this.carService = carService;
    }

    @GetMapping()
    public String displayDrivers(Model model) {
//        List<Driver> drivers = driverService.findAll();
//        List<User> drivers = userSerivce.findAllDrivers();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userSerivce.loadUserByUsername(username);
        List<User> drivers;
        if (user.getRole() == Role.ROLE_ADMIN) {
            // If the user is a driver and has a car associated with them, show only their car
            drivers = List.of(user);
        } else {
            // Otherwise, show all cars
            drivers = userSerivce.findAllDrivers();
        }
        model.addAttribute("drivers", drivers);
        model.addAttribute("bodyContent", "driverList");
        return "master-template";
    }


    @GetMapping("/edit-form/{id}")
    public String editDriverPage(@PathVariable Long id, Model model) {
        User driver = userSerivce.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("driver", driver);
        model.addAttribute("bodyContent", "add_driver");
        return "master-template";
    }


    @PostMapping("/add")
    public String saveDriver(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String username) {

        userSerivce.update(id,name,surname,username);
        return "redirect:/drivers";
    }

}
