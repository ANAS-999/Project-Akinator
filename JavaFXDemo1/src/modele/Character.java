package modele;

import java.util.Map;

public class Character {
	
	private String idCharacter;
	private String name;
	private String imagePath;
	private Map<Integer, Integer> answers;
	private static int nbCharacter = 0;
	private int categoryId;
	
	// ============Constructeur==================
	public Character(String name, String imagePath, Map<Integer, Integer> answers) {
		this.name = name;
		this.imagePath = imagePath;
		this.answers = answers;
		idCharacter = "nbCharacter";
		nbCharacter++;
	}

	// Overloaded constructor for database retrieval
	public Character(String idCharacter, String name, String imagePath, int categoryId, Map<Integer, Integer> answers) {
		this.idCharacter = idCharacter;
		this.name = name;
		this.imagePath = imagePath;
		this.categoryId = categoryId;
		this.answers = answers;
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
