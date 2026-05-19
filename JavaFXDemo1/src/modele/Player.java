package modele;

public class Player {
    private String name;
	private String idPlayer;
	private String userName;
	private String passWord;
	private int score;
	private int gamesNb;
	private static int nbPlayer = 1;
	
	// ==============Constructeur================
	public Player(String name, String userName, String passWord) {

        this.name = name;
		this.userName = userName;
		this.passWord = passWord;
		idPlayer = userName + nbPlayer;
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

	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public int getGamesNb() {
		return gamesNb;
	}


	public void setGamesNb(int gamesNb) {
		this.gamesNb = gamesNb;
	}


	// ===============Score increment==================
	public void incrementScore(int increment) {
		score += increment;
	}
}
