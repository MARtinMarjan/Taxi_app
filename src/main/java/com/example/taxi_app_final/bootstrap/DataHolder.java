package com.example.taxi_app_final.bootstrap;

import com.example.taxi_app_final.model.BookingDto;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<BookingDto> bookingDtos = new ArrayList<>();
    @PostConstruct
    public void init(){
        bookingDtos.add(new BookingDto("Skopje", "Gevgelija", (long) 2.0,  150));
        bookingDtos.add(new BookingDto("Skopje", "Ohrid", (long) 2.41,172 ));
    }
}
