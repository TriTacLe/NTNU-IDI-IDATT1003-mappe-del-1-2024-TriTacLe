import java.util.ArrayList;

public class Recipe {
    private final String name;
    private final String description; 
    private ArrayList<Item> ingredients;
    
    public Recipe(String name, String description){
        this.name = name;
        this.description = description;
        this.ingredients = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public String description(){
        return description;
    }

    
}
