package com.example.mailapp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Класс для отправки email-сообщений через SMTP-сервер Gmail.
 */
public class MailSender {
    private static final Logger logger = LogManager.getLogger(MailSender.class);

    private static final String USERNAME = "rzspov@yandex.ru";
    private static final String PASSWORD = "timnynttmrwwrmhj";

    /**
     * Отправляет email-сообщение указанному получателю.
     *
     * @param to          Email-адрес получателя.
     * @param messageText Текст сообщения.
     */
    public static void sendEmail(String to, String messageText) {
        logger.info("Начало отправки сообщения на: " + to);

        Session session = createSession();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Сообщение");
            message.setText(messageText);

            Transport.send(message);
            logger.info("Сообщение успешно отправлено на: " + to);
        } catch (MessagingException e) {
            logger.error("Ошибка при отправке сообщения на: " + to, e);
            throw new RuntimeException("Ошибка отправки сообщения: " + e.getMessage(), e);
        }
    }

    /**
     * Проверяет возможность подключения к SMTP-серверу.
     *
     * @throws MessagingException Если соединение не удалось.
     */
    public static void testConnection() throws MessagingException {
        logger.info("Проверка подключения к SMTP-серверу.");

        Session session = createSession();
        Transport transport = session.getTransport("smtp");
        transport.connect();
        transport.close();

        logger.info("Подключение к SMTP-серверу успешно.");
    }

    /**
     * Создает сессию для подключения к SMTP-серверу.
     *
     * @return Сессия для работы с почтой.
     */
    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }
}