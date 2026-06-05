package com.ensa.akinator.controllers;

import java.io.IOException;

import com.ensa.akinator.App;

import javafx.fxml.FXML;

public class NotFoundController {

    @FXML
    private void onPlayAgainClicked() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void onHomeClicked() throws IOException {
        App.setRoot("primary");
    }
}
