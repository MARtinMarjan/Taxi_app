package com.example.taxi_app_final.web.controller.Payment;

import com.example.taxi_app_final.model.payment.StripeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StripeController {

    @Value("${stripe.api.publicKey}")
    private String publicKey;
    @GetMapping("/payment")
    private String payment(Model model) {
        model.addAttribute("request", new StripeRequest());
        return "payment";
    }

    @PostMapping("/payment")
    public String showCard(@ModelAttribute  StripeRequest request,
                           BindingResult bindingResult,
                           Model model){
        if (bindingResult.hasErrors()){
            return "payment";
        }
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", request.getAmount());
        model.addAttribute("email", request.getEmail());
        model.addAttribute("productName", request.getProductName());
        return "checkout";
    }
}
