package com.github.sparsick.infra.testing.infratestingdemoapp.mail;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;


class MailClientTest {

    private GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP);
    private MailClient clientUnderTest;

    @BeforeEach
    void setUp() {
        JavaMailSender javaMailSender = createJavaMailSender();
        clientUnderTest = new MailClient(javaMailSender);
        greenMail.withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "xxx"));
        greenMail.start();
    }

    @Test
    void sendMessage() throws MessagingException, IOException {
        String message = "text";
        String from = "from@example.com";
        String to = "to@example.com";
        clientUnderTest.sendMessage(from, to, message);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);
        String content = (String) receivedMessages[0].getContent();
        assertThat(content.trim()).isEqualTo(message);

    }

    @AfterEach
    void cleanUp() {
        greenMail.stop();
    }

    @NotNull
    private JavaMailSenderImpl createJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.port", "3025");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.user", "from@example.com");
        props.setProperty("mail.smtp.host", "localhost");
        props.setProperty("mail.smtp.from", "from@example.com");
        javaMailSender.setJavaMailProperties(props);
        javaMailSender.setUsername("test");
        javaMailSender.setPassword("xxx");
        return javaMailSender;
    }
}