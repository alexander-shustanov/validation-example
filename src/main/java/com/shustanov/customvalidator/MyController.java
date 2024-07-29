package com.shustanov.customvalidator;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Validated
public class MyController {
    @PostMapping("/card")
    void checkCardNumber(@RequestParam @Valid @CardNumber String cardNumber) {
    }

    @PostMapping("/password")
    void checkPassword(@RequestParam @Valid @ValidPassword String password) {
    }
}

