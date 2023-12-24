package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.Driver;
import com.example.taxi_app_final.service.CarService;
import com.example.taxi_app_final.service.DriverService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;
    private final CarService carService;

    public DriverController(DriverService driverService, CarService carService) {
        this.driverService = driverService;
        this.carService = carService;
    }

    @GetMapping()
    public String displayDrivers(Model model) {
        List<Driver> drivers = driverService.findAll();
        model.addAttribute("drivers", drivers);
        model.addAttribute("bodyContent", "driverList");
        return "master-template";
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public String deleteDriver(@PathVariable Long id) {
        this.driverService.deleteById(id);
        return "redirect:/drivers";
    }

    @GetMapping("/edit-form/{id}")
    public String editDriverPage(@PathVariable Long id, Model model) {
        if (this.driverService.findById(id).isPresent()) {
            Driver driver = this.driverService.findById(id).get();
            List<Car> cars = this.carService.findAll();
            model.addAttribute("driver", driver);
            model.addAttribute("cars",cars);
            model.addAttribute("bodyContent", "add_driver");
            return "master-template";
        }
        return "redirect:/driver?error=DriverNotFound";
    }

    @GetMapping("/add-driver")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addDriverPage(Model model) {
        List<Car> cars = this.carService.findAll();
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("cars", cars);
        model.addAttribute("bodyContent", "add_driver");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveDriver(
            @RequestParam(required = false) Long id,
            @RequestParam String fullName,
            @RequestParam("car.id") Long carId) {

        Car car = carService.findById(carId).orElse(null);

        if (id != null) {
            this.driverService.edit(id, fullName, car);
        } else {
            this.driverService.save(fullName, car);
        }
        return "redirect:/drivers";
    }

}
