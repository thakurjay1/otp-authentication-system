OTP-Based Authentication System with Spring Boot, MySQL, Redis, and Docker
This project implements a mobile number and OTP-based authentication system using Spring Boot, MySQL, Redis, and Docker. The system allows users to register, log in with OTP (One Time Password), and manage their sessions securely using JWT (JSON Web Token). It also supports device fingerprinting for login verification from multiple devices.

Features :- 
User Registration: Register with mobile number, email, password, and device fingerprint.
OTP-Based Login: Authenticate users using OTP sent to their mobile numbers. If OTP is valid, a JWT token is generated.
Device Fingerprinting: Detect and differentiate logins from new devices by comparing device fingerprints.
OTP Expiry & Resend: OTP expires after a certain time. Users can request a new OTP if it expires.
JWT Authentication: Once authenticated, the user is granted a JWT token for secure access to protected endpoints.
Redis Integration: Store OTPs in Redis for fast, in-memory access with expiry management.
MySQL Database: Store user information and authentication data in MySQL.
Dockerized Setup: The project is containerized using Docker, with separate containers for the Spring Boot application, MySQL, and Redis.
Technologies Used
Backend: Java 17, Spring Boot, Spring Security, JWT (JSON Web Token)
Database: MySQL 8.0
Cache: Redis
Build Tool: Maven
Containerization: Docker, Docker Compose
Prerequisites
Before you begin, ensure you have the following installed on your local machine:

Docker: Install Docker
Java 17: Download Java 17
Maven: Install Maven
Postman : Install Postman
Device Fingerprint: The system detects logins from new devices and notifies the user.
Password Storage: Passwords are stored in the database using bcrypt hashing for security.
Conclusion
This project provides a robust authentication system with mobile number-based OTP and device fingerprinting, leveraging Spring Boot, MySQL, Redis, and Docker. It's designed to be highly scalable, secure, and easy to deploy with Docker.
