package services;

import lombok.extern.slf4j.Slf4j;
import models.User;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import play.db.ebean.Transactional;

import javax.inject.Singleton;

@Slf4j
@Transactional
@Singleton
public class EmailService {

    private final String emailFromAddress = "dmcs.p.lodz.pl@gmail.com";
    private final String emailFromUsername = "dmcs.p.lodz.pl";
    private final String emailFromPass = "dmcs1234";
    private final int emailPort = 465;
    private final String emailHost = "smtp.gmail.com";
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
        try {
            Email email = new SimpleEmail();
            email.setHostName(emailHost);
            email.setSmtpPort(emailPort);
            email.setAuthenticator(new DefaultAuthenticator(emailFromUsername, emailFromPass));
            email.setSSLOnConnect(true);
            email.setFrom(emailFromAddress);
            email.setSubject(subject);
            email.setMsg(content);
            email.addTo(recipient);
            email.send();
        } catch (EmailException e) {
            log.error("Critical error during sending email.", e);
            return;
        }
        log.info("Sent message successfully to " + recipient);
    }

}