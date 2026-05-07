package com.ensa.akinator.GameEngines;

import java.util.ArrayList;

import com.ensa.akinator.Exceptions.AIConnectionFailedException;
import com.ensa.akinator.Managers.AIManager;
import com.ensa.akinator.Models.Answer;

public class AIGameEngine {
    private String lastQuestion;
    private ArrayList<Answer> answers;

    public AIGameEngine() {
        this.answers = new ArrayList<>();
    }

    // ! Methods
    public String initGame() throws AIConnectionFailedException {
        String response = AIManager.sendApiRequest(getInitPrompt());
        this.lastQuestion = response;

        return response;
    }

    public String getNextQuestion(int answer) throws AIConnectionFailedException {
        answers.add(new Answer(answer, lastQuestion));

        String history = getHistory();
        String prompt = getInitPrompt() + history + "What is your next question?";
        String response = AIManager.sendApiRequest(prompt);

        this.lastQuestion = response;

        return response;
    }

    // ! Utils Methods
    private String getInitPrompt() {
        String prompt = "You are Akinator. I am thinking of a famous character (real or fictional). " +
                "Follow these exact rules: " +
                "1. Ask exactly ONE Yes/No question per turn to narrow it down. " +
                "2. Respond ONLY with the question itself. Absolutely NO conversational filler, greetings, or explanations. "
                +
                "3. Use simple, easily understood language. " +
                "4. Do NOT ask about sports on the very first question. Start with broad questions, but prioritize figuring out if they are a football (soccer) player within the first 3 questions. " +
                "5. Once you are 95% confident, make your final guess using EXACTLY this format: 'I think it is [Name]'";

        return prompt;
    }

    @SuppressWarnings("unused")
    private String getInit2Prompt() {
        String prompt = "You are Akinator. I am thinking of a famous character (real or fictional). " +
                "Follow these exact rules: " +
                "1. Ask exactly ONE Yes/No question per turn to narrow it down. " +
                "2. Respond ONLY with the question itself. Absolutely NO conversational filler, greetings, or explanations. "
                +
                "3. Use simple, easily understood language. " +
                "5. Once you are 95% confident, make your final guess using EXACTLY this format: 'I think it is [Name]'"
                +
                "MOST IMPORTANT : Check if this is a football (soccer) player in third question!!!!!. ";

        return prompt;
    }

    private String getHistory() {
        String history = "\n";

        for (Answer answer : answers) {
            history += "You asked: '" + answer.getQuestion() + "' The user answered: '" + answer.getStringAnswer()
                    + "',\n";
        }

        return history;
    }
}
