package com.example.mailapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для работы с файлами.
 */
public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    /**
     * Загружает список email и имен из файла.
     *
     * @param file Файл с email-адресами.
     * @return Список объектов Email.
     * @throws IOException Если файл не найден или произошла ошибка чтения.
     */
    public List<Email> loadEmails(File file) throws IOException {
        logger.info("Загрузка email из файла: " + file.getAbsolutePath());
        return Files.lines(Paths.get(file.toURI()))
                .map(line -> {
                    String[] parts = line.split(";");
                    String email = parts[0];
                    String name = parts[1];
                    return new Email(email, name);
                })
                .collect(Collectors.toList());
    }

    /**
     * Загружает содержимое шаблона письма из файла.
     *
     * @param file Файл с шаблоном.
     * @return Содержимое шаблона письма.
     * @throws IOException Если файл не найден или произошла ошибка чтения.
     */
    public String loadTemplate(File file) throws IOException {
        logger.info("Загрузка шаблона письма из файла: " + file.getAbsolutePath());
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }
}
