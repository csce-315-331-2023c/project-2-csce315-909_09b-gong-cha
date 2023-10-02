package com.example.gongchapos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class cashierController {
    @FXML private Label cashierText;
    @FXML private Button taro;
    // This will be executed on startup of the application

    @FXML public void initialize() {
        cashierText.setText("You are a cashier");
    }

    @FXML public void changeText() { taro.setText("this is a test"); }
}