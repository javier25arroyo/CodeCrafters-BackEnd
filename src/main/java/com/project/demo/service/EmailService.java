package com.project.demo.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para el envío de correos electrónicos.
 * Gestiona la construcción y el envío de emails, como el de restablecimiento de contraseña.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

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
        logger.info("Sending password reset email to: {}", toEmail);

        String subject = "Password Reset Request";
        String resetUrl = appBaseUrl + "/reset-password?token=" + resetToken + "&email=" + toEmail;

        String content = buildResetEmailContent(resetUrl);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    private String buildResetEmailContent(String resetUrl) {
        return "<p>Hola,</p>"
                + "<p>Hemos recibido una solicitud para cambiar tu contraseña</p>"
                + "<p>Siguel el link para cambiar tu contraseña:</p>"
                + "<p><a href=\"" + resetUrl + "\">Cambiar contraseña/a></p>"
                + "<br><p>Si tu no solicitaste esto, porfavor ignora este mensaje.</p>";
    }

    @Value("${app.frontend.base-url}")
    private String appBaseUrl;
}
