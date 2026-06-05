package com.ensa.akinator.Utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    public void play() {
        URL musicResource = getClass().getResource("/com/ensa/akinator/audios/soundtrack.mp3");

        if (musicResource != null) {
            Media media = new Media(musicResource.toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.3);

            System.out.println("PLAYING...");
            mediaPlayer.play();
        } else {
            System.out.println("Error: Could not find the background music file.");
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}

    
