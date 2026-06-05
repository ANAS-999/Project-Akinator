package com.ensa.akinator.controllers;

import java.io.IOException;

import com.ensa.akinator.App;
import com.ensa.akinator.Utils.Global;
import com.ensa.akinator.Managers.PlayerDAO;
import com.ensa.akinator.Models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrimaryController {

    private boolean isMusicOn;

    @FXML
    private Button musicButton;

    @FXML
    private Button playButton;

    @FXML
    private Button playButton1;

    @FXML
    private Label loginWarningLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button historyButton;

    @FXML
    private Label scoreLabel;

    @FXML
    public void initialize() {
        isMusicOn = Global.audioPlayer.getMediaPlayer().isMute();
        handleMusicToggle();

        // Rafraîchir les statistiques depuis la base de données si connecté
        if (Global.loggedInPlayer != null) {
            for (Player p : PlayerDAO.getAllPlayers()) {
                if (p.getUserName().equalsIgnoreCase(Global.loggedInPlayer.getUserName())) {
                    // En arrivant sur l'accueil, on force la réinitialisation du score à 0
                    p.setScore(0);
                    PlayerDAO.updatePlayerScoreAndHighest(p.getUserName(), 0, p.getHighestScore());
                    
                    Global.loggedInPlayer.setScore(0);
                    Global.loggedInPlayer.setHighestScore(p.getHighestScore());
                    Global.loggedInPlayer.setGamesNb(p.getGamesNb());
                    break;
                }
            }
        }

        // Initialisation de l'affichage en fonction de la connexion
        if (Global.loggedInPlayer != null) {
            if (loginButton != null) {
                loginButton.setText("👋  Logout");
            }
        } else {
            if (loginButton != null) {
                loginButton.setText("🔑  Login");
            }
        }

        updateScoreLabel();
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
    private void handlePlayAction() throws IOException {
        if (Global.loggedInPlayer == null) {
            showLoginWarning();
        } else {
            App.setRoot("game");
        }
    }

    @FXML
    private void handlePlayAIAction() throws IOException {
        if (Global.loggedInPlayer == null) {
            showLoginWarning();
        } else {
            App.setRoot("aigame");
        }
    }

    @FXML
    private void handleHistoryAction() throws IOException {
        App.setRoot("history");
    }

    private void showLoginWarning() {
        if (loginWarningLabel != null) {
            loginWarningLabel.setText("Please login to play!");
            loginWarningLabel.setVisible(true);
            loginWarningLabel.setManaged(true);
        }
    }

    @FXML
    private void handleLoginAction() throws IOException {
        if (Global.loggedInPlayer != null) {
            // Déconnexion
            Global.loggedInPlayer = null;
            App.setRoot("primary");
        } else {
            // Navigation vers l'écran de connexion
            App.setRoot("LoginFXML");
        }
    }

    @FXML
    private void handleMusicToggle() {
        isMusicOn = !isMusicOn;
        if (isMusicOn) {
            musicButton.setText("🔊");
            musicButton.getStyleClass().remove("music-disabled");
            Global.audioPlayer.getMediaPlayer().setMute(false);
        } else {
            musicButton.setText("🔇");
            musicButton.getStyleClass().add("music-disabled");
            Global.audioPlayer.getMediaPlayer().setMute(true);
        }
    }
}
