package UI_JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;

public class SignUpFXMLController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private void handleSignUp(ActionEvent event) {
        String nom = nomField.getText();
        String user = userField.getText();
        String password = passField.getText();

        System.out.println("Inscription de : " + nom + " (" + user + ")");
        // Ici, tu ajouteras ton code pour sauvegarder dans la base de données
    }

    

    @FXML
    private void goToLogin(ActionEvent event) {
        // Code pour changer de scène et retourner au login
    }
}