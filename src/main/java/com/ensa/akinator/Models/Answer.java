package com.ensa.akinator.Models;

public class Answer {
    private int answer;
    private String question;

    public Answer(int answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getStringAnswer() {

        switch (answer) {
            case 1:
                return "Yes";
            case 2:
                return "Probably yes";
            case 3:
                return "Probably no";
            case 4:
                return "I don't know";
            default:
                return "No";
        }

    }

    @Override
    public String toString() {
        return "Answer [answer=" + answer + ", question=" + question + "]";
    }
}
