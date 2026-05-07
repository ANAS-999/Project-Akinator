package Entities;

public class FilteredQuestion {
    private Question question;
    private int charactersCount;

    public FilteredQuestion(Question question, int charactersCount) {
        this.question = question;
        this.charactersCount = charactersCount;
    }

    public Question getQuestion() {
        return question;
    }

    public int getCharactersCount() {
        return charactersCount;
    }

    @Override
    public String toString() {
        return "FilteredQuestion [charactersCount=" + charactersCount + ", question=" + question.getText() + "]";
    }

}
