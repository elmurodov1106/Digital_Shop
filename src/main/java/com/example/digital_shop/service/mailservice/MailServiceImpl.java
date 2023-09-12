package com.example.digital_shop.service.mailservice;

import com.example.digital_shop.domain.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;
    public String sendVerificationCode(String email, String verificationCode) {
        String message = "This is your verification code: " + verificationCode;
        return sendMail(new MailDto(message,email));
    }

    private String sendMail(MailDto mailDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailDto.getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(mailDto.getMessage());
        mailSender.send(simpleMailMessage);
        return "Verification code has sent your email";
    }

}
