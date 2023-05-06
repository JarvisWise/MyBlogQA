package org.simple.blog.tools;

import org.simple.blog.entities.blog.BlogUser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;
import java.util.Properties;

public final class MailUtils {

    private MailUtils() {}

    private static Session getSession() {
        if (!AppProperties.isRead())
            AppProperties.readProperties();

        Properties properties = new Properties();
        properties.put("mail.smtp.host", AppProperties.getMailHost());
        properties.put("mail.smtp.port", AppProperties.getMailPort());
        properties.put("mail.smtp.ssl", AppProperties.getMailSSL());
        properties.put("mail.smtp.auth", AppProperties.getMailAuth());

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppProperties.getMailAddress(), AppProperties.getMailPassword());
            }
        });
    }

    public static void sendEmail(BlogUser user) throws MessagingException, NamingException {

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        Message message = new MimeMessage(getSession());

        message.setFrom(new InternetAddress(AppProperties.getMailAddress()));
        message.setRecipients(
            Message.RecipientType.TO, InternetAddress.parse(user.getEmail())
        );
        message.setSubject("Too Simple Blog (reset-password)");

        String msg = getFullEmailText(user.getFirstName(), AppProperties.getMailResetUrl() + user.getResetToken());
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

    private static String getFullEmailText(String name, String url) {
        return "<div>" +
                "<p>Dear " + name + ",</p>" +
                "<p>To reset your password use next link:</p>" +
                "<a href=\"" + url + "\">" + url + "</a>" +
                "<p>If you do not wish to reset password just ignore this mail!</p>" +
                "<p>Best Regards,</p>" +
                "<p>Too Simple Blog</p>" +
                "</div>";
    }
}
