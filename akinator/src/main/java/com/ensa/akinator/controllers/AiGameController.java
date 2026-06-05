package com.ensa.akinator.controllers;

import java.io.IOException;

import com.ensa.akinator.App;
import com.ensa.akinator.GameEngines.AIGameEngine;
import com.ensa.akinator.Models.AnswerEnum;
import com.ensa.akinator.Models.Character;
import com.ensa.akinator.Utils.Functions;
import com.ensa.akinator.Utils.Global;
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
    private int currentStep = 1;

    @FXML
    private Label questionLabel;

    @FXML
    private ImageView genieImage;

    @FXML
    private Label stepLabel;

    @FXML
    public void initialize() {
        this.aiGameEngine = new AIGameEngine();
        updateStepLabel();
        loadNextQuestionTask(null, -1);
    }

    private void updateStepLabel() {
        if (stepLabel != null) {
            stepLabel.setText(String.valueOf(currentStep));
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
        App.setRoot("primary");
    }

    public void updateAkinatorImage(String mode) {
        String imagePath = "/com/ensa/akinator/assets/akinator-" + mode + ".png";
        Image newImage = new Image(getClass().getResourceAsStream(imagePath));
        genieImage.setImage(newImage);
    }

    private void play(AnswerEnum answerEnum) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

        updateAkinatorImage("think");
        this.questionLabel.setText("...");

        pause.setOnFinished(event -> {
            loadNextQuestionTask(aiGameEngine, answerEnum.getValue());
        });

        pause.play();
    }

    private void loadNextQuestionTask(AIGameEngine engine, int answerValue) {
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
            String response = task.getValue();
            updateAkinatorImage("ask");
            handleResponse(response);
        });

        task.setOnFailed(event -> {
            updateAkinatorImage("ask");
            Throwable exception = task.getException();
            Platform.runLater(() -> {
                Functions.showErrorAlert("AI ERROR", "Failed to connect to AI server: " + exception.getMessage());
                returnToPrimary();
            });
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
}
