package edu.ntnu.idi.idatt;//import static org.junit.Assert.assertEquals;
//import org.junit.Test;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.storage.Cookbook;
import edu.ntnu.idi.idatt.storage.FoodStorage;

import java.time.LocalDate;

public class TestKlient {
  public static void main(String[] args) {
    //test del 1
        /*
        Fridge fridge = new Fridge();
        fridge.addItem(new Item("Mel", 2000, "gram", LocalDate.of(2028, 2, 19), 30));
        fridge.addItem(new Item("Melk", 100, "Milliliter", LocalDate.of(2024, 12, 15), 30));
        //expired item eple
        fridge.addItem(new Item("Eple", 2, "Stykker", LocalDate.of(2000, 12, 15), 20));
        fridge.addItem(new Item("Appelsin", 5, "Stykker", LocalDate.of(1900, 12, 15), 20));

        System.out.println(); //mellomrom

         */
    
    System.out.println("Date today: " + LocalDate.now());
    
    System.out.println(); //mellomrom
        /*
        //add
        System.out.print("Fridge: ");fridge.printFridge();

        System.out.println(); //mellomrom 
        //find
        System.out.println("Melk: " + fridge.findItem("Melk"));
        
        System.out.println(); //mellomrom 
        //remove item
        double quantity = 1;
        fridge.removeItem("Melk", quantity);
        System.out.print("Fridge etter å ha fjernet 1 melk: ");
        fridge.printFridge();

        System.out.println(); //mellomrom 

        fridge.printExpiredItems();

        System.out.println(); //mellomrom 
        //total price
        System.out.println("Total pris: " + fridge.totalValue() + " kr");



        //tester andre klassene
        
        String description =  "\nVaffeloppskrift!!!!!!!: " +
        "\n1. Ha mel og sukker i en bolle." +
        "\n2. Spe med litt av melken om gangen." +
        "\n3. Rør inn egg og tilset."+
        "\n4. Stek vaflene i et vaffeljern.";

        Recipe vaffelRecipe = new Recipe("Vaffelroere", description);

        vaffelRecipe.addItemForRecipe(new Item("Mel", 500, "gram", LocalDate.of(2028, 2, 19), 30));
        vaffelRecipe.addItemForRecipe(new Item("Melk", 40, "Milliliter", LocalDate.of(2024, 12, 12), 25));
        vaffelRecipe.addItemForRecipe(new Item("Egg", 3, "Stykker", LocalDate.of(2025, 5, 12), 3));
        vaffelRecipe.addItemForRecipe(new Item("Margarin", 100, "Gram", LocalDate.of(2025, 1, 20), 40));
        vaffelRecipe.addItemForRecipe(new Item("Sukker", 10, "gram", LocalDate.of(2027, 7, 4), 20));

        System.out.println(); //mellomrom 

        vaffelRecipe.quantityFridge(fridge);   

        System.out.println(); //mellomrom 
        
        System.out.print("Ingredienser i oppskriften: ");vaffelRecipe.printIngredientsRecipe();

        System.out.println(); //mellomrom 

        vaffelRecipe.procedure();

        System.out.println(); //mellomrom 

        //teser cookingbook
        CookingBook cookingBook = new CookingBook();
        cookingBook.addRecipe(vaffelRecipe);
        cookingBook.printCookingBook();

        System.out.println(); //mellomrom 

        cookingBook.suggestionRecipe(fridge);

         */
    
    //Test del 2
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookBook = new Cookbook();
    
    foodStorage.addItemToFoodStorage(new Item("Honey", 2000, "Grams", LocalDate.of(2028, 2, 19), 30));
    foodStorage.addItemToFoodStorage(new Item("Apple", 2, "Pieces", LocalDate.of(2000, 12, 15), 20));
    foodStorage.addItemToFoodStorage(new Item("Apple", 4, "Pieces", LocalDate.of(2026, 6, 7), 18));
    
    
    foodStorage.addItemToFoodStorage(new Item("Honey", 2000, "Grams", LocalDate.of(2028, 2, 19), 30));
    foodStorage.addItemToFoodStorage(new Item("Milk", 100, "Milliliters", LocalDate.of(2024, 12, 15), 30));
    foodStorage.addItemToFoodStorage(new Item("Eggs", 10, "Pieces", LocalDate.of(2025, 12, 24), 3));
    foodStorage.addItemToFoodStorage(new Item("Margarine", 1000, "Grams", LocalDate.of(2025, 12, 24), 30));
    foodStorage.addItemToFoodStorage(new Item("Sugar", 3000, "Grams", LocalDate.of(2025, 12, 24), 40));
    // Expired items
    foodStorage.addItemToFoodStorage(new Item("Apple", 2, "Pieces", LocalDate.of(2000, 12, 15), 20));
    foodStorage.addItemToFoodStorage(new Item("Orange", 5, "Pieces", LocalDate.of(1900, 12, 15), 20));
    
    foodStorage.findItemByName("Honey");
    foodStorage.removeItem("Honey", 300);
    
    foodStorage.getItemsExpirationDateBefore(LocalDate.of(2026, 4, 30));
    foodStorage.getExpiredItems();
    
    foodStorage.sortAlphabetically();
    System.out.println(); //Space
    foodStorage.totalValue();
    
    
    Recipe recipeForChicken = new Recipe("Chicken Breast", "decription decription decription", "procedure procedure procedure", 4);
    Recipe recipeFiletMignon = new Recipe("Filet mignon", "decription decription decription", "procedure procedure procedure", 4);
    
    
    recipeForChicken.addItemToRecipe(new Item("Honey", 200, "Grams", 30));
    recipeFiletMignon.addItemToRecipe(new Item("Margarine", 1000, "Grams", 30));
    recipeFiletMignon.addItemToRecipe(new Item("Margarine", 1000, "Grams", 30));
    
    recipeFiletMignon.addItemToRecipe(new Item("Filet mignon", 200, "Grams", 100));
    
    System.out.println("-" + recipeFiletMignon.toString());
    
    recipeForChicken.hasEnoughItemsForRecipe(foodStorage);
    recipeFiletMignon.hasEnoughItemsForRecipe(foodStorage);
    
    System.out.println(recipeFiletMignon);
    cookBook.addRecipe(recipeFiletMignon);
    cookBook.addRecipe(recipeForChicken);
    
    cookBook.displayCookbook();
    
    System.out.println("Suggsted recipes");
    cookBook.suggestionRecipe(foodStorage).forEach(recipe -> {
      System.out.println(recipe);
    });
  }
}
