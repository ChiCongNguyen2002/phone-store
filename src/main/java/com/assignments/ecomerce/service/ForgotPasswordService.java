package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.ForgotPasswordToken;
import jakarta.mail.MessagingException;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public interface ForgotPasswordService {
    public String generateToken();
    public LocalDateTime expireTimeRange();

    public void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException;
    public boolean isExpired(ForgotPasswordToken forgotPasswordToken);

    public String checkValidity(ForgotPasswordToken forgotPasswordToken, Model model);
}
