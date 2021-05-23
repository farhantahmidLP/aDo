package project;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailManager {

    public String generatedCode = "";

    public void sendMail(String enteredName, String enteredMail, String messageType) throws Exception {

        String from = "ado.reach@gmail.com";
        String password = "projectado001";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(enteredMail));
            if (messageType.equals("welcomeMail")) {
                message.setSubject("Welcome");
                message.setText("Hello " + enteredName + Emails.welcomeEmail);
            } else if (messageType.equals("verificationMail")) {
                message.setSubject("aDo : Password-reset code.");
                message.setText("Hello " + enteredName + ",\n\nForgot password? Well, that’s quite intimidating but what’s password reset prompt for? 😀\n"
                        + "Here’s your code : " + generatedCode + Emails.resetMail);
            }
            else if (messageType.equals("passwordChangedMail")) {
                message.setSubject("aDo : Password-changed");
                message.setText("Hello " + enteredName + "\nyour password has been changed.");
            }
            Transport.send(message);

        } catch (MessagingException mex) {
            System.out.print(mex);
        }

    }

}
