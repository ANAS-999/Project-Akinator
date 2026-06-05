package com.ensa.akinator.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import com.ensa.akinator.App;
import com.ensa.akinator.GameEngines.GameEngine;
import com.ensa.akinator.Models.AnswerEnum;
import com.ensa.akinator.Models.Question;
import com.ensa.akinator.Models.Character;
import com.ensa.akinator.Utils.Functions;
import com.ensa.akinator.Utils.Global;
import com.ensa.akinator.Managers.PlayerDAO;

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

    @FXML
    private Label scoreLabel;

    private int currentStep = 1;

    @FXML
    public void initialize() throws IOException {
        try {
            if (Global.loggedInPlayer != null) {
                Global.loggedInPlayer.setScore(0);
                PlayerDAO.updatePlayerScoreAndHighest(
                    Global.loggedInPlayer.getUserName(), 
                    0, 
                    Global.loggedInPlayer.getHighestScore()
                );
            }
            this.gameEngine = new GameEngine("Anas");
            this.currentQuestion = gameEngine.getBestQuestion();
            this.questionLabel.setText(this.currentQuestion.getText());
            updateStepLabel();
            updateScoreLabel();

        } catch (SQLException e) {
            Functions.showErrorAlert("ERROR", e.getMessage());
            returnToPrimary();
        }
    }

    private void updateStepLabel() {
        if (stepLabel != null) {
            stepLabel.setText(String.valueOf(currentStep));
        }
    }

    private void updateScoreLabel() {
        if (scoreLabel != null) {
            if (Global.loggedInPlayer != null) {
                scoreLabel.setText("👤 " + Global.loggedInPlayer.getUserName() + 
                                   "  |  🎮 Games: " + Global.loggedInPlayer.getGamesNb() + 
                                   "  |  ⭐ Best: " + Global.loggedInPlayer.getHighestScore() + 
                                   "  |  ⚡ Score: " + Global.loggedInPlayer.getScore());
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

    // ! Methods
    private void returnToPrimary() {
        Platform.runLater(() -> {
            try {
                App.setRoot("primary");
            } catch (IOException ignored) {
            }
        });
    }

    public void updateAkinatorImage(String mode) {
        String imagePath = "/com/ensa/akinator/assets/akinator-" + mode + ".png";
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        genieImage.setImage(newImage);
    }

    private void play(AnswerEnum answerEnum) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.25));

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
        // Augmenter le score de 98 après chaque réponse
        if (Global.loggedInPlayer != null) {
            Global.loggedInPlayer.incrementScore(98);
            PlayerDAO.updatePlayerScoreAndHighest(
                Global.loggedInPlayer.getUserName(), 
                Global.loggedInPlayer.getScore(),
                Global.loggedInPlayer.getHighestScore()
            );
            updateScoreLabel();
        }

        int answer = answerEnum.getValue() == 1 ? 1 : 0;
        gameEngine.filterCharacters(currentQuestion.getId(), answer);

        if (gameEngine.checkWinCondition()) {
            handleCharacterFound();
            return;
        }

        if (gameEngine.getPossibleCandidateIds().isEmpty()) {
            handleCharacterNotFound();
            return;
        }

        currentStep++;
        updateStepLabel();
        this.currentQuestion = gameEngine.getBestQuestion();

        if (this.currentQuestion == null) {
            handleCharacterNotFound();
            return;
        }

        this.questionLabel.setText(this.currentQuestion.getText());
    }

    private void handleCharacterFound() throws SQLException, IOException {
        Character character = gameEngine.getFoundedCharacter();
        Global.characterFounded = character;

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

        App.setRoot("character");
    }

    private void handleCharacterNotFound() throws IOException {
        // Fin de partie perdue : sauvegarde et incrémentation des statistiques
        if (Global.loggedInPlayer != null) {
            // Toujours afficher et enregistrer le score à 1489 si le caractère n'est pas trouvé
            Global.loggedInPlayer.setScore(1489);
            
            Global.lastMatchScore = 1489;
            
            if (Global.loggedInPlayer.getScore() > Global.loggedInPlayer.getHighestScore()) {
                Global.loggedInPlayer.setHighestScore(Global.loggedInPlayer.getScore());
            }
            Global.loggedInPlayer.setGamesNb(Global.loggedInPlayer.getGamesNb() + 1);
            Global.loggedInPlayer.setScore(0); // Réinitialiser le score
            
            PlayerDAO.updatePlayerStats(
                Global.loggedInPlayer.getUserName(),
                Global.loggedInPlayer.getScore(),
                Global.loggedInPlayer.getHighestScore(),
                Global.loggedInPlayer.getGamesNb()
            );
        }

        App.setRoot("notfound");
    }
}