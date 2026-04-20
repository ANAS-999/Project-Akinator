package Entities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:akinator.db";
    private Connection connection;
    private ArrayList<Question> listQuestions;

    public ArrayList<Question> getListQuestions() {
        return listQuestions;
    }

    public DatabaseManager() {
        connect();
        this.listQuestions = loadAllQuestions();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("> Database connected successfuly!");
        } catch (SQLException e) {
            System.out.println("> Database connection failed: " + e.getMessage());
        }
    }

    public ArrayList<Integer> getCharacterIdsByAnswer(int questionId, int expectedAnswer) {
        ArrayList<Integer> matchingIds = new ArrayList<>();

        // The SQL query we discussed!
        String sql = "SELECT character_id FROM character_answers WHERE question_id = ? AND expected_answer = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Fill in the ? marks securely
            pstmt.setInt(1, questionId);
            pstmt.setInt(2, expectedAnswer);

            ResultSet rs = pstmt.executeQuery();

            // Loop through the results and add the IDs to our list
            while (rs.next()) {
                matchingIds.add(rs.getInt("character_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error filtering characters: " + e.getMessage());
        }

        return matchingIds;
    }

    public ArrayList<Question> getQuestionsWithParentId(int parentQuestionId) {
        ArrayList<Question> questions = new ArrayList<>();

        String sql = "SELECT id, question_text, parent_question_id FROM questions WHERE parent_question_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, parentQuestionId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("question_text");
                Integer parentId = (Integer) rs.getObject("parent_question_id");

                questions.add(new Question(id, text, parentId));
            }
        } catch (SQLException e) {
            System.out.println("Error Get Questions With Parent Id: " + e.getMessage());
        }

        return questions;
    }

    public Character getCharacterById(int characterId) {
        String sql = "SELECT id, name, category_id, image_path FROM characters WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, characterId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int categoryId = rs.getInt("category_id");
                String imagePath = rs.getString("image_path");

                System.out.println(id + "-" + name + "-" + categoryId + "-" + imagePath);
                // return new Character(id, name, categoryId, imagePath);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching character: " + e.getMessage());
        }
        return null;
    }

    private ArrayList<Question> loadAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        String sql = "SELECT id, question_text, parent_question_id FROM questions";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("question_text");
                // Using getObject to handle NULL parent IDs gracefully
                Integer parentId = (Integer) rs.getObject("parent_question_id");

                questions.add(new Question(id, text, parentId));
            }
        } catch (SQLException e) {
            System.out.println("Error loading questions: " + e.getMessage());
        }
        return questions;
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}