package com.nerydlg.apiservices.service;

import com.nerydlg.apiservices.entity.ContactInfo;
import com.nerydlg.apiservices.entity.InteractionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.mail.internet.MimeMessage;
import java.util.function.Function;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final InteractionResponse success;
    private final InteractionResponse error;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
        this.success = new InteractionResponse("OK");
        this.error = new InteractionResponse("MAIL_ERROR");
    }

    public Mono<InteractionResponse> send(ContactInfo contactInfo) {
        return Mono.just(contactInfo)
                .map(this::getContactInfoSimpleMailMessageFunction)
                .map(message1 -> {
                    emailSender.send(message1);
                    return success;
                })
                .doOnError(e -> e.printStackTrace())
                .onErrorReturn(error);
    }

    private SimpleMailMessage getContactInfoSimpleMailMessageFunction(ContactInfo contactInfo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@yoursite.com");
        message.setTo(contactInfo.getTo());
        message.setSubject("Alguien te dejó un mensaje en tu página!!");
        message.setText(stringBody(contactInfo));
        return message;
    }

    private String stringBody(ContactInfo contactInfo) {
        String name = contactInfo.getName() == null ? "No ingresado" : contactInfo.getName();
        String phone = contactInfo.getPhone() == null ? "No ingresado" : contactInfo.getPhone();
        String email = contactInfo.getMail() == null ? "No ingresado" : contactInfo.getMail();
        StringBuilder message = new StringBuilder();
        message.append(contactInfo.getMessage())
                .append("\n")
                .append(name)
                .append("\n")
                .append(phone)
                .append("\n")
                .append(email);
        return message.toString();
    }
}
