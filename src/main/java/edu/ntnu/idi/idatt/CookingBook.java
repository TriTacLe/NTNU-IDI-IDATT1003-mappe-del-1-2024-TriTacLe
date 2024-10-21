//import java.time.LocalDate;
import java.util.ArrayList;
//import java.util.random.*;

public class CookingBook{
    private ArrayList<Recipe> recipes;

    public CookingBook(){
        this.recipes = new ArrayList<>();
    }

    //legg til oppskrift (instans av Recipe) til kokebok recipes
    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    } 

    //Få forslag til hvilke retter som kan lages fra rettene i kokeboken med varene/ingrediensene som finnes i kjøleskapet. (Avansert!)
    public void suggestionRecipe(){
        int randomSuggestion = (int)(Math.random() * 101); // 0 to 100, 0 - antall oppskrift + 1 
        
        switch(randomSuggestion){
            case
        }
    } 
    

    
}