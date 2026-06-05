package akinatorDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modele.Category;
import modele.Character;
import modele.Question;

public class AkinatorDAO {
    private static final String DB_NAME = "akinator (1).db";

    /**
     * Obtains a connection to the SQLite database.
     * Checks if the DB is in the current directory, and falls back to JavaFXDemo1/ directory if needed.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found. Make sure sqlite-jdbc jar is on the classpath.");
        }

        java.io.File dbFile = new java.io.File(DB_NAME);
        String finalPath = DB_NAME;
        if (!dbFile.exists()) {
            java.io.File fallbackFile = new java.io.File("JavaFXDemo1/" + DB_NAME);
            if (fallbackFile.exists()) {
                finalPath = "JavaFXDemo1/" + DB_NAME;
            }
        }

        return DriverManager.getConnection("jdbc:sqlite:" + finalPath);
    }

    /**
     * Récupère toutes les catégories de la base de données.
     */
    public static List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name FROM categories";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des catégories : " + e.getMessage());
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Récupère toutes les questions de la base de données.
     */
    public static List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT id, question_text, parent_question_id FROM questions";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("question_text");
                int parentId = rs.getInt("parent_question_id");

                questions.add(new Question(String.valueOf(id), text, parentId));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des questions : " + e.getMessage());
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * Récupère une question par son identifiant.
     */
    public static Question getQuestionById(int id) {
        String query = "SELECT id, question_text, parent_question_id FROM questions WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String text = rs.getString("question_text");
                    int parentId = rs.getInt("parent_question_id");
                    return new Question(String.valueOf(id), text, parentId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la question : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère tous les personnages de la base de données, ainsi que leurs réponses associées.
     */
    public static List<Character> getAllCharacters() {
        List<Character> characters = new ArrayList<>();

        // 1. Charger toutes les réponses en une seule requête pour de meilleures performances
        Map<Integer, Map<Integer, Integer>> answersMap = getCharacterAnswersMap();

        // 2. Charger les personnages
        String query = "SELECT id, name, category_id, image_path FROM characters";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int categoryId = rs.getInt("category_id");
                String imagePath = rs.getString("image_path");

                Map<Integer, Integer> answers = answersMap.getOrDefault(id, new HashMap<>());

                Character c = new Character(String.valueOf(id), name, imagePath, categoryId, answers);
                characters.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des personnages : " + e.getMessage());
            e.printStackTrace();
        }

        return characters;
    }

    /**
     * Récupère un personnage spécifique avec ses réponses chargées.
     */
    public static Character getCharacterById(int id) {
        String query = "SELECT id, name, category_id, image_path FROM characters WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int categoryId = rs.getInt("category_id");
                    String imagePath = rs.getString("image_path");

                    Map<Integer, Integer> answers = getAnswersForCharacter(id);
                    return new Character(String.valueOf(id), name, imagePath, categoryId, answers);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du personnage : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère les réponses associées à un personnage donné.
     */
    public static Map<Integer, Integer> getAnswersForCharacter(int characterId) {
        Map<Integer, Integer> answers = new HashMap<>();
        String query = "SELECT question_id, expected_answer FROM character_answers WHERE character_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, characterId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    answers.put(rs.getInt("question_id"), rs.getInt("expected_answer"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des réponses du personnage : " + e.getMessage());
            e.printStackTrace();
        }
        return answers;
    }

    /**
     * Charge toutes les réponses de tous les personnages (méthode utilitaire interne).
     */
    private static Map<Integer, Map<Integer, Integer>> getCharacterAnswersMap() {
        Map<Integer, Map<Integer, Integer>> answersMap = new HashMap<>();
        String query = "SELECT character_id, question_id, expected_answer FROM character_answers";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int charId = rs.getInt("character_id");
                int questionId = rs.getInt("question_id");
                int expectedAnswer = rs.getInt("expected_answer");

                answersMap.computeIfAbsent(charId, k -> new HashMap<>()).put(questionId, expectedAnswer);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement groupé des réponses : " + e.getMessage());
            e.printStackTrace();
        }
        return answersMap;
    }
}
