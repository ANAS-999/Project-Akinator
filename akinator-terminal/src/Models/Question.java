package Models;

import java.util.Objects;

public class Question {
    private int id;
    private String text;
    private Integer parentQuestionId;

    public int getId() {
        return id;
    }

    public Question(int id, String text, Integer parentQuestionId) {
        this.id = id;
        this.text = text;
        this.parentQuestionId = parentQuestionId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(Integer parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", text=" + text + ", parentQuestionId=" + parentQuestionId + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        return id == other.id && Objects.equals(text, other.text)
                && Objects.equals(parentQuestionId, other.parentQuestionId);
    }

}
