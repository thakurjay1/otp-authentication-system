package com.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String mobileNumber;
    private String email;
    private String password;
    private String deviceFingerprintId;
}
