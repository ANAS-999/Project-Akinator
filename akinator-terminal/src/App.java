import Entities.GameEngine;
import Entities.Question;
import java.util.Scanner;

public class App {
    static void printLine() {
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

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

        System.out.println(gameEngine.getBestQuestion());
        System.out.println(gameEngine.getBestQuestion());

        gameEngine.db.close();
    }
}
