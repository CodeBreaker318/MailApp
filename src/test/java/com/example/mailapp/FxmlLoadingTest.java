package com.example.mailapp;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FxmlLoadingTest {

    @BeforeAll
    static void initToolkit() {
        new Thread(() -> {
            Platform.startup(() -> {});
        }).start();
    }
    @Test
    void testLoadFxml() {
        // Загружаем FXML
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/mailapp/layout.fxml"));
            assertNotNull(root, "FXML файл должен быть загружен");
        } catch (IOException e) {
            fail("Ошибка при загрузке FXML: " + e.getMessage());
        }
    }
}
