package com.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth.dto.LoginRequest;
import com.auth.dto.LoginResponse;
import com.auth.dto.RegisterRequest;
import com.auth.entity.User;
import com.auth.service.AuthService;
import com.auth.service.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully.");
    }

    @GetMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestParam String mobileNumber) {
        String otp = authService.generateOtp(mobileNumber);
        return ResponseEntity.ok("Generated OTP: " + otp);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
        authService.validateOtp(mobileNumber, otp);
        return ResponseEntity.ok("OTP validated successfully.");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String mobileNumber) {
        String otp = authService.resendOtp(mobileNumber);
        return ResponseEntity.ok("Resent OTP: " + otp);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam String mobileNumber) {
        User user = authService.getUserByMobileNumber(mobileNumber);
        return ResponseEntity.ok(user);
    }
}
