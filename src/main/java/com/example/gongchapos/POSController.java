package com.example.gongchapos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class POSController {
    @FXML
    private Label headerText;

    @FXML
    // This will be executed on startup of the application
    public void initialize() {
        headerText.setText("Please select one of the two options");
    }

    @FXML
    protected void onCashierButtonClick() {
        headerText.setText("You are a cashier");
    }

    @FXML
    protected void onManagerButtonClick() {
        headerText.setText("You are a manager");
    }
}