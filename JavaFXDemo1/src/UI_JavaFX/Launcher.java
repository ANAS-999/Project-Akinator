package UI_JavaFX;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        // Démarre l'application en contournant les restrictions de module JavaFX
        Application.launch(MainScene.class, args);
    }
}