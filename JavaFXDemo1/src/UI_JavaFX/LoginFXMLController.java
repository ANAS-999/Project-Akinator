package UI_JavaFX;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import playerDataBase.*;

public class LoginFXMLController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // On laisse cette méthode vide pour éviter tout conflit au démarrage
    }

    @FXML
    private TextField usernameField;   // Le champ du nom d'utilisateur

    @FXML
    private PasswordField passwordField; // Le champ du mot de passe

    @FXML
    private Label errorLabel;          // Un Label vide sous les champs pour afficher l'erreur

    @FXML
    private void handleLogin(ActionEvent event) {
        // 1. Récupérer les données saisies
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 2. Vérifier les identifiants avec ta méthode de BDD
        if (PlayerDAO.isLoginValid(username, password)) {

            // --- SUCCÈS : On change de scène ---
            try {
                URL viewUrl = Objects.requireNonNull(getClass().getResource("DemoFXML.fxml"), "Fichier DemoFXML introuvable.");
                Parent root = FXMLLoader.load(viewUrl);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException | NullPointerException e) {
                System.err.println("❌ Erreur de chargement de l'interface : " + e.getMessage());
                e.printStackTrace();
            }

        } else {
            // --- ÉCHEC : On affiche le message d'erreur ---
            errorLabel.setText("Incorrect password");
            errorLabel.setStyle("-fx-text-fill: red;"); // Rend le texte rouge pour bien signaler l'erreur
        }
    }
}