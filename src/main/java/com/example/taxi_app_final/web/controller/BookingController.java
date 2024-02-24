package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.model.*;
import com.example.taxi_app_final.service.BookingService;
import com.example.taxi_app_final.service.CarService;
import com.example.taxi_app_final.service.UserSerivce;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.taxi_app_final.repository.inMemoryBookingRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final UserSerivce userSerivce;
    private final CarService carService;
    private final inMemoryBookingRepository inMemoryBookingRepository;

    public BookingController(BookingService bookingService, UserSerivce userSerivce, CarService carService,  com.example.taxi_app_final.repository.inMemoryBookingRepository inMemoryBookingRepository) {
        this.bookingService = bookingService;
        this.userSerivce = userSerivce;
        this.carService = carService;
        this.inMemoryBookingRepository = inMemoryBookingRepository;
    }

    @GetMapping
    public String listBookings(Model model, HttpServletRequest request) {
        List<BookingDto> bookingDtos = inMemoryBookingRepository.findAll();
        model.addAttribute("bookingsDtos", bookingDtos);
        model.addAttribute("bodyContent", "bookinghtml");
        return "master-template";
    }

    @GetMapping("/list")
    public String listBookingInfo(Model model, HttpSession session){
//        List<Driver> drivers = driverService.findDriverByStatus();
        BookingDto bookingDto = (BookingDto) session.getAttribute("savedBooking");

        LocalDateTime userPickupTime = (LocalDateTime) session.getAttribute("userPickupTime");
        LocalDateTime userReturnTime = (LocalDateTime) session.getAttribute("userReturnTime");
        String userTripType = (String) session.getAttribute("userTripType");
        int passengers = (int) session.getAttribute("passengers");

        List<Car> cars = userSerivce.findCarsForPassengers(bookingDto.getPassengers());

        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("userPickupTime",userPickupTime);
        model.addAttribute("userReturnTime", userReturnTime);
        model.addAttribute("userTripType", userTripType);
        model.addAttribute("passengers",passengers);
//        model.addAttribute("drivers",drivers);
        model.addAttribute("cars",cars);
        model.addAttribute("bodyContent","bookingdetails");
        return "master-template";
    }

    @GetMapping("/form")
    public String showBookingForm(Model model) {
        //List<Car> availableCars = carService.getAvailableCars();
        //model.addAttribute("cars", availableCars);
        return "main";
    }

    @PostMapping("/add")
    public String saveBooking(HttpServletRequest request, HttpSession session, Model model,
                              @RequestParam String tripType,
                              @RequestParam String pickupLocation,
                              @RequestParam String dropOffLocation,
                              @RequestParam String pickupDateTime,
                              @RequestParam int passengers,
                              @RequestParam(required = false) String returnDateTime){


        Optional<BookingDto> bookingDtoOptional = inMemoryBookingRepository.findByPickupAndDropOff(pickupLocation, dropOffLocation);

        if (bookingDtoOptional.isPresent()) {
            BookingDto bookingDto = bookingDtoOptional.get();

            LocalDateTime parsedPickupDateTime = LocalDateTime.parse(pickupDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            LocalDateTime parsedReturnDateTime = null;
            if (returnDateTime != null && !returnDateTime.isEmpty()) {
                parsedReturnDateTime = LocalDateTime.parse(returnDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            session.setAttribute("userPickupTime", parsedPickupDateTime);
            session.setAttribute("userReturnTime", parsedReturnDateTime);
            session.setAttribute("userTripType", tripType);
            session.setAttribute("passengers", passengers);
            session.setAttribute("savedBooking", bookingDto);

            return "redirect:/booking/list";
        } else {
            // Handle the case where the booking is not present in the repository
            // You might want to show an error message or redirect to an error page
            return "redirect:/error";
        }


    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/booking";
    }
    @PostMapping("/accept/{id}")
    public String markAccepted(@PathVariable Long id) {
        System.out.println("Marking booking as accepted for ID: " + id);
        this.bookingService.accept(id);
        // If you want to print something after the accept method
        System.out.println("Booking accepted successfully for ID: " + id);
        return "redirect:/booking/info";
    }

    @PostMapping("/cancel/{id}")
    public String userCancel(@PathVariable Long id) {
        System.out.println("Marking booking as accepted for ID: " + id);
        this.bookingService.cancel(id);
        // If you want to print something after the accept method
        System.out.println("Booking accepted successfully for ID: " + id);
        return "redirect:/booking/info";
    }



    @PostMapping("/bookCar/{carId}")
    public String bookCar(@PathVariable Long carId,
                          Model model,
                          HttpServletRequest request,
                          HttpSession session) {

        BookingDto bookingDto = (BookingDto) session.getAttribute("savedBooking");

        LocalDateTime pickupDateTime = (LocalDateTime) session.getAttribute("userPickupTime");
        LocalDateTime returnDateTime = (LocalDateTime) session.getAttribute("userReturnTime");
        String tripType = (String) session.getAttribute("userTripType");
        int passengers = (int) session.getAttribute("passengers");

        String pickupLocation = bookingDto.getPickupLocation();
        String dropOffLocation = bookingDto.getDropOffLocation();

        User user = userSerivce.loadUserByUsername(request.getRemoteUser());

        Car car = carService.findById(carId).orElseThrow(RuntimeException::new);
        User driver = userSerivce.findByCar(car).orElseThrow(RuntimeException::new);

//        Booking booking = new Booking(tripType, pickupLocation, dropOffLocation, pickupDateTime, passengers, returnDateTime, user);
        bookingService.saveBooking(tripType, pickupLocation, dropOffLocation, pickupDateTime, passengers, returnDateTime, user, driver);

        // Optionally, you can add attributes to the model to display messages or data on the page
        model.addAttribute("bookingMessage", "Booking successful");

        return "redirect:/booking/info"; // Redirect to the original page after booking
    }

    @GetMapping("/info")
    public String showBookingInfo(Model model, HttpServletRequest request) {
//        List<Booking> bookings = bookingService.getAllBookings();

        User user = userSerivce.loadUserByUsername(request.getRemoteUser());
        List<Booking> bookingsUser = bookingService.findBookingsByUser(user);
        List<Booking> bookingsDriver = bookingService.findBookingsByDriver(user);

        Boolean isDriver = user.getRole() == Role.ROLE_ADMIN;

        model.addAttribute("bookingsUser", bookingsUser);

        model.addAttribute("currentUser",request.getRemoteUser());

//        model.addAttribute("bookings", bookings);
        model.addAttribute("bookingsDriver",bookingsDriver);
        model.addAttribute("bodyContent","bookingInfo");
        model.addAttribute("showButton", BookingStatus.REQUESTED);
        model.addAttribute("isDriver",isDriver);
        return "master-template";
    }

}