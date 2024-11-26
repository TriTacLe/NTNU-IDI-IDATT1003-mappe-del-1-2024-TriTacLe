package edu.ntnu.idi.idatt.Utils;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.storage.Cookbook;
import edu.ntnu.idi.idatt.storage.FoodStorage;

import java.time.LocalDate;

/**
 * A class that holds dummy instances of recipes and items, and adds them to their respective registers.
 */
public class DummyData {
  
  public static void loadDummyData(FoodStorage foodStorage, Cookbook cookbook) {
    // Add dummy items to FoodStorage
    foodStorage.addItemToFoodStorage(new Item("Honey", 2000, Unit.GRAM, LocalDate.of(2028, 2, 19), 30));
    foodStorage.addItemToFoodStorage(new Item("Honey", 2000, Unit.GRAM, LocalDate.of(1934, 2, 19), 30));
    foodStorage.addItemToFoodStorage(new Item("Apple", 5, Unit.PIECES, LocalDate.of(2026, 6, 7), 10));
    foodStorage.addItemToFoodStorage(new Item("Apple", 2, Unit.PIECES, LocalDate.of(2029, 6, 7), 15));
    foodStorage.addItemToFoodStorage(new Item("Milk", 1, Unit.LITRE, LocalDate.of(2024, 12, 15), 20));
    foodStorage.addItemToFoodStorage(new Item("Eggs", 12, Unit.PIECES, LocalDate.of(2025, 12, 24), 5));
    foodStorage.addItemToFoodStorage(new Item("Sugar", 1000, Unit.GRAM, LocalDate.of(2025, 12, 24), 15));
    foodStorage.addItemToFoodStorage(new Item("Flour", 2000, Unit.GRAM, LocalDate.of(2026, 1, 15), 12));
    foodStorage.addItemToFoodStorage(new Item("Butter", 500, Unit.GRAM, LocalDate.of(2025, 5, 10), 25));
    foodStorage.addItemToFoodStorage(new Item("Butter", 200, Unit.GRAM, LocalDate.of(2029, 5, 10), 20));
    foodStorage.addItemToFoodStorage(new Item("Chocolate", 200, Unit.GRAM, LocalDate.of(2026, 3, 15), 40));
    foodStorage.addItemToFoodStorage(new Item("Banana", 6, Unit.PIECES, LocalDate.of(2024, 11, 30), 5));
    foodStorage.addItemToFoodStorage(new Item("Vanilla Extract", 50, Unit.MILLILITRE, LocalDate.of(2025, 10, 15), 10));
    foodStorage.addItemToFoodStorage(new Item("Vanilla Extract", 50, Unit.MILLILITRE, LocalDate.of(1925, 10, 15), 10));
    // Add dummy recipes to Cookbook
    Recipe pancakeRecipe = new Recipe(
        "Pancakes",
        "A simple pancake recipe.",
        "1. Mix ingredients.\n2. Cook on a skillet.",
        4
    );
    pancakeRecipe.addItemToRecipe(new Item("Flour", 250, Unit.GRAM, 12));
    pancakeRecipe.addItemToRecipe(new Item("Milk", 0.5, Unit.LITRE, 10));
    pancakeRecipe.addItemToRecipe(new Item("Eggs", 2, Unit.PIECES, 5));
    pancakeRecipe.addItemToRecipe(new Item("Sugar", 50, Unit.GRAM, 15));
    
    Recipe honeyCakeRecipe = new Recipe(
        "Honey Cake",
        "A delicious honey cake recipe.",
        "1. Mix ingredients.\n2. Bake in the oven.",
        6
    );
    honeyCakeRecipe.addItemToRecipe(new Item("Honey", 500, Unit.GRAM, 30));
    honeyCakeRecipe.addItemToRecipe(new Item("Flour", 300, Unit.GRAM, 12));
    honeyCakeRecipe.addItemToRecipe(new Item("Eggs", 4, Unit.PIECES, 5));
    honeyCakeRecipe.addItemToRecipe(new Item("Sugar", 200, Unit.GRAM, 15));
    
    Recipe bananaBreadRecipe = new Recipe(
        "Banana Bread",
        "A moist and delicious banana bread recipe.",
        "1. Mash bananas.\n2. Mix all ingredients.\n3. Bake in the oven.",
        8
    );
    bananaBreadRecipe.addItemToRecipe(new Item("Banana", 4, Unit.PIECES, 5));
    bananaBreadRecipe.addItemToRecipe(new Item("Flour", 300, Unit.GRAM, 12));
    bananaBreadRecipe.addItemToRecipe(new Item("Eggs", 2, Unit.PIECES, 5));
    bananaBreadRecipe.addItemToRecipe(new Item("Sugar", 100, Unit.GRAM, 15));
    bananaBreadRecipe.addItemToRecipe(new Item("Butter", 100, Unit.GRAM, 25));
    
    Recipe chocolateCakeRecipe = new Recipe(
        "Chocolate Cake",
        "A rich chocolate cake recipe.",
        "1. Melt chocolate.\n2. Mix ingredients.\n3. Bake in the oven.",
        10
    );
    chocolateCakeRecipe.addItemToRecipe(new Item("Chocolate", 200, Unit.GRAM, 40));
    chocolateCakeRecipe.addItemToRecipe(new Item("Flour", 250, Unit.GRAM, 12));
    chocolateCakeRecipe.addItemToRecipe(new Item("Eggs", 3, Unit.PIECES, 5));
    chocolateCakeRecipe.addItemToRecipe(new Item("Sugar", 200, Unit.GRAM, 15));
    chocolateCakeRecipe.addItemToRecipe(new Item("Butter", 150, Unit.GRAM, 25));
    
    Recipe chickenAndRice = new Recipe(
        "Chikcen and Rice",
        "Manly diet for gym",
        "1. Fry rice. \n2. Season and steak chicken \n3. Mix it up with veggies",
        1
    );
    chickenAndRice.addItemToRecipe(new Item("Chicken", 1, Unit.KILOGRAM, 90));
    chickenAndRice.addItemToRecipe(new Item("Rice", 500, Unit.GRAM, 30));
    chickenAndRice.addItemToRecipe(new Item("Oil", 100, Unit.MILLILITRE, 45));
    
    cookbook.addRecipeToCookbook(pancakeRecipe);
    cookbook.addRecipeToCookbook(honeyCakeRecipe);
    cookbook.addRecipeToCookbook(bananaBreadRecipe);
    cookbook.addRecipeToCookbook(chocolateCakeRecipe);
    cookbook.addRecipeToCookbook(chickenAndRice);
    
  }
}
