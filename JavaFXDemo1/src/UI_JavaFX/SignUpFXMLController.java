package UI_JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import playerDataBase.PlayerDAO;

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
            System.out.println("✅ Inscription réussie pour : " + nom + " (" + username + ")");
            if (statusLabel != null) {
                statusLabel.setText("Inscription réussie !");
                statusLabel.setStyle("-fx-text-fill: green;");
            }

            // Optionnel : Tu peux appeler directement ta méthode pour rediriger vers le Login
            // goToLogin(event);

        } else {
            System.out.println("❌ Échec de l'inscription. Pseudo déjà pris.");
            if (statusLabel != null) {
                statusLabel.setText("Ce nom d'utilisateur existe déjà.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        // Code pour changer de scène et retourner au login
        System.out.println("--- Retour à la page de Login ---");
    }
}