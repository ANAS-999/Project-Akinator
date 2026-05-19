package modele;

public class Question {

	private String idQuestion;
	private String text;
	private static int nbQuestion;
	private int parentQuestionId;
	
	// ============Constructeur===============
	public Question(String text, int parentQuestionId) {
		this.text = text;
		this.parentQuestionId = parentQuestionId;
		idQuestion = "nbQuestion";
		
	}
	
	// =============Getters and setters================
	public String getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(String idQuestion) {
		this.idQuestion = idQuestion;
	}

	public String getTexte() {
		return text;
	}

	public void setTexte(String texte) {
		this.text = texte;
	}

	public int getParentQuestionId() {
		return parentQuestionId;
	}
	
	// ================Incremrnt score================
	public void setParentQuestionId(int parentQuestionId) {
		this.parentQuestionId = parentQuestionId;
	}

}
