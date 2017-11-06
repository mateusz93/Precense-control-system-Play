package services;

import lombok.extern.slf4j.Slf4j;
import models.User;
import play.db.ebean.Transactional;

import javax.inject.Singleton;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Transactional
@Singleton
public class EmailService {

    private final String emailFromAdress = "dmcs.p.lodz.pl@gmail.com";
    private final String emailFromUsername = "dmcs.p.lodz.pl";
    private final String emailFromPass = "dmcs1234";
    private final String emailPort = "587";
    private final String emailHost = "smtp.gmail.com";
    private final String emailSmtpAuth = "true";
    private final String emailSmtpStarttlsEnable = "true";
    private final String contactEmailContent = "Dziękujemy za kontakt. Odpowiemy w ciągu 24godzin.";
    private final String contactEmailSubject = "Kontakt";

    public void sendActivationLink(User user, String activationToken) {
        String recipient = user.getEmail();
        String subject = "Link aktywacyjny";
        String content = "Poniżej znajduje sie link aktywacyjny wazny 24h.\n\n";
        content += activationToken + "\n\n";
        sendEmail(recipient, subject, content);
    }

    public void sendEmail(String recipient, String subject, String content) {
        Session session = getSession(emailFromUsername, emailFromPass, getProperties());

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFromAdress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            log.info("Sent message successfully to " + recipient);
        } catch (MessagingException e) {
            log.error("Message not sent");
            throw new RuntimeException(e);
        }
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", emailSmtpAuth);
        props.put("mail.smtp.starttls.enable", emailSmtpStarttlsEnable);
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtp.port", emailPort);
        return props;
    }

    private Session getSession(String username, String password, Properties props) {
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

}
