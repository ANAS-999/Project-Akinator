import modele.*;
import playerDataBase.*;

public class App {
    public static void main(String[] args) throws Exception {
    	
        Player p = new Player("younes", "password");
        PlayerDAO.savePlayer(p);
        System.out.println("Hello, World!" + " " + p.getIdPlayer());
    }
}
