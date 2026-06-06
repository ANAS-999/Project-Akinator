package com.ensa.akinator.Controllers;

import java.io.IOException;

import com.ensa.akinator.App;
import com.ensa.akinator.GameEngines.AIGameEngine;
import com.ensa.akinator.Models.AnswerEnum;
import com.ensa.akinator.Models.Character;
import com.ensa.akinator.Utils.Functions;
import com.ensa.akinator.Utils.Global;
import com.ensa.akinator.Managers.PlayerDAO;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AiGameController {
    private AIGameEngine aiGameEngine;
    private int currentStep = 0;

    @FXML
    private Label questionLabel;

    @FXML
    private ImageView genieImage;

    @FXML
    private Label stepLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    public void initialize() {
        if (Global.loggedInPlayer != null) {
            Global.loggedInPlayer.setScore(0);
            PlayerDAO.updatePlayerScoreAndHighest(Global.loggedInPlayer);
        }

        this.aiGameEngine = new AIGameEngine();
        updateStepLabel();
        updateScoreLabel();
        loadNextQuestionTask(null, -1);
    }

    private void updateStepLabel() {
        if (stepLabel != null) {
            stepLabel.setText(String.valueOf(currentStep));
        }
    }

    private void updateScoreLabel() {
        if (scoreLabel != null) {
            if (Global.loggedInPlayer != null) {
                scoreLabel.setText("⚡ Score: " + Global.loggedInPlayer.getScore());
                scoreLabel.setVisible(true);
                scoreLabel.setManaged(true);
            } else {
                scoreLabel.setVisible(false);
                scoreLabel.setManaged(false);
            }
        }
    }

    @FXML
    private void onYesClicked() throws IOException {
        play(AnswerEnum.YES);
    }

    @FXML
    private void onNoClicked() throws IOException {
        play(AnswerEnum.NO);
    }

    @FXML
    private void onDontKnowClicked() throws IOException {
        play(AnswerEnum.DONT_KNOW);
    }

    @FXML
    private void onProbablyClicked() throws IOException {
        play(AnswerEnum.PROBABLY);
    }

    @FXML
    private void onProbablyNotClicked() throws IOException {
        play(AnswerEnum.PROBABLY_NOT);
    }

    @FXML
    private void handleHomeAction() throws IOException {
        if (Global.loggedInPlayer != null) {
            if (Global.loggedInPlayer.getScore() > Global.loggedInPlayer.getHighestScore()) {
                Global.loggedInPlayer.setHighestScore(Global.loggedInPlayer.getScore());
            }
            Global.loggedInPlayer.setGamesNb(Global.loggedInPlayer.getGamesNb() + 1);
            Global.loggedInPlayer.setScore(0); // Réinitialiser le score pour la prochaine partie

            PlayerDAO.updatePlayerStats(
                    Global.loggedInPlayer.getUserName(),
                    Global.loggedInPlayer.getScore(),
                    Global.loggedInPlayer.getHighestScore(),
                    Global.loggedInPlayer.getGamesNb()
            );
        }
        App.setRoot("primary");
    }

    public void updateAkinatorImage(String mode) {
        String imagePath = "/com/ensa/akinator/assets/akinator-" + mode + ".png";
        Image newImage = new Image(getClass().getResourceAsStream(imagePath));
        genieImage.setImage(newImage);
    }

    private void play(AnswerEnum answerEnum) throws IOException {
        // Augmenter le score de 98 après chaque réponse
        if (Global.loggedInPlayer != null) {
            Global.loggedInPlayer.incrementScore(98);
            PlayerDAO.updatePlayerScoreAndHighest(Global.loggedInPlayer);
            updateScoreLabel();
        }

        if (currentStep >= 30) {
            handleCharacterNotFound();
            return;
        }

        updateAkinatorImage("think");
        this.questionLabel.setText("...");

        loadNextQuestionTask(aiGameEngine, answerEnum.getValue());
    }

    private void loadNextQuestionTask(AIGameEngine engine, int answerValue) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                if (engine == null) {
                    return aiGameEngine.initGame();
                } else {
                    return aiGameEngine.getNextQuestion(answerValue);
                }
            }
        };

        task.setOnSucceeded(event -> {
            pause.setOnFinished(pauseEvent -> {
                String response = task.getValue();
                updateAkinatorImage("ask");
                handleResponse(response);
            });

            pause.play();
        });

        task.setOnFailed(event -> {
            pause.setOnFinished(pauseEvent -> {
                updateAkinatorImage("ask");
                Throwable exception = task.getException();
                Platform.runLater(() -> {
                    Functions.showErrorAlert("AI ERROR", "Failed to connect to AI server: " + exception.getMessage());
                    returnToPrimary();
                });
            });

            pause.play();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void handleResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            returnToPrimary();
            return;
        }

        if (response.toLowerCase().contains("i think it is") || response.toLowerCase().contains("i think is")) {
            String guessedName = getCharacterName(response);
            String image = "/com/ensa/akinator/assets/character.png";

            Global.characterFounded = new Character(-1, guessedName, -1, image);

            // Fin de partie réussie : sauvegarde et incrémentation des statistiques
            if (Global.loggedInPlayer != null) {
                Global.lastMatchScore = Global.loggedInPlayer.getScore();
                if (Global.loggedInPlayer.getScore() > Global.loggedInPlayer.getHighestScore()) {
                    Global.loggedInPlayer.setHighestScore(Global.loggedInPlayer.getScore());
                }
                Global.loggedInPlayer.setGamesNb(Global.loggedInPlayer.getGamesNb() + 1);
                Global.loggedInPlayer.setScore(0); // Réinitialiser le score pour la prochaine partie

                PlayerDAO.updatePlayerStats(
                        Global.loggedInPlayer.getUserName(),
                        Global.loggedInPlayer.getScore(),
                        Global.loggedInPlayer.getHighestScore(),
                        Global.loggedInPlayer.getGamesNb()
                );
            }

            Platform.runLater(() -> {
                try {
                    App.setRoot("character");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return;
        }

        // Otherwise update question
        this.questionLabel.setText(response);
        currentStep++;
        updateStepLabel();
    }

    private String getCharacterName(String response) {
        String guessedName = response;
        int index = response.toLowerCase().indexOf("i think it is");

        if (index != -1) {
            guessedName = response.substring(index + "i think it is".length()).trim();
        } else {
            index = response.toLowerCase().indexOf("i think is");
            if (index != -1) {
                guessedName = response.substring(index + "i think is".length()).trim();
            }
        }
        if (guessedName.endsWith(".") || guessedName.endsWith("!")) {
            guessedName = guessedName.substring(0, guessedName.length() - 1);
        }

        return guessedName;
    }

    private void returnToPrimary() {
        Platform.runLater(() -> {
            try {
                App.setRoot("primary");
            } catch (IOException ignored) {
            }
        });
    }

    private void handleCharacterNotFound() throws IOException {
        // Fin de partie perdue : sauvegarde et incrémentation des statistiques
        PlayerDAO.handleCharacterNotFound();

        App.setRoot("notfound");
    }

}
