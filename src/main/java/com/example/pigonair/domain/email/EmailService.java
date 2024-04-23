package com.example.pigonair.domain.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String recipientEmail, String subject, String body) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body);

            emailSender.send(message);
            System.out.println("이메일 전송 완료");
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류 발생", e);
        }
	}
}
