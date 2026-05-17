package playerDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Player; // Import de ta classe Player

public class PlayerDAO {

	// Informations de connexion (à adapter selon ta BDD)
    private String url = "jdbc:mysql://localhost:3306/player_db";
    private String user = "root";
    private String password = "";

    // Méthode pour INSÉRER un joueur (Create)
    public void savePlayer(Player player) {
        String query = "INSERT INTO players (name, score) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getScore());
            pstmt.executeUpdate();
            
            System.out.println("Joueur enregistré avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour RÉCUPÉRER tous les joueurs (Read)
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // On crée un objet Player à partir des données de la BDD
                Player p = new Player(rs.getString("name"), rs.getInt("score"));
                players.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}
