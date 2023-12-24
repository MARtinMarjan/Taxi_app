package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final CarService carService;

    public HomeController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public String getHomePage(Model model) {
        model.addAttribute("bodyContent", "home");
        model.addAttribute("cars", carService.findAll());
        return "master-template";
    }
}