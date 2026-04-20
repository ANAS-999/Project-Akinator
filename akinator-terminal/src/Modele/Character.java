package Modele;

import java.util.Map;

public class Character {
	
	private String idCharacter;
	private String name;
	private String imagePath;
	private Map<Integer, Integer> answers;
	private static int nbCharacter = 0;
	
	// ============Constructeur==================
	public Character(String name, String imagePath, Map<Integer, Integer> answers) {
		this.name = name;
		this.imagePath = imagePath;
		this.answers = answers;
		idCharacter = "nbCharacter";
		nbCharacter++;
	}

	

	// =============Getters and setters =====================
	public String getIdCharacter() {
		return idCharacter;
	}

	public void setIdCharacter(String idCharacter) {
		this.idCharacter = idCharacter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Map<Integer, Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(Map<Integer, Integer> answers) {
		this.answers = answers;
	}
	
	// =====================Method matches=======================
	public boolean matches(int questionId, int userAnswer) {
    	if (this.answers.containsKey(questionId)) {
        	int characterAnswer = this.answers.get(questionId);
        	return characterAnswer == userAnswer;
    	}
    
    	return false; 
	}
}
