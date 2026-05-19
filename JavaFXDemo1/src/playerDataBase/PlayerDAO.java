package playerDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modele.*;

public class PlayerDAO {

	// Informations de connexion (à adapter selon ta BDD)
    private static String url = "jdbc:mysql://localhost:3306/player_db";
    private static String user = "root";
    private static String password = "";

    // Méthode pour INSÉRER un joueur (Create)
    public static void savePlayer(Player player) {
        String query = "INSERT INTO players (id, username, password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, player.getIdPlayer());
            pstmt.setString(2, player.getUserName());
            pstmt.setString(3, player.getPassWord());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour RÉCUPÉRER tous les joueurs (Read)
    public static List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // On crée un objet Player à partir des données de la BDD
                Player p = new Player(rs.getString("name"), rs.getString("username"), rs.getString("password"));
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
    
}
