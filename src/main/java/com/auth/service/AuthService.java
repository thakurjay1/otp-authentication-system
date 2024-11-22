package com.auth.service;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.LoginRequest;
import com.auth.dto.LoginResponse;
import com.auth.dto.RegisterRequest;
import com.auth.entity.User;
import com.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisOtpStoreService redisOtpStoreService;
    private final RedisTemplate<String, String> redisTemplate;
    
    private static final long OTP_EXPIRATION_TIME = 5;

    public void register(RegisterRequest request) {
        if (userRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
            throw new IllegalArgumentException("User with this mobile number already exists.");
        }
        User user = new User();
        user.setMobileNumber(request.getMobileNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDeviceFingerprintId(request.getDeviceFingerprintId());
        userRepository.save(user);
    }

    // Generate and store OTP in Redis
    public String generateOtp(String mobileNumber) {
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000);
        redisTemplate.opsForValue().set(mobileNumber, otp, OTP_EXPIRATION_TIME, TimeUnit.MINUTES);
        return otp;
    }

    // Validate OTP
    public boolean validateOtp(String mobileNumber, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(mobileNumber);
        if (storedOtp == null) {
            throw new IllegalArgumentException("OTP expired. Please request a new OTP.");
        }
        if (!storedOtp.equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP. Please try again.");
        }
        return true;
    }

    // Resend OTP
    public String resendOtp(String mobileNumber) {
        return generateOtp(mobileNumber);
    }

    // Get user details by mobile number
    public User getUserByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    // Login user with OTP and generate JWT
    public LoginResponse login(LoginRequest request) {
        if (validateOtp(request.getMobileNumber(), request.getOtp())) {
            User user = getUserByMobileNumber(request.getMobileNumber());
            String jwtToken = jwtUtil.generateToken(user.getMobileNumber());

            if (user.getDeviceFingerprintId().equals(request.getDeviceFingerprintId())) {
                return new LoginResponse("Logged in with registered device.", jwtToken);
            } else {
                return new LoginResponse("Logging in from a new device.", jwtToken);
            }
        }
        throw new IllegalArgumentException("OTP validation failed.");
    }
}
