package com.example.mailapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Контроллер для управления логикой приложения.
 * Предоставляет функционал загрузки файлов с email-адресами и шаблоном письма,
 * а также запуска рассылки с персонализацией сообщений.
 */
public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @FXML
    private Button loadEmailsButton;

    @FXML
    private Button loadTemplateButton;

    @FXML
    private Button startMailingButton;

    private File emailsFile;
    private File templateFile;

    private final FileHandler fileHandler = new FileHandler();

    /**
     * Инициализация контроллера.
     * Привязывает действия к кнопкам интерфейса.
     */
    @FXML
    private void initialize() {
        logger.info("Инициализация контроллера.");

        loadEmailsButton.setOnAction(event -> chooseEmailsFile());
        loadTemplateButton.setOnAction(event -> chooseTemplateFile());
        startMailingButton.setOnAction(event -> startMailing());
    }

    /**
     * Открывает диалог выбора файла для загрузки email-адресов.
     * Выбранный файл сохраняется для последующего использования.
     */
    private void chooseEmailsFile() {
        FileChooser fileChooser = new FileChooser();
        File projectDir = new File(System.getProperty("user.dir"));
        if (projectDir.exists() && projectDir.isDirectory()) {
            fileChooser.setInitialDirectory(projectDir);
        }
        fileChooser.setTitle("Выберите файл с email-адресами");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        emailsFile = fileChooser.showOpenDialog(loadEmailsButton.getScene().getWindow());

        if (emailsFile != null) {
            logger.info("Выбран файл с email-адресами: " + emailsFile.getAbsolutePath());
        } else {
            logger.warn("Файл с email-адресами не выбран.");
        }
    }

    /**
     * Открывает диалог выбора файла для загрузки шаблона письма.
     * Выбранный файл сохраняется для последующего использования.
     */
    private void chooseTemplateFile() {
        FileChooser fileChooser = new FileChooser();
        File projectDir = new File(System.getProperty("user.dir"));
        if (projectDir.exists() && projectDir.isDirectory()) {
            fileChooser.setInitialDirectory(projectDir);
        }
        fileChooser.setTitle("Выберите файл с шаблоном письма");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        templateFile = fileChooser.showOpenDialog(loadTemplateButton.getScene().getWindow());

        if (templateFile != null) {
            logger.info("Выбран файл с шаблоном письма: " + templateFile.getAbsolutePath());
        } else {
            logger.warn("Файл с шаблоном письма не выбран.");
        }
    }

    /**
     * Запускает рассылку сообщений.
     * Загружает email-адреса и шаблон письма из выбранных файлов,
     * затем отправляет персонализированные сообщения каждому получателю.
     * <p>
     * Персонализация осуществляется заменой плейсхолдера {name} на имя получателя,
     * извлеченное из email-адреса.
     * </p>
     */
    private void startMailing() {
        if (emailsFile == null || templateFile == null) {
            logger.error("Файлы с email-адресами или шаблоном письма не выбраны.");
            return;
        }

        try {
            // Загрузка email-адресов и шаблона
            List<Email> emails = fileHandler.loadEmails(emailsFile);
            String template = fileHandler.loadTemplate(templateFile);

            // Отправка писем
            emails.forEach(email -> {
                String personalizedMessage = template.replace("{name}", email.getName());
                MailSender.sendEmail(email.getAddress(), personalizedMessage);
                logger.info("Отправлено письмо на: " + email.getAddress());
            });

            logger.info("Рассылка завершена.");
        } catch (Exception e) {
            logger.error("Ошибка при выполнении рассылки.", e);
        }
    }
}