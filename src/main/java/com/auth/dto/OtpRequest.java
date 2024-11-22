package com.auth.dto;

import lombok.Data;

@Data
public class OtpRequest {
    private String mobileNumber;
    private String otp;
}
