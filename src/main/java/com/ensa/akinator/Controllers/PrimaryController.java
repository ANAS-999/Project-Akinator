package com.ensa.akinator.Controllers;

import java.io.IOException;

import com.ensa.akinator.App;
import com.ensa.akinator.Utils.Global;
import com.ensa.akinator.Managers.PlayerDAO;
import com.ensa.akinator.Models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

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
                    PlayerDAO.updatePlayerScoreAndHighest(p);
                    
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
                loginButton.setText("🚪");
            }
        } else {
            if (loginButton != null) {
                loginButton.setText("👤");
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

    @FXML
    private void handleInfoAction() {
        try {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            
            // Get owner window
            Window owner = musicButton.getScene().getWindow();
            dialogStage.initOwner(owner);

            // Root container
            VBox root = new VBox();
            root.getStyleClass().add("about-dialog-root");
            root.setSpacing(18);
            root.setAlignment(Pos.TOP_CENTER);
            root.setPadding(new Insets(10, 20, 20, 20));

            // Header layout for close button
            HBox header = new HBox();
            header.setAlignment(Pos.CENTER_RIGHT);
            Button closeBtn = new Button("×");
            closeBtn.getStyleClass().add("dialog-close-btn");
            closeBtn.setOnAction(e -> dialogStage.close());
            header.getChildren().add(closeBtn);

            Label titleLabel = new Label("ℹ  ABOUT AKINATOR");
            titleLabel.getStyleClass().add("about-dialog-title");

            // How to Play Card Panel
            VBox instructionsCard = new VBox();
            instructionsCard.getStyleClass().add("dialog-card");
            instructionsCard.setSpacing(8);
            instructionsCard.setPadding(new Insets(12, 15, 12, 15));
            instructionsCard.setAlignment(Pos.CENTER);

            Label descTitle = new Label("📖  HOW TO PLAY");
            descTitle.getStyleClass().add("about-dialog-subtitle");

            Label descText = new Label(
                "Think of a real or fictional character.\n" +
                "Answer Akinator's questions honestly.\n" +
                "The genie will guess who you're thinking of!"
            );
            descText.getStyleClass().add("about-dialog-text");
            descText.setWrapText(true);
            descText.setAlignment(Pos.CENTER);
            
            instructionsCard.getChildren().addAll(descTitle, descText);

            // Score Rules Card Panel
            VBox rulesCard = new VBox();
            rulesCard.getStyleClass().add("dialog-card-rules");
            rulesCard.setSpacing(6);
            rulesCard.setPadding(new Insets(10, 15, 10, 15));
            rulesCard.setAlignment(Pos.CENTER);

            Label rulesTitle = new Label("🏆  SCORING RULES");
            rulesTitle.getStyleClass().add("about-dialog-subtitle-rules");

            Label rulesText = new Label(
                "• Earn +98 points for every question answered\n" +
                "• Defeat Akinator (when he fails to guess) for a flat 1489 points\n" +
                "• Leaderboard ranks players based on their Highest Score"
            );
            rulesText.getStyleClass().add("about-dialog-text-rules");
            rulesText.setWrapText(true);
            rulesText.setAlignment(Pos.CENTER);

            rulesCard.getChildren().addAll(rulesTitle, rulesText);

            // Tech Stack Card Panel
            VBox techCard = new VBox();
            techCard.getStyleClass().add("dialog-card-tech");
            techCard.setSpacing(6);
            techCard.setPadding(new Insets(10, 15, 10, 15));
            techCard.setAlignment(Pos.CENTER);

            Label techTitle = new Label("🛠️  TECH STACK");
            techTitle.getStyleClass().add("about-dialog-subtitle-tech");

            Label techText = new Label(
                "• Java & JavaFX for GUI layout\n" +
                "• SQLite for characters and local authentication\n" +
                "• Custom AI connection via Web API"
            );
            techText.getStyleClass().add("about-dialog-text-tech");
            techText.setWrapText(true);
            techText.setAlignment(Pos.CENTER);

            techCard.getChildren().addAll(techTitle, techText);

            // Creators Card Panel
            VBox creatorsCard = new VBox();
            creatorsCard.getStyleClass().add("dialog-card-creators");
            creatorsCard.setSpacing(5);
            creatorsCard.setPadding(new Insets(10, 15, 10, 15));
            creatorsCard.setAlignment(Pos.CENTER);

            Label creatorsTitle = new Label("💻  DEVELOPERS");
            creatorsTitle.getStyleClass().add("about-dialog-subtitle-creators");

            Label creatorsText = new Label("ANAS  &  YOUNES");
            creatorsText.getStyleClass().add("about-dialog-creators");

            creatorsCard.getChildren().addAll(creatorsTitle, creatorsText);

            root.getChildren().addAll(header, titleLabel, instructionsCard, rulesCard, techCard, creatorsCard);

            Scene scene = new Scene(root, 420, 580);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(App.class.getResource("styles/primary.css").toExternalForm());

            dialogStage.setScene(scene);
            
            // Center the dialog relative to the owner window
            dialogStage.setOnShown(e -> {
                dialogStage.setX(owner.getX() + (owner.getWidth() - dialogStage.getWidth()) / 2);
                dialogStage.setY(owner.getY() + (owner.getHeight() - dialogStage.getHeight()) / 2);
            });

            // Get root pane of the primary scene and add dim overlay
            StackPane rootPane = (StackPane) musicButton.getScene().getRoot();
            Region dimOverlay = new Region();
            dimOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.65);");
            dimOverlay.setPrefSize(rootPane.getWidth(), rootPane.getHeight());

            rootPane.getChildren().add(dimOverlay);
            dialogStage.showAndWait();
            rootPane.getChildren().remove(dimOverlay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
