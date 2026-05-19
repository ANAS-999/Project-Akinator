package UI_JavaFX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFXMLController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // On laisse cette méthode vide pour éviter tout conflit au démarrage
    }    
    
    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            System.out.println("--- Clic détecté sur le bouton Sign up ! ---");
            
            // 1. Charger la nouvelle vue
            URL mainSceneUrl = getClass().getResource("DemoFXML.fxml");
            if (mainSceneUrl == null) {
                System.out.println("❌ Erreur de chemin : Le fichier MainScene.fxml est introuvable !");
                return;
            }
            
            Parent root = FXMLLoader.load(mainSceneUrl);
            
            // 2. Récupérer le Stage actuel via l'événement du clic
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 3. Remplacer la scène
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            System.out.println("--- Changement de scène réussi ! ---");
            
        } catch (IOException e) {
            System.out.println("❌ Erreur lors de l'ouverture de MainScene.fxml :");
            e.printStackTrace();
        }
    }
}