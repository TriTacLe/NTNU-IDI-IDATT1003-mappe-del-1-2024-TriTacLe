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

    public String getDescription(){
        return description;
    }

    public ArrayList<Item> getItems(){
        return ingredients;
    }

    /**
     * Sjekke om fridge har nok varer til å lage retten
     * @param fridge
     */
    public void quantityFridge(Fridge fridge){
        boolean found = true;
        for (Item ingredient : ingredients) {
            for (Item fridgeItem : fridge.getItems()) {
                //compare quantity av items (fridge) med ingredienser som trengs
                if (fridgeItem.getName().equals(ingredient.getName())) {
                    if (ingredient.getQuantity() > fridgeItem.getQuantity()){
                        System.out.println("Fridge har ikke nok varer av: " + fridgeItem.getName());
                        found = false;
                    }   
                }
            }
        }
        if (found == true){
            System.out.println("Fridge har nok ingredienser av til å lage retten: " + this.getName());
        }
    }

    //legger til item til ingredienser
    public void addItemForRecipe(Item item){  
        ingredients.add(item);
    }

    //vis recipe ingredienser
    public void printIngredientsRecipe(){
        System.out.println(ingredients);
    }
    //getter
    public ArrayList<Item> getIngredients(){
        return ingredients;
    }

    //fremgangsmåte på hvordan man lager retten
    public void procedure(){
        /*
         * Skriv tekst i string format. bruk getQuantity for mengde ingrediens
         * 
         */
        
        System.out.println("Oversikt over ingredienser og dens mengde: ");
        for (Item ingredient : ingredients){
            System.out.println(ingredient.getName() + " antall: " + ingredient.getQuantity() + " " + ingredient.getUnit());
        }
        System.out.println(getDescription());
    }
}
