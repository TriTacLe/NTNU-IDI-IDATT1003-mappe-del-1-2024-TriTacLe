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

    public void printCookingBook(){
        System.out.println("Kokeboken: ");
        for (Recipe recipe : recipes) {
            System.out.println(recipe.getName());
        }
    }

    //Få forslag til hvilke retter som kan lages fra rettene i kokeboken med varene/ingrediensene som finnes i kjøleskapet. (Avansert!)
    /*
    public void suggestionRecipe() {
        if (recipes.isEmpty()) {
            System.out.println("No recipes available in the CookingBook.");
        }
        Random rand = new Random();
        int randomSuggestion = rand.nextInt(recipes.size());
        
        Recipe suggestedRecipe = recipes.get(randomSuggestion);
        // Print the procedure of the suggested recipe
        System.out.println("Suggested Recipe: " + suggestedRecipe.getName());
        suggestedRecipe.procedure();   
    }
    */

    public void suggestionRecipe(Fridge fridge){
        //looper over alle ingredienser i fridge
        //sjekker om ingredienser i recipe finnes i fridge
        //hvis ja, sjekk om antallet av hver ingrediens i recipe 
        //er mindre eller lik antallet ingredienser i fridge
        //Hvis ja kan man lage det -> anbefal det
        //hvis nei kan man ikke lage det
        
        ArrayList<Recipe> suggestedRecipes = new ArrayList<Recipe>();
        
        for (Recipe recipe : recipes) {
            boolean found = true;
            for (Item recipeItem : recipe.getItems()) {
                boolean itemFound = false;  
                for (Item fridgeItem : fridge.getItems()) {
                    if (fridgeItem.getName().equals(recipeItem.getName())) {
                        if (fridgeItem.getQuantity() >= recipeItem.getQuantity()) {
                            itemFound = true;
                            break;//går ut av loopen da varen er funnet, sparer effektivitet
                        }
                    }
                }
                if (!itemFound) {
                    found = false;  
                    break; //går ut av loopen da retten kan ikke lages
                }
            }
            if (found) {
                suggestedRecipes.add(recipe); 
            }
        }
        
        System.out.println("Suggested recipes: ");
        for (Recipe recipe : suggestedRecipes) {
            System.out.println(recipe.getName());
        }
    }
}