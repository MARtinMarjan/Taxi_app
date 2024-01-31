package com.example.taxi_app_final.web.controller;

import com.example.taxi_app_final.model.*;
import com.example.taxi_app_final.service.BookingService;
import com.example.taxi_app_final.service.CarService;
import com.example.taxi_app_final.service.DriverService;
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
    private final DriverService driverService;
    private final inMemoryBookingRepository inMemoryBookingRepository;

    public BookingController(BookingService bookingService, UserSerivce userSerivce, CarService carService, DriverService driverService, com.example.taxi_app_final.repository.inMemoryBookingRepository inMemoryBookingRepository) {
        this.bookingService = bookingService;
        this.userSerivce = userSerivce;
        this.carService = carService;
        this.driverService = driverService;
        this.inMemoryBookingRepository = inMemoryBookingRepository;
    }

    @GetMapping
    public String listBookings(Model model, HttpServletRequest request) {
        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingDto> bookingDtos = inMemoryBookingRepository.findAll();
        User user = userSerivce.loadUserByUsername(request.getRemoteUser());
        List<Booking> bookingsUser = bookingService.findBookingsByUser(user);

        model.addAttribute("bookingsUser", bookingsUser);

        model.addAttribute("currentUser",request.getRemoteUser());
        model.addAttribute("bookingsDtos", bookingDtos);
        model.addAttribute("bookings", bookings);
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
        List<Car> cars = driverService.findCarsForPassengers(bookingDto.getPassengers());
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

//        Optional<BookingDto> bookingDto = inMemoryBookingRepository.findByPickupAndDropOff(pickupLocation, dropOffLocation);
//        // Parse pickupDateTime to LocalDateTime
//        LocalDateTime parsedPickupDateTime = LocalDateTime.parse(pickupDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//
//        // Parse returnDateTime to LocalDateTime only if it's not null
//        LocalDateTime parsedReturnDateTime = null;
//        if (returnDateTime != null && !returnDateTime.isEmpty()) {
//            parsedReturnDateTime = LocalDateTime.parse(returnDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        }
////        BookingDto savedBooking = inMemoryBookingRepository.save(tripType, bookingDto.get().getPickupLocation(),
////        bookingDto.get().getDropOffLocation(),bookingDto.get().getDuration(),
////        bookingDto.get().getKilometers(), parsedPickupDateTime, passengers, parsedReturnDateTime);
////        session.setAttribute("savedBooking", savedBooking);
//        session.setAttribute("userPickupTime", parsedPickupDateTime );
//        session.setAttribute("userReturnTime",parsedReturnDateTime);
//        session.setAttribute("userTripType", tripType);
//        session.setAttribute("passengers",passengers);
//        session.setAttribute("savedBooking", bookingDto);
////        session.setAttribute("userPickupLocation",bookingDto.get().getPickupLocation());
////        session.setAttribute("userDropOffLocation",bookingDto.get().getDropOffLocation());
////        session.setAttribute("userDuration",bookingDto.get().getDuration());
////        session.setAttribute("userKilometers",bookingDto.get().getKilometers());
//
//        return "redirect:/booking/list";
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

//    @PostMapping("/create")
//    public String createBooking(@ModelAttribute BookingForm bookingForm, Model model) {
//        Optional<Booking> newBooking = bookingService.saveBooking(
//                bookingForm.getTripType(),
//                bookingForm.getPickupLocation(),
//                bookingForm.getDropOffLocation(),
//                bookingForm.getPickupDateTime(),
//                bookingForm.getPassengers(),
//                bookingForm.getReturnDateTime(),
//                bookingForm.getUser()
//        );
//
//        // Handle the case where the booking creation fails (if needed)
//
//        return "redirect:/bookings/list";
//    }

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
        return "redirect:/booking";
    }

    @PostMapping("/cancel/{id}")
    public String userCancel(@PathVariable Long id) {
        System.out.println("Marking booking as accepted for ID: " + id);
        this.bookingService.cancel(id);
        // If you want to print something after the accept method
        System.out.println("Booking accepted successfully for ID: " + id);
        return "redirect:/booking";
    }



    @PostMapping("/bookCar")
    public String bookCar(@RequestParam String tripType,
                          @RequestParam String pickupLocation,
                          @RequestParam String dropOffLocation,
                          @RequestParam String pickupDateTime,
                          @RequestParam int passengers,
                          @RequestParam String returnDateTime,
                          @RequestParam Long carId,
                          Model model,
                          HttpServletRequest request) {
        // Handle the booking logic here using the received car ID
        // ...
        // Parse pickupDateTime to LocalDateTime
        LocalDateTime parsedPickupDateTime = LocalDateTime.parse(pickupDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Parse returnDateTime to LocalDateTime only if it's not null
        LocalDateTime parsedReturnDateTime = null;
        if (returnDateTime != null && !returnDateTime.isEmpty()) {
            parsedReturnDateTime = LocalDateTime.parse(returnDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        User user = userSerivce.loadUserByUsername(request.getRemoteUser());
//        Booking booking = new Booking(tripType, pickupLocation, dropOffLocation, pickupDateTime, passengers, returnDateTime, user);
        bookingService.saveBooking(tripType, pickupLocation, dropOffLocation, parsedPickupDateTime, passengers, parsedReturnDateTime, user);

        // Optionally, you can add attributes to the model to display messages or data on the page
        model.addAttribute("bookingMessage", "Booking successful");

        return "redirect:/booking"; // Redirect to the original page after booking
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
