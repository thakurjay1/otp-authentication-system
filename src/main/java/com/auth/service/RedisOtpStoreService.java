package com.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisOtpStoreService {

    private final RedisTemplate<String, String> redisTemplate;

    public String generateOtp(String mobileNumber) {
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000); // Generate 4-digit OTP
        redisTemplate.opsForValue().set(mobileNumber, otp, 5, TimeUnit.MINUTES); // Set OTP expiration to 5 mins
        return otp;
    }

    public boolean validateOtp(String mobileNumber, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(mobileNumber);
        return otp != null && otp.equals(storedOtp);
    }
}
