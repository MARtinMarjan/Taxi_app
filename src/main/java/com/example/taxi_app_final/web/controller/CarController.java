package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.exceptions.CarNotFoundException;
import com.example.taxi_app_final.repository.CarRepository;
import com.example.taxi_app_final.service.CarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public String displayCars(Model model) {
        List<Car> cars = carService.findAll();
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
            model.addAttribute("bodyContent","add_car");
            return "master-template";
        }
    return "redirect:/car?error=CarNotFound";
}

    @GetMapping("/add-car")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCarPage(Model model) {
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("bodyContent","add_car");
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
        if (id != null) {
            this.carService.edit(id, model, licensePlate, color, year, capacity, bag, pricePerKm);
        } else {
            this.carService.save(model, licensePlate, color, year, capacity, bag, pricePerKm);
        }
        return "redirect:/cars";
    }


}
