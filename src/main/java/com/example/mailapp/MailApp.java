package com.example.mailapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Лаунчер приложения
 */
public class MailApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mailapp/layout.fxml"));
        primaryStage.setTitle("Email Sender");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
