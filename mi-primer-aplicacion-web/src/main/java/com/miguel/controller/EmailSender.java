package com.miguel.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author miguel
 */
public class EmailSender {

    // Permitir envío en apps no seguras
    // https://myaccount.google.com/u/3/lesssecureapps
    private static final String SENDER = "miguelpiniaprueba@gmail.com";
    private static final String PASSWORD = "abcdef123456_";

    public static void sendEmail(String to, String title, String html)
            throws MessagingException {
        Session session = createSession();
        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, to, title, html);
        System.out.println("Enviando correo!");
        Transport.send(message);
    }

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, PASSWORD);
            }
        });
        return session;
    }

    private static void prepareEmailMessage(MimeMessage message, String to, String title, String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

}
