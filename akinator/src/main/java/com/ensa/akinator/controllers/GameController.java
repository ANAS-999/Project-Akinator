package com.ensa.akinator.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.ensa.akinator.App;
import com.ensa.akinator.GameEngines.GameEngine;
import com.ensa.akinator.Models.AnswerEnum;
import com.ensa.akinator.Models.Question;
import com.ensa.akinator.Models.Character;
import com.ensa.akinator.Utils.Functions;
import com.ensa.akinator.Utils.Global;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GameController {
    GameEngine gameEngine;
    Question currentQuestion;

    @FXML
    private Label questionLabel;

    @FXML
    private ImageView genieImage;

    @FXML
    private Label stepLabel;

    private int currentStep = 1;

    @FXML
    public void initialize() throws IOException {
        try {
            this.gameEngine = new GameEngine("Anas");
            this.currentQuestion = gameEngine.getBestQuestion();
            this.questionLabel.setText(this.currentQuestion.getText());
            updateStepLabel();

        } catch (SQLException e) {
            Functions.showErrorAlert("ERROR", e.getMessage());
            returnToPrimary();
        }
    }

    private void updateStepLabel() {
        if (stepLabel != null) {
            stepLabel.setText("QUESTION N° " + currentStep);
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

    // ! Methods
    private void returnToPrimary() {
        Platform.runLater(() -> {
            try {
                App.setRoot("primary");
            } catch (IOException e) {
            }
        });
    }

    public void updateAkinatorImage(String mode) {
        String imagePath = "/com/ensa/akinator/assets/akinator-" + mode + ".png";
        Image newImage = new Image(getClass().getResourceAsStream(imagePath));
        genieImage.setImage(newImage);
    }

    private void play(AnswerEnum answerEnum) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));

        updateAkinatorImage("think");
        this.questionLabel.setText("...");

        pause.setOnFinished(event -> {
            try {
                handlePlay(answerEnum);
            } catch (SQLException e) {
                Platform.runLater(
                        () -> Functions.showErrorAlert("SQL ERROR", e.getMessage()));
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(
                        () -> Functions.showErrorAlert("IO ERROR", e.toString()));
            } finally {
                updateAkinatorImage("ask");
            }
        });

        pause.play();
    }

    private void handlePlay(AnswerEnum answerEnum) throws SQLException, IOException {
        int answer = answerEnum.getValue() == 1 ? 1 : 0;
        gameEngine.filterCharacters(currentQuestion.getId(), answer);

        if (gameEngine.checkWinCondition()) {
            handleCharacterFound();
            return;
        }

        currentStep++;
        updateStepLabel();
        this.currentQuestion = gameEngine.getBestQuestion();
        this.questionLabel.setText(this.currentQuestion.getText());
    }

    private void handleCharacterFound() throws SQLException, IOException {
        Character character = gameEngine.getFoundedCharacter();

        /*
         * Platform.runLater(
         * () -> {
         * Functions.showInfoAlert("Akinator", "The character is '" +
         * character.getName() + "'!");
         * });
         */

        Global.characterFounded = character;
        App.setRoot("character");
    }
}