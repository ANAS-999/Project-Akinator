import Entities.GameEngine;
import Entities.Question;
import Entities.Character;

import java.util.Scanner;

public class App {
    static void printLine() {
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Akinator");

        Scanner scanner = new Scanner(System.in);
        GameEngine gameEngine = new GameEngine("Anas");

        /*
         * printLine();
         * for (Question question : gameEngine.db.loadAllQuestions())
         * System.out.println(question);
         * printLine();
         */

        /*
         * boolean isWin = false;
         * while (!isWin) {
         * Question question = gameEngine.getBestQuestion();
         * 
         * System.out.print("\n" + question + "\nYes = 1, No = 0 : ");
         * int answer = scanner.nextInt();
         * 
         * gameEngine.filterCharacters(question.getId(), answer);
         * isWin = gameEngine.checkWinCondition();
         * 
         * System.out.println(gameEngine.getPossibleCandidateIds());
         * }
         */

        while (!gameEngine.checkWinCondition()) {
            Question question = gameEngine.getBestQuestion();

            if (question == null)
                break;

            System.out.println("\n" + question);
            int answer = scanner.nextInt();

            gameEngine.filterCharacters(question.getId(), answer);
            System.out.println("Possible (" + gameEngine.getPossibleCandidateIds().size() + ") : "
                    + gameEngine.getPossibleCandidateIds());
        }

        Character character = gameEngine.getFoundedCharacter();

        if (character != null)
            System.out.println(character);
        else
            System.out.println("Character not found!");

        gameEngine.db.close();
    }
}
