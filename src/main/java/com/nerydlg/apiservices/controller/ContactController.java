package com.nerydlg.apiservices.controller;

import com.nerydlg.apiservices.entity.ContactInfo;
import com.nerydlg.apiservices.entity.InteractionResponse;
import com.nerydlg.apiservices.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = {"*"})
public class ContactController {

    private final EmailService emailService;

    @PostMapping("/")
    private Mono<InteractionResponse> sendContactInformation(@RequestBody ContactInfo contactInfo) {
        return emailService.send(contactInfo);
    }

}
