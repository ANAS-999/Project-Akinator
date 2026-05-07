package com.ensa.akinator.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.ensa.akinator.App;
import com.ensa.akinator.GameEngines.GameEngine;
import com.ensa.akinator.Models.AnswerEnum;
import com.ensa.akinator.Models.Question;
import com.ensa.akinator.Models.Character;
import com.ensa.akinator.Utils.Functions;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameController {
    GameEngine gameEngine;
    Question currentQuestion;

    @FXML
    private Label questionLabel;

    @FXML
    public void initialize() throws IOException {
        try {
            this.gameEngine = new GameEngine("Anas");
            this.currentQuestion = gameEngine.getBestQuestion();
            this.questionLabel.setText(this.currentQuestion.getText());

        } catch (SQLException e) {
            Functions.showErrorAlert("ERROR", e.getMessage());
            returnToPrimary();
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

    private void play(AnswerEnum answerEnum) {
        try {
            int answer = answerEnum.getValue() == 1 ? 1 : 0;
            gameEngine.filterCharacters(currentQuestion.getId(), answer);

            if (gameEngine.checkWinCondition()) {
                Character character = gameEngine.getFoundedCharacter();
                Functions.showInfoAlert("Akinator", "The character is '" + character.getName() + "'!");
                return;
            }

            this.currentQuestion = gameEngine.getBestQuestion();
            this.questionLabel.setText(this.currentQuestion.getText());

        } catch (SQLException e) {
            Functions.showErrorAlert("ERROR", e.getMessage());
        }
    }
}