package com.ensa.akinator.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ensa.akinator.App;
import com.ensa.akinator.Utils.Global;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CharacterController {

    @FXML
    private Label characterLabel;

    @FXML
    private ImageView characterImage;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label matchScoreLabel;

    @FXML
    private void initialize() {
        setCharacterName();
        setCharacterImage();
        applyAnimations();
        updateScoreLabel();
        updateMatchScoreLabel();
    }

    private void updateMatchScoreLabel() {
        if (matchScoreLabel != null) {
            matchScoreLabel.setText("Score: " + Global.lastMatchScore);
        }
    }

    private void updateScoreLabel() {
        if (scoreLabel != null) {
            if (Global.loggedInPlayer != null) {
                scoreLabel.setText("👤 " + Global.loggedInPlayer.getUserName() + 
                                   "  |  🎮 Games: " + Global.loggedInPlayer.getGamesNb() + 
                                   "  |  ⭐ Best: " + Global.loggedInPlayer.getHighestScore());
                scoreLabel.setVisible(true);
                scoreLabel.setManaged(true);
            } else {
                scoreLabel.setVisible(false);
                scoreLabel.setManaged(false);
            }
        }
    }

    private void applyAnimations() {
        FadeTransition fadeInImage = new FadeTransition(Duration.seconds(1.5), characterImage);
        fadeInImage.setFromValue(0.0);
        fadeInImage.setToValue(1.0);

        FadeTransition fadeInLabel = new FadeTransition(Duration.seconds(1.5), characterLabel);
        fadeInLabel.setFromValue(0.0);
        fadeInLabel.setToValue(1.0);

        fadeInImage.play();
        fadeInLabel.play();
    }

    private void setCharacterName() {
        if (Global.characterFounded != null) {
            String characterName = Global.characterFounded.getName();
            characterLabel.setText(characterName);
        }
    }

    public void setCharacterImage() {
        if (Global.characterFounded != null) {
            String imageUrl = Global.characterFounded.getImagePath();
            try {
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    System.out.println(imageUrl);
                    if (imageUrl.startsWith("http")) {
                        loadSecuredWebImage(imageUrl);
                    } else {
                        characterImage.setImage(new Image(getClass().getResourceAsStream(imageUrl)));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }
    }

    public void loadSecuredWebImage(String imageUrlString) {
        Task<Image> downloadTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                URL url = new URL(imageUrlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

                try (InputStream in = connection.getInputStream()) {
                    return new Image(in);
                }
            }
        };

        downloadTask.setOnSucceeded(event -> {
            characterImage.setImage(downloadTask.getValue());
            System.out.println("Image loaded successfully!");
        });

        downloadTask.setOnFailed(event -> {
            System.err.println("Failed to download image from: " + imageUrlString);
            downloadTask.getException().printStackTrace();
        });

        Thread thread = new Thread(downloadTask);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void onPlayAgainClicked() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void onHomeClicked() throws IOException {
        App.setRoot("primary");
    }
}
