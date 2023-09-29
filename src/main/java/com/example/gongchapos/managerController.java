package com.example.gongchapos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class managerController {
    @FXML private Label managerText;

    // This will be executed on startup of the application
    @FXML public void initialize() {
        managerText.setText("You are a manager");
    }

}
