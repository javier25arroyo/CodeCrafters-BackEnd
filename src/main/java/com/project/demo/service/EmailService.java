package com.project.demo.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Servicio para el envío de correos electrónicos.
 * Gestiona la construcción y el envío de emails, como el de restablecimiento de contraseña.
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Constructor por defecto.
     */
    public EmailService() {
    }

    /**
     * Envía un correo electrónico para el restablecimiento de la contraseña.
     *
     * @param toEmail    La dirección de correo electrónico del destinatario.
     * @param resetToken El token único para el restablecimiento de la contraseña.
     * @throws MessagingException Si ocurre un error al crear o enviar el mensaje.
     */
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
