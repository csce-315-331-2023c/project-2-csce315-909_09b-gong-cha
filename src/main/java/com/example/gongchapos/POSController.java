package com.example.gongchapos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class POSController {
    @FXML private Label headerText, cashierText, managerText;
    private static Stage initialStage;

    // This will be executed on startup of the application
    @FXML public void initialize() {
        headerText.setText("Please select one of the two options");
    }

    // This function is used to pass the initial stage to our new stages
    public static void getInitialStage(Stage stage) {
        initialStage = stage;
    }

    @FXML protected void onCashierButtonClick() throws IOException {
        initialStage.hide();

        // The code below is used to create the manager view
        Stage cashierWindow = new Stage();
        cashierWindow.setTitle("Cashier View");
        FXMLLoader cashierLoader = new FXMLLoader(getClass().getResource("cashierWindow.fxml"));
        cashierWindow.setScene(new Scene(cashierLoader.load(), 320, 240));
        cashierWindow.show();
    }

    @FXML protected void onManagerButtonClick() throws IOException {
        initialStage.hide();

        // The code below is used to create the manager view
        Stage managerWindow = new Stage();
        managerWindow.setTitle("Manager View");
        FXMLLoader managerLoader = new FXMLLoader(getClass().getResource("managerWindow.fxml"));
        managerWindow.setScene(new Scene(managerLoader.load(), 320, 240));
        managerWindow.show();
    }
}