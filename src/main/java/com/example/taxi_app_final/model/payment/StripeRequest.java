package com.example.taxi_app_final.model.payment;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeRequest {
    @NotNull
    private Long amount;

    private String email;


    private String productName;
}
