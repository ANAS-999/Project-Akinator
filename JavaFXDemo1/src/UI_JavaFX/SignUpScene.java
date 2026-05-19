package UI_JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class SignUpScene extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root,350, 630);

        primaryStage.setTitle("Sign Up !");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    static void main(String[] args) {
        launch(args);
    }
}
