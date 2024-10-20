//import java.time.LocalDate;
import java.util.ArrayList;

public class CookingBook{
    private ArrayList<Recipe> recipes;

    public CookingBook(){
        this.recipes = new ArrayList<>();
    }

    //legg til oppskrift (instans av Recipe) til kokebok recipes
    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }
    
}