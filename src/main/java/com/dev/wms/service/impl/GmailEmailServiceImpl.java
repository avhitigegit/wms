package com.dev.wms.service.impl;

import com.dev.wms.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailEmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String mailTo, String mailSubject, String mailBody) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(mailTo);
            email.setSubject(mailSubject);
            email.setText(mailBody);
            javaMailSender.send(email);
        } catch (Exception e) {
//            throw new EmailSendingException("Email Not Sending !");
        }
    }
}
