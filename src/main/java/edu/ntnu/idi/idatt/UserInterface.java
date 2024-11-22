package edu.ntnu.idi.idatt;

import java.time.LocalDate;

/**
 * attributtes are the outer layer classes: CookingBook and FoodStorage.
 */
public class UserInterface {
  private FoodStorage foodStorage;
  private Cookbook cookBook;
  
  /**
   * Initalizes cookingbook and foodStorage.
   */
  public void init() {
    foodStorage = new FoodStorage();
    cookBook = new Cookbook();
  }
  
  /**
   * .
   */
  public void start() {
    foodStorage.addItem(new Item("Honey", 2000, "Grams", LocalDate.of(2028, 2, 19), 30));
    foodStorage.addItem(new Item("Apple", 2, "Pieces", LocalDate.of(2000, 12, 15), 20));
    foodStorage.addItem(new Item("Apple", 4, "Pieces", LocalDate.of(2026, 6, 7), 18));
    
    
    foodStorage.addItem(new Item("Honey", 2000, "Grams", LocalDate.of(2028, 2, 19), 30));
    foodStorage.addItem(new Item("Milk", 100, "Milliliters", LocalDate.of(2024, 12, 15), 30));
    foodStorage.addItem(new Item("Eggs", 10, "Pieces", LocalDate.of(2025, 12, 24), 3));
    foodStorage.addItem(new Item("Margarine", 1000, "Grams", LocalDate.of(2025, 12, 24), 30));
    foodStorage.addItem(new Item("Sugar", 3000, "Grams", LocalDate.of(2025, 12, 24), 40));
    // Expired items
    foodStorage.addItem(new Item("Apple", 2, "Pieces", LocalDate.of(2000, 12, 15), 20));
    foodStorage.addItem(new Item("Orange", 5, "Pieces", LocalDate.of(1900, 12, 15), 20));
    
    foodStorage.findItemByName("Honey");
    foodStorage.removeItem("Honey", 300);
    
    foodStorage.getItemsExpirationDateBefore(LocalDate.of(2026, 4, 30));
    foodStorage.getExpiredItems();
    
    foodStorage.sortAlphabetically();
    System.out.println(); //Space
    foodStorage.totalValue();
    
    
    Recipe recipeForChicken = new Recipe("Chicken Breast", "decription decription decription", "procedure procedure procedure", 4);
    Recipe recipeFiletMignon = new Recipe("Filet mignon", "decription decription decription", "procedure procedure procedure", 4);
    
    
    recipeForChicken.addItemToRecipe(new Item("Honey", 200, "Grams", LocalDate.of(2028, 2, 19), 30));
    recipeFiletMignon.addItemToRecipe(new Item("Margarine", 1000, "Grams", LocalDate.of(2025, 12, 24), 30));
    recipeFiletMignon.addItemToRecipe(new Item("Margarine", 1000, "Grams", LocalDate.of(2025, 12, 24), 30));
    
    recipeFiletMignon.addItemToRecipe(new Item("Filet mignon", 200, "Grams", LocalDate.of(2024, 12, 19), 100));
    
    
    recipeForChicken.hasEnoughItemsForRecipe(foodStorage);
    recipeFiletMignon.hasEnoughItemsForRecipe(foodStorage);
    
  }
}
