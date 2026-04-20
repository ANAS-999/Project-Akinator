package Modele;

public class Player {
	private String idPlayer;
	private String userName;
	private int score;
	private static int nbPlayer = 1;
	
	// ==============Constructeur================
	public Player(String useName) {

		this.userName = useName;
		idPlayer = useName + "nbPlayer";
		nbPlayer++;
	}

	
	// ==============Getters and stters=============
	public String getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(String idPlayer) {
		this.idPlayer = idPlayer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	// ===============Score increment==================
	public void incrementScore(int increment) {
		score += increment;
	}
}
