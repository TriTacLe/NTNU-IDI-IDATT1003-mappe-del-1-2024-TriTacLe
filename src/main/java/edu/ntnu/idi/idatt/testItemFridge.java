//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
import java.time.LocalDate;

public class testItemFridge {
    public static void main(String[] args) {

        Fridge fridge = new Fridge();
        
        fridge.addItem(new Item("Melk", 4, "liter", LocalDate.of(2024, 12, 15), 30));
        //expired item
        fridge.addItem(new Item("Eple", 2, "stk", LocalDate.of(2, 12, 15), 20));
        //fridge.addItem(new Item("Ost", 2, "Kg", 20));

        System.out.println("Dato idag " + LocalDate.now());

        //add
        System.out.print("Fridge: ");fridge.printFridge();

        //find
        System.out.println("Melk: " + fridge.findItem("Melk"));
        
        //remove item
        double quantity = 1;
        fridge.removeItem("Melk", quantity);
        System.err.print("Fridge etter å ha fjernet 1 melk: "); fridge.printFridge();

        System.out.println("utgåtte varer: " + fridge.printExpiredItems());

        //total price
        System.err.println("Total pris: " + fridge.totalValue());

        //tester andre klassene
        String desription = "kake";
        Recipe recipe = new Recipe("Kake", desription);

        recipe.addItemForRecipe(new Item("Melk", 2, "Liter", LocalDate.of(2024, 12, 12), 12));

        recipe.quantityFridge(fridge);

        recipe.printIngredientsRecipe();
    }
}
