package com.ensa.akinator.controllers;

import java.io.IOException;

import com.ensa.akinator.App;

import com.ensa.akinator.Utils.Global;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    private boolean isMusicOn;

    @FXML
    private Button musicButton;

    @FXML
    public void initialize() {
        isMusicOn = Global.audioPlayer.getMediaPlayer().isMute();
        handleMusicToggle();
    }

    @FXML
    private void handlePlayAction() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void handlePlayAIAction() throws IOException {
        App.setRoot("aigame");
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
