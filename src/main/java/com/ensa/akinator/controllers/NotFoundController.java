package com.ensa.akinator.controllers;

import java.io.IOException;

import com.ensa.akinator.App;
import com.ensa.akinator.Utils.Global;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NotFoundController {

    @FXML
    private Label scoreLabel;

    @FXML
    private Label matchScoreLabel;

    @FXML
    public void initialize() {
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

    @FXML
    private void onPlayAgainClicked() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void onHomeClicked() throws IOException {
        App.setRoot("primary");
    }
}
