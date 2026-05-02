package Managers;

import java.sql.*;
import java.util.ArrayList;

import Models.Question;
import Models.Character;

public class DatabaseManager {

    private Connection connection;
    private ArrayList<Question> listQuestions;
    private static final String DB_URL = "jdbc:sqlite:akinator_no_dobl.db";

    public DatabaseManager() throws SQLException {
        connect();
        this.listQuestions = loadAllQuestions();
    }

    public ArrayList<Question> getListQuestions() {
        return listQuestions;
    }

    // ! Connection Methods
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
    }

    public void close() throws SQLException {
        if (connection != null)
            connection.close();
    }

    // ! SQL Methods
    public ArrayList<Integer> getCharacterIdsByAnswer(int questionId, int expectedAnswer) throws SQLException {
        ArrayList<Integer> matchingIds = new ArrayList<>();

        String sql = "SELECT character_id FROM character_answers WHERE question_id = ? AND expected_answer = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, questionId);
        pstmt.setInt(2, expectedAnswer);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            matchingIds.add(rs.getInt("character_id"));
        }

        return matchingIds;
    }

    public ArrayList<Question> getQuestionsWithParentId(Integer parentQuestionId) throws SQLException {
        ResultSet rs;
        ArrayList<Question> questions = new ArrayList<>();

        if (parentQuestionId == null) {
            String sql = "SELECT id, question_text, parent_question_id FROM questions WHERE parent_question_id IS NULL";
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(sql);
        } else {
            String sql = "SELECT id, question_text, parent_question_id FROM questions WHERE parent_question_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, parentQuestionId);
            rs = pstmt.executeQuery();
        }

        while (rs.next()) {
            int id = rs.getInt("id");
            String text = rs.getString("question_text");
            Integer parentId = (Integer) rs.getObject("parent_question_id");

            questions.add(new Question(id, text, parentId));
        }

        return questions;
    }

    public Character getCharacterById(int characterId) throws SQLException {
        String sql = "SELECT id, name, category_id, image_path FROM characters WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setInt(1, characterId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int categoryId = rs.getInt("category_id");
            String imagePath = rs.getString("image_path");

            return new Character(id, name, categoryId, imagePath);
        }

        return null;
    }

    // ! Utils Methods
    private ArrayList<Question> loadAllQuestions() throws SQLException {
        ArrayList<Question> questions = new ArrayList<>();
        String sql = "SELECT id, question_text, parent_question_id FROM questions";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String text = rs.getString("question_text");
            Integer parentId = (Integer) rs.getObject("parent_question_id");

            questions.add(new Question(id, text, parentId));
        }

        return questions;
    }

}