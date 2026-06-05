package com.ensa.akinator.controllers;

import com.ensa.akinator.App;
import com.ensa.akinator.Managers.PlayerDAO;
import com.ensa.akinator.Models.Player;
import com.ensa.akinator.Utils.Global;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class SignUpFXMLController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleSignUp(ActionEvent event) {
        String nom = nomField.getText();
        String username = userField.getText();
        String password = passField.getText();

        if (nom.isEmpty() || username.isEmpty() || password.isEmpty()) {
            if (statusLabel != null) {
                statusLabel.setText("Please fill in all fields.");
                statusLabel.setStyle("-fx-text-fill: #ff4a4a;");
            }
            return;
        }

        boolean isRegistered = PlayerDAO.registerPlayer(nom, username, password);

        if (isRegistered) {
            // Définir l'utilisateur comme connecté globalement
            Global.loggedInPlayer = new Player(nom, username, password);
            
            try {
                // Rediriger vers l'écran principal après inscription réussie
                App.setRoot("primary");
            } catch (IOException e) {
                System.err.println("Error loading primary screen: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Registration failed. Username already taken.");
            if (statusLabel != null) {
                statusLabel.setText("Username already exists.");
                statusLabel.setStyle("-fx-text-fill: #ff4a4a;");
            }
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            App.setRoot("LoginFXML");
        } catch (IOException e) {
            System.err.println("Error loading login screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}