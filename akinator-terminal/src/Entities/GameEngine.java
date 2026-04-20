package Entities;

import java.util.ArrayList;

public class GameEngine {
    public DatabaseManager db;
    private String currentPlayer;
    private ArrayList<Integer> possibleCandidateIds;
    private ArrayList<Question> askedQuestions;

    private int currentQuestionCount = 0;

    public GameEngine(String currentPlayer) {
        this.db = new DatabaseManager();
        this.currentPlayer = currentPlayer;
        this.askedQuestions = new ArrayList<>();
        this.possibleCandidateIds = new ArrayList<>();
    }

    // ! Methods
    public Question getBestQuestion() {
        if (currentQuestionCount == 0)
            return getFirstQuestion();

        ArrayList<Question> availableQuestions = getAvailableQuestions();
        System.out.println(availableQuestions.size());

        return null;
    }

    public void filterCharacters(int questionId, int answer) {
        this.possibleCandidateIds = this.db.getCharacterIdsByAnswer(questionId, answer);
    }

    public boolean checkWinCondition() {
        if (this.possibleCandidateIds.size() == 1)
            return true;

        return false;
    }

    // ! Utils Methods
    private ArrayList<Question> getAvailableQuestions() {
        Question lastAskedQuestion = this.askedQuestions.get(currentQuestionCount - 1);
        Integer parentId = lastAskedQuestion.getId();

        if (lastAskedQuestion.getParentQuestionId() != null)
            parentId = lastAskedQuestion.getParentQuestionId();

        ArrayList<Question> childrenQuestionsIds = this.db.getQuestionsWithParentId(parentId);

        if (childrenQuestionsIds.size() != 0)
            return childrenQuestionsIds;

        return getNotAskedQuestions();
    }

    private ArrayList<Question> getNotAskedQuestions() {
        ArrayList<Question> notAskedQuestions = new ArrayList<>();

        for (Question question : this.db.getListQuestions()) {
            if (!this.askedQuestions.contains(question))
                notAskedQuestions.add(question);
        }

        return notAskedQuestions;
    }

    private Question getFirstQuestion() {
        ArrayList<Question> allQuestions = this.db.getListQuestions();

        Question question = allQuestions.get(0);
        this.askedQuestions.add(question);
        this.currentQuestionCount += 1;

        return question;
    }

    // ! Getters & Setters
    public ArrayList<Integer> getPossibleCandidateIds() {
        return this.possibleCandidateIds;
    }

    public ArrayList<Question> getAskedQuestions() {
        return this.askedQuestions;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPossibleCandidateIds(ArrayList<Integer> possibleCandidateIds) {
        this.possibleCandidateIds = possibleCandidateIds;
    }

    @Override
    public String toString() {
        return "GameEngine [possibleCandidateIds=" + possibleCandidateIds.size() + ", askedQuestions="
                + askedQuestions.size()
                + ", currentPlayer=" + currentPlayer + "]";
    }

}
