package com.github.sparsick.infra.testing.infratestingdemoapp.mail;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.util.SocketUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;


class MailClientTest {

    private static final ServerSetup SERVER_SETUP_SMTP = new ServerSetup(SocketUtils.findAvailableTcpPort(), null, ServerSetup.PROTOCOL_SMTP);
    private static final ServerSetup SERVER_SETUP_POP3 = new ServerSetup(SocketUtils.findAvailableTcpPort(), null, ServerSetup.PROTOCOL_POP3);

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(new ServerSetup[]{
            SERVER_SETUP_SMTP,
            SERVER_SETUP_POP3});


    private MailClient clientUnderTest;

    @BeforeAll
    static void setUpAll() {
        greenMail.withConfiguration(GreenMailConfiguration.aConfig().withUser("user@example.com","test", "xxx"));
    }

    @AfterEach
    void cleanUp(){
        greenMail.reset();
    }

    @BeforeEach
    void setUp() {
        Properties properties = new Properties();
        properties = SERVER_SETUP_POP3.configureJavaMailSessionProperties(properties, false);
        properties = SERVER_SETUP_SMTP.configureJavaMailSessionProperties(properties, false);
        clientUnderTest = new MailClient(properties, "test", "xxx");
    }

    @Test
    void sendMessage() throws MessagingException, IOException {
        String message = "text";
        String from = "user@example.com";
        String to = "to@example.com";
        clientUnderTest.sendMessage(from, to, message);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);
        String content = (String) receivedMessages[0].getContent();
        assertThat(content.trim()).isEqualTo(message);
    }

    @Test
    void receiveMessages(){
        deliverMessage();

        List<Message> messages = clientUnderTest.receiveMessages();

        assertThat(messages).hasSize(1);
    }

    private void deliverMessage() {
        GreenMailUser user = greenMail.setUser("user@example.com","test", "xxx");
        MimeMessage message = GreenMailUtil.createTextEmail("user@example.com", "from@localhost.com", "A subject", "some text", SERVER_SETUP_POP3);
        user.deliver(message);
    }
}