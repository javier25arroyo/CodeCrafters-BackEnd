package com.project.demo.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String toEmail, String resetToken) throws MessagingException {
        System.out.println("Sending email to: " + toEmail);
        String subject = "Password Reset Request";
        String resetUrl = "http://localhost:4200/reset-password?token=" + resetToken;

        String content = "<p>Hello,</p>"
                + "<p>We received a request to reset your password.</p>"
                + "<p>Click the following link to change your password:</p>"
                + "<p><a href=\"" + resetUrl + "\">Reset Password</a></p>"
                + "<br>"
                + "<p>If you did not request this, please ignore this message.</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
