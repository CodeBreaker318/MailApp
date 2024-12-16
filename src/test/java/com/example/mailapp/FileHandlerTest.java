package com.example.mailapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {
    private FileHandler fileHandler;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler();
    }

    @Test
    void testLoadEmails() throws IOException {
        // Создаем временный файл
        File tempFile = File.createTempFile("emails1", ".txt");
        try (FileWriter writer = new FileWriter(tempFile, StandardCharsets.UTF_8)) {
            writer.write("user1@example.com;Толян\nuser2@example.com;Колян");
        }

        // Загружаем email
        List<Email> emails = fileHandler.loadEmails(tempFile);

        // Проверяем результат
        assertNotNull(emails);
        assertEquals(2, emails.size());
        assertEquals("user1@example.com", emails.get(0).getAddress());
        assertEquals("Толян", emails.get(0).getName());
        assertEquals("user2@example.com", emails.get(1).getAddress());
        assertEquals("Колян", emails.get(1).getName());

        // Удаляем временный файл
        tempFile.deleteOnExit();
    }

    @Test
    void testLoadEmailsWithMalformedData() {
        // Создаем временный файл с некорректным форматом
        File tempFile = null;
        try {
            tempFile = File.createTempFile("emails_invalid", ".txt");
            try (FileWriter writer = new FileWriter(tempFile, StandardCharsets.UTF_8)) {
                writer.write("user1@example.com-John\nuser2@example.com-Jane"); // Некорректный разделитель
            }

            // Проверяем, что метод выбрасывает исключение
            File finalTempFile = tempFile;
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> fileHandler.loadEmails(finalTempFile));
        } catch (IOException e) {
            fail("Ошибка при создании временного файла: " + e.getMessage());
        } finally {
            if (tempFile != null) {
                tempFile.deleteOnExit();
            }
        }
    }

    @Test
    void testLoadTemplate() throws IOException {
        // Создаем временный файл
        File tempFile = File.createTempFile("template", ".txt");
        String content = "Здравствуйте, {name}!\nДобро пожаловать.";
        try (FileWriter writer = new FileWriter(tempFile, StandardCharsets.UTF_8)) {
            writer.write(content);
        }

        // Загружаем шаблон
        String template = fileHandler.loadTemplate(tempFile);

        // Проверяем результат
        assertNotNull(template);
        assertEquals(content, template);

        // Удаляем временный файл
        tempFile.deleteOnExit();
    }
}