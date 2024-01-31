package com.example.taxi_app_final.model.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeResponse {
    private String intentID;

    private String clientSecret;

}
