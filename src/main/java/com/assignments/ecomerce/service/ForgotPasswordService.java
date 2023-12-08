package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.ForgotPasswordToken;
import com.assignments.ecomerce.model.OrderDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

public interface ForgotPasswordService {
    @Autowired
    JavaMailSender javaMailSender = null;
    final int MINUTES = 10;
    public String generateToken();
    public LocalDateTime expireTimeRange();

    public void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException;
    public boolean isExpired(ForgotPasswordToken forgotPasswordToken);

    public String checkValidity(ForgotPasswordToken forgotPasswordToken, Model model);

    public void sendEmailConfirmOrder(String to, String subject, String userName, List<OrderDetail> orderDetails) throws MessagingException, UnsupportedEncodingException;

    public void sendEmailCancelOrder(String to, String subject,String userName) throws MessagingException, UnsupportedEncodingException;
}
