package com.ensa.akinator.Controllers;

import com.ensa.akinator.App;
import com.ensa.akinator.Managers.PlayerDAO;
import com.ensa.akinator.Models.Player;
import com.ensa.akinator.Utils.Global;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HistoryController {

    @FXML
    private VBox leaderboardContainer;

    @FXML
    private Label scoreLabel;

    @FXML
    public void initialize() {
        updateScoreLabel();
        loadLeaderboard();
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

    private void loadLeaderboard() {
        if (leaderboardContainer == null) return;
        
        leaderboardContainer.getChildren().clear();

        List<Player> players = PlayerDAO.getAllPlayers();
        if (players == null || players.isEmpty()) {
            Label noDataLabel = new Label("No records found.");
            noDataLabel.setStyle("-fx-text-fill: #a0a0a0; -fx-font-size: 16px; -fx-font-style: italic;");
            leaderboardContainer.getChildren().add(noDataLabel);
            return;
        }

        // Trier les joueurs par highestScore décroissant
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getHighestScore(), p1.getHighestScore());
            }
        });

        int rank = 1;
        for (Player p : players) {
            HBox row = new HBox();
            row.getStyleClass().add("leaderboard-row");
            if (Global.loggedInPlayer != null && p.getUserName().equalsIgnoreCase(Global.loggedInPlayer.getUserName())) {
                row.getStyleClass().add("logged-in-user-row");
            }

            // Label de Rang
            Label rankLbl = new Label(String.valueOf(rank));
            rankLbl.getStyleClass().add("rank-label");
            if (rank == 1) {
                rankLbl.getStyleClass().add("rank-1");
                rankLbl.setText("🥇");
            } else if (rank == 2) {
                rankLbl.getStyleClass().add("rank-2");
                rankLbl.setText("🥈");
            } else if (rank == 3) {
                rankLbl.getStyleClass().add("rank-3");
                rankLbl.setText("🥉");
            }
            
            // Nom d'utilisateur
            Label userLbl = new Label(p.getUserName());
            userLbl.getStyleClass().add("username-label");

            // Spacer
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Boîte détails de scores
            VBox scoreBox = new VBox();
            scoreBox.getStyleClass().add("score-details-box");
            scoreBox.setSpacing(2);

            Label gamesPlayed = new Label("Games: " + p.getGamesNb());
            gamesPlayed.getStyleClass().add("score-text");

            Label highS = new Label("Best: " + p.getHighestScore());
            highS.getStyleClass().add("highest-score-text");

            scoreBox.getChildren().addAll(highS, gamesPlayed);

            row.getChildren().addAll(rankLbl, userLbl, spacer, scoreBox);
            leaderboardContainer.getChildren().add(row);

            rank++;
        }
    }

    @FXML
    private void onBackClicked() throws IOException {
        App.setRoot("primary");
    }
}
