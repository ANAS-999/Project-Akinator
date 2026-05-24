package UI_JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import playerDataBase.PlayerDAO;

import java.io.IOException;

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
                statusLabel.setText("Veuillez remplir tous les champs.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
            return;
        }

        boolean isRegistered = PlayerDAO.registerPlayer(nom, username, password);

        if (isRegistered) {
            if (statusLabel != null) {
                statusLabel.setText("Inscription réussie !");
                statusLabel.setStyle("-fx-text-fill: green;");
            }


        } else {
            System.out.println(" Échec de l'inscription. Pseudo déjà pris.");
            if (statusLabel != null) {
                statusLabel.setText("Ce nom d'utilisateur existe déjà.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {

            Parent loginRoot = FXMLLoader.load(getClass().getResource("/UI_JavaFX/LoginFXML.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(loginRoot);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la page de connexion : " + e.getMessage());
        }
    }
}