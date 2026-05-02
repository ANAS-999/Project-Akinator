package Models;

public class Character {
    private int id;
    private String name;
    private int categoryId;
    private String imagePath;

    public Character(int id, String name, int categoryId, String imagePath) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Character [id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", imagePath=" + imagePath
                + "]";
    }

}
