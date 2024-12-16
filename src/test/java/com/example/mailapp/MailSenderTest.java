package com.example.mailapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailSenderTest {
    @Test
    void testConnectionToSmtpServer() {
        // Проверяем подключение к SMTP-серверу
        assertDoesNotThrow(MailSender::testConnection, "Подключение к SMTP-серверу должно быть успешным");
    }

}