package com.test.actions;

import com.test.util.Constants;
import com.test.util.reporter.Reporter;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailActions {

    private Properties mailProperties;
    private Session mailSession;
    private MimeMessage mailMessage;
    private MimeBodyPart messageBodyPart;
    private MimeMultipart multipart;
    private Transport transport;

    public void sendReports() throws MessagingException {
        mailProperties = System.getProperties();

        mailProperties.put("mail.smtp.port", "587");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");

        mailSession = Session.getDefaultInstance(mailProperties, null);

        mailMessage = new MimeMessage(mailSession);
        messageBodyPart = new MimeBodyPart();
        multipart = new MimeMultipart();

        messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(Constants.FILE_PATH)));
        messageBodyPart.setFileName(Constants.FILE_PATH);
        multipart.addBodyPart(messageBodyPart);

        mailMessage.setSubject("Testing calculator");
        mailMessage.addRecipients(Message.RecipientType.TO, Constants.TO_EMAIL);
        mailMessage.setContent(multipart);

        transport = mailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", Constants.MAIL_USERNAME, Constants.MAIL_PASSWORD);

        Reporter.log("<-Sending message success->");
    }
}
