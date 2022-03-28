package mb.seeme.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EMailService {

    private final String WELCOME_MESSAGE = "Dear user!\n\nI'm very happy to see you as a part of our community. I hope it will be pleasure for you! Just log in and enjoy.\n\nMagda";
    private final String WELCOME_TITLE = "Welcome in the SeeMe community";

    public String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }

    public String getWelcomeTitle() {
        return WELCOME_TITLE;
    }

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@seemeapp.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
