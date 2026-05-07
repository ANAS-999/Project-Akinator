package com.ensa.akinator.controllers;

import java.io.IOException;

import com.ensa.akinator.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void handlePlayAction() throws IOException {
        App.setRoot("game");
    }
}
