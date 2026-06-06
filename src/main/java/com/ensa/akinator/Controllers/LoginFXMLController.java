package com.ensa.akinator.Controllers;

import com.ensa.akinator.App;
import com.ensa.akinator.Managers.PlayerDAO;
import com.ensa.akinator.Models.Player;
import com.ensa.akinator.Utils.Global;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginFXMLController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            if (errorLabel != null) {
                errorLabel.setText("Please fill in all fields.");
            }
            return;
        }

        if (PlayerDAO.isLoginValid(username, password)) {
            // Récupérer le joueur connecté et le stocker globalement
            Global.loggedInPlayer = null;
            for (Player p : PlayerDAO.getAllPlayers()) {
                if (p.getUserName().equalsIgnoreCase(username)) {
                    Global.loggedInPlayer = p;
                    break;
                }
            }
            // Sécurité si non trouvé
            if (Global.loggedInPlayer == null) {
                Global.loggedInPlayer = new Player("", username, "");
            }

            try {
                // Redirection vers l'écran principal après connexion réussie
                App.setRoot("primary");
            } catch (IOException e) {
                System.err.println("Error loading primary screen: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            if (errorLabel != null) {
                errorLabel.setText("Incorrect credentials.");
            }
        }
    }

    @FXML
    private void goToSignUp(ActionEvent event) {
        try {
            App.setRoot("SignUp");
        } catch (IOException e) {
            System.err.println("Error loading signup screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void goToHome(ActionEvent event) {
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            System.err.println("Error loading primary screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}