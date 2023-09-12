package com.example.digital_shop.service.mailservice;

public interface MailService{
     String sendVerificationCode(String email, String verificationCode);
}
