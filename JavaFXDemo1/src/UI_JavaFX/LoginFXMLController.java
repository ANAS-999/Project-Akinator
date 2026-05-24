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

        if (PlayerDAO.isLoginValid(username, password)) {

            try {
                URL viewUrl = Objects.requireNonNull(getClass().getResource("DemoFXML.fxml"), "Fichier DemoFXML introuvable.");
                Parent root = FXMLLoader.load(viewUrl);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException | NullPointerException e) {
                System.err.println(" Erreur de chargement de l'interface : " + e.getMessage());
                e.printStackTrace();
            }

        } else {
            errorLabel.setText("Incorrect password");
            errorLabel.setStyle("-fx-text-fill: red;"); // Rend le texte rouge pour bien signaler l'erreur
        }
    }


    @FXML
    private void goToSignUp(ActionEvent event) {
        try {
            Parent signUpRoot = FXMLLoader.load(getClass().getResource("/UI_JavaFX/SignUp.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(signUpRoot);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de charger la page d'inscription : " + e.getMessage());
        }
    }
}