//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
import java.time.LocalDate;

public class testItemFridge {
    public static void main(String[] args) {

        Fridge fridge = new Fridge();
        
        fridge.addItem(new Item("Melk", 100, "Milliliter", LocalDate.of(2024, 12, 15), 30));
        fridge.addItem(new Item("Egg", 10, "Stykker", LocalDate.of(2025, 12, 24), 3));
        fridge.addItem(new Item("Margarin", 1000, "gram", LocalDate.of(2025, 12, 24), 30));
        fridge.addItem(new Item("Sukker", 3000, "gram", LocalDate.of(2025, 12, 24), 40));

        //expired item eple
        fridge.addItem(new Item("Eple", 2, "Stykker", LocalDate.of(2000, 12, 15), 20));

        System.out.println("Dato idag " + LocalDate.now());

        //add
        System.out.print("Fridge: ");fridge.printFridge();

        //find
        System.out.println("Melk: " + fridge.findItem("Melk"));
        
        //remove item
        double quantity = 1;
        fridge.removeItem("Melk", quantity);
        System.err.print("Fridge etter å ha fjernet 1 melk: "); fridge.printFridge();

        fridge.printExpiredItems();

        //total price
        System.err.println("Total pris: " + fridge.totalValue());

        //tester andre klassene
        
        String description =  "\nVaffeloppskrift!!!!!!!: " +
        "\n1. Ha mel og sukker i en bolle." +
        "\n2. Spe med litt av melken om gangen." +
        "\n3. Rør inn egg og tilset."+
        "\n4. Stek vaflene i et vaffeljern.";

        Recipe vaffelRecipe = new Recipe("Vaffelroere", description);

        vaffelRecipe.addItemForRecipe(new Item("Melk", 40, "Milliliter", LocalDate.of(2024, 12, 12), 25));
        vaffelRecipe.addItemForRecipe(new Item("Egg", 3, "Stykker", LocalDate.of(2025, 5, 12), 3));
        vaffelRecipe.addItemForRecipe(new Item("Margarin", 100, "Gram", LocalDate.of(2025, 1, 20), 40));
        vaffelRecipe.addItemForRecipe(new Item("Sukker", 10, "gram", LocalDate.of(2027, 7, 4), 20));

        vaffelRecipe.quantityFridge(fridge);   

        vaffelRecipe.printIngredientsRecipe();

        //vaffelRecipe.procedure();

        //teser cookingbook
        CookingBook cookingBook = new CookingBook();
        cookingBook.addRecipe(vaffelRecipe);
        cookingBook.printCookingBook();

        cookingBook.suggestionRecipe(fridge);
    }
}
