package com.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
	    private String mobileNumber;
	    private String otp;
	    private String deviceFingerprintId;
}
