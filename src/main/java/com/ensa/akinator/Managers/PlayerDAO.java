package com.ensa.akinator.Managers;

import com.ensa.akinator.Models.Player;
import com.ensa.akinator.Utils.Global;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    // Informations de connexion (SQLite local)
    private static String url = "jdbc:sqlite:users.db";

    // Initialiser la base de données SQLite et s'assurer que la table existe
    static {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS players (" +
                               "id TEXT, " +
                               "name TEXT, " +
                               "username TEXT PRIMARY KEY, " +
                               "password TEXT, " +
                               "score INTEGER DEFAULT 0, " +
                               "highest_score INTEGER DEFAULT 0, " +
                               "games_nb INTEGER DEFAULT 0" +
                               ")");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'initialisation de la table players : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void handleCharacterNotFound() {
        if (Global.loggedInPlayer != null) {
            Global.loggedInPlayer.incrementScore(1489);
            Global.lastMatchScore =  Global.loggedInPlayer.getScore();

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
    }

    // Mettre à jour le score et le score maximum du joueur
    public static void updatePlayerScoreAndHighest(Player player) {
        String username= player.getUserName();
        int newScore = player.getScore();
        int newHighest = player.getHighestScore();

        String query = "UPDATE players SET score = ?, highest_score = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, newScore);
            pstmt.setInt(2, newHighest);
            pstmt.setString(3, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour du score et highest_score : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Mettre à jour toutes les statistiques du joueur à la fin d'un match
    public static void updatePlayerStats(String username, int score, int highestScore, int gamesNb) {
        String query = "UPDATE players SET score = ?, highest_score = ?, games_nb = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, score);
            pstmt.setInt(2, highestScore);
            pstmt.setInt(3, gamesNb);
            pstmt.setString(4, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour des stats du joueur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour INSÉRER un joueur (Create)
    public static void savePlayer(Player player) {
        String query = "INSERT INTO players (id, username, password, score, highest_score, games_nb) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, player.getIdPlayer());
            pstmt.setString(2, player.getUserName());
            pstmt.setString(3, player.getPassWord());
            pstmt.setInt(4, player.getScore());
            pstmt.setInt(5, player.getHighestScore());
            pstmt.setInt(6, player.getGamesNb());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour RÉCUPÉRER tous les joueurs (Read)
    public static List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // On crée un objet Player à partir des données de la BDD
                Player p = new Player(rs.getString("name"), rs.getString("username"), rs.getString("password"));
                try {
                    p.setScore(rs.getInt("score"));
                } catch (SQLException sqle) {
                    p.setScore(0);
                }
                try {
                    p.setHighestScore(rs.getInt("highest_score"));
                } catch (SQLException sqle) {
                    p.setHighestScore(0);
                }
                try {
                    p.setGamesNb(rs.getInt("games_nb"));
                } catch (SQLException sqle) {
                    p.setGamesNb(0);
                }
                players.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public static boolean containUserName(String pseudoRecherche) {
        if (PlayerDAO.getAllPlayers() == null) { return false; }

        for (Player p : PlayerDAO.getAllPlayers()) {
            if (p.getUserName().equals(pseudoRecherche)) {
                return true; // Le joueur existe !
            }
        }
        return false; // On a parcouru toute la liste sans le trouver
    }

    public static boolean isLoginValid(String username, String providedPassword) {
        String query = "SELECT password FROM players WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(providedPassword);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du login : " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Méthode pour INSCRIRE un nouveau joueur
    public static boolean registerPlayer(String nom, String username, String userPassword) {
        if (containUserName(username)) {
            return false;
        }

        String query = "INSERT INTO players (name, username, password, score, highest_score, games_nb) VALUES (?, ?, ?, 0, 0, 0)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, username);
            pstmt.setString(3, userPassword);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'enregistrement en BDD : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
