import java.util.Scanner;

import Exceptions.AIConnectionFailedException;
import Models.Character;
import Models.Question;
import GameEngines.*;

public class App {
    static void play(Scanner scanner) {
        try {
            GameEngine gameEngine = new GameEngine("Anas");

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

        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    static void playAI(Scanner scanner) {
        try {
            AIGameEngine aiGameEngine = new AIGameEngine();
            String question = aiGameEngine.initGame();
            System.out.println(question);

            while (true) {
                int answer = scanner.nextInt();
                question = aiGameEngine.getNextQuestion(answer);

                System.out.println("\n" + question);
            }

        } catch (AIConnectionFailedException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        System.out.println("Akinator");

        try (Scanner scanner = new Scanner(System.in)) {
            playAI(scanner);
        }
    }
}
