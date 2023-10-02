package com.example.gongchapos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class POSApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
         FXMLLoader fxmlLoader = new FXMLLoader(POSApplication.class.getResource("gong-cha.fxml"));
         Scene initialWindow = new Scene(fxmlLoader.load(), 320, 240);
         initialWindow.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
         POSController.getInitialStage(stage);

         stage.setTitle("Gong Cha");
         stage.setScene(initialWindow);
         stage.show();
    }

    public static void main(String[] args) {
         launch();
    }
}