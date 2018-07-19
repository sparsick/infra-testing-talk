package com.github.sparsick.infra.testing.infratestingdemoapp.mail;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailClient {

    private final JavaMailSender javaMailSender;

    public MailClient(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(String from, String to, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setText(body);
        javaMailSender.send(message);
    }
}
