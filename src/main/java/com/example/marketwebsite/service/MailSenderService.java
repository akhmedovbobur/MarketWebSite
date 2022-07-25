package com.example.marketwebsite.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {

    private JavaMailSender javaMailSender;

    void sendEmail(String toAccount, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAccount);  // example@gmail.com
        simpleMailMessage.setSubject("Digital Market verification"); // Subject of mail
        simpleMailMessage.setText(text); // Content of mail
        javaMailSender.send(simpleMailMessage);
    }
}
