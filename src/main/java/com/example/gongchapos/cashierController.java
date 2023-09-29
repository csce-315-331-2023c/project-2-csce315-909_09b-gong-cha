package com.example.gongchapos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class cashierController {
    @FXML private Label cashierText;

    // This will be executed on startup of the application
    @FXML public void initialize() {
        cashierText.setText("You are a cashier");
    }

}