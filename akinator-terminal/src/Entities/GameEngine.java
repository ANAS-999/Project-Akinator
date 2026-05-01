package Entities;

import java.sql.SQLException;
import java.util.ArrayList;

import Utils.Functions;

public class GameEngine {
    public DatabaseManager db;
    private String currentPlayer;
    private ArrayList<Integer> answers;
    private ArrayList<Integer> possibleCharactersIds;
    private ArrayList<Question> askedQuestions;

    private Integer parentQuestionId;
    private int currentQuestionCount = 0;

    public GameEngine(String currentPlayer) throws SQLException {
        this.db = new DatabaseManager();
        this.currentPlayer = currentPlayer;
        this.answers = new ArrayList<>();
        this.askedQuestions = new ArrayList<>();
        this.possibleCharactersIds = new ArrayList<>();
    }

    // ! Methods
    public Character getFoundedCharacter() throws SQLException {
        if (this.possibleCharactersIds.size() == 1) {
            return this.db.getCharacterById(possibleCharactersIds.get(0));
        }

        return null;
    }

    public Question getBestQuestion() throws SQLException {
        Question question;

        if (currentQuestionCount == 0) {
            question = this.db.getListQuestions().get(0);

        } else {
            ArrayList<Question> availableQuestions = getAvailableQuestions();
            ArrayList<FilteredQuestion> filteredQuestions = getFilteredQuestions(availableQuestions);

            Functions.printListQuestionsIds(availableQuestions);

            question = getMinCharactersCountQuestion(filteredQuestions);
        }

        this.currentQuestionCount += 1;
        this.askedQuestions.add(question);

        return question;
    }

    public void filterCharacters(int questionId, int answer) throws SQLException {
        ArrayList<Integer> newPossibleCharactersIds = this.db.getCharacterIdsByAnswer(questionId, answer);

        if (this.possibleCharactersIds.size() == 0)
            this.possibleCharactersIds = newPossibleCharactersIds;
        else
            this.possibleCharactersIds = Functions.intersection(this.possibleCharactersIds, newPossibleCharactersIds);

        this.answers.add(answer);
    }

    public boolean checkWinCondition() {
        if (this.possibleCharactersIds.size() == 1)
            return true;

        return false;
    }

    // ! Utils Methods
    private ArrayList<Question> getAvailableQuestions() throws SQLException {
        Integer lastAnswer = this.answers.get(currentQuestionCount - 1);
        Question lastAskedQuestion = this.askedQuestions.get(currentQuestionCount - 1);
        ArrayList<Question> childrenQuestions = this.db.getQuestionsWithParentId(lastAskedQuestion.getId());

        if (childrenQuestions.size() != 0 && lastAnswer == 1) {
            this.parentQuestionId = lastAskedQuestion.getId();
            return childrenQuestions;
        }

        ArrayList<Question> questions = this.db.getQuestionsWithParentId(parentQuestionId);
        return getNotAskedQuestions(questions);
    }

    private ArrayList<Question> getNotAskedQuestions(ArrayList<Question> questions) {
        ArrayList<Question> notAskedQuestions = new ArrayList<>();

        for (Question question : questions) {
            if (!this.askedQuestions.contains(question))
                notAskedQuestions.add(question);
        }

        return notAskedQuestions;
    }

    private ArrayList<FilteredQuestion> getFilteredQuestions(ArrayList<Question> availableQuestions)
            throws SQLException {
        /*
         * This method eliminate questions which has not a possible characters
         * by checking the old answers
         * return list of question + characters count
         */

        ArrayList<FilteredQuestion> filteredQuestions = new ArrayList<>();

        for (Question question : availableQuestions) {
            ArrayList<Integer> yesPossibleCharacters = this.db.getCharacterIdsByAnswer(question.getId(), 1);
            int intersectionCount = Functions.intersection(possibleCharactersIds, yesPossibleCharacters).size();

            if (intersectionCount != 0)
                filteredQuestions.add(new FilteredQuestion(question, intersectionCount));
        }

        return filteredQuestions;
    }

    private Question getMinCharactersCountQuestion(ArrayList<FilteredQuestion> filteredQuestions) {
        if (filteredQuestions.size() == 0)
            return null;

        FilteredQuestion filteredQuestion = filteredQuestions.get(0);

        for (FilteredQuestion fq : filteredQuestions) {
            if (fq.getCharactersCount() < filteredQuestion.getCharactersCount())
                filteredQuestion = fq;
        }

        return filteredQuestion.getQuestion();
    }

    // ! Getters & Setters
    public ArrayList<Integer> getPossibleCandidateIds() {
        return this.possibleCharactersIds;
    }

    public ArrayList<Question> getAskedQuestions() {
        return this.askedQuestions;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "GameEngine [possibleCharactersIds=" + possibleCharactersIds.size() + ", askedQuestions="
                + askedQuestions.size()
                + ", currentPlayer=" + currentPlayer + "]";
    }

}
