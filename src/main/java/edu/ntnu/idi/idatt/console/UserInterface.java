package edu.ntnu.idi.idatt.console;

import edu.ntnu.idi.idatt.storage.Cookbook;
import edu.ntnu.idi.idatt.storage.FoodStorage;
import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * attributtes are the outer layer classes: CookingBook and FoodStorage.
 */
public class UserInterface {
  private FoodStorage foodStorage;
  private Cookbook cookBook;
  private Scanner scanner;
  
  
  /**
   * Initalizes cookingbook and foodStorage.
   */
  public void init() {
    foodStorage = new FoodStorage();
    cookBook = new Cookbook();
    scanner = new Scanner(System.in);
    preLoadData();
  }
  
  private void preLoadData() {
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
    
    Recipe recipeForChicken = new Recipe("Chicken Breast", "decription decription decription", "procedure procedure procedure", 4);
    Recipe recipeFiletMignon = new Recipe("Filet mignon", "decription decription decription", "procedure procedure procedure", 4);
    recipeForChicken.addItemToRecipe(new Item("Honey", 200, "Grams", LocalDate.of(2028, 2, 19), 30));
    recipeFiletMignon.addItemToRecipe(new Item("Margarine", 1000, "Grams", LocalDate.of(2025, 12, 24), 30));
    recipeFiletMignon.addItemToRecipe(new Item("Margarine", 1000, "Grams", LocalDate.of(2025, 12, 24), 30));
    
    recipeFiletMignon.addItemToRecipe(new Item("Filet mignon", 200, "Grams", LocalDate.of(2024, 12, 19), 100));
    
    /*
    foodStorage.findItemByName("Honey");
    
    foodStorage.removeItem("Honey", 300);
    foodStorage.getItemsExpirationDateBefore(LocalDate.of(2026, 4, 30));
    foodStorage.getExpiredItems();
    foodStorage.sortAlphabetically();
    
    foodStorage.totalValue();
    */
  }
  
  /**
   * .
   */
  public void start() {
    System.out.println("Welcome to the food conservation app");
    boolean running = true;
    while (running) {
      displayMenu();
      int choice = getUserChoice();
      
      
    }
  }
  
  private void displayMenu() {
    System.out.println("\nMenu:");
    //Nivå 1
    System.out.println(". Create a grocery");
    System.out.println(". Add a grocery"); //kunne angi mengden
    System.out.println(". Search for a grocery");
    System.out.println(". Remove grocery quantity");
    System.out.println(". View expired groceries and get its cost");
    System.out.println(". Get total value of all groceries");
    System.out.println(". View all groceries that have a expiration date before a date");
    System.out.println(". View all groceries"); //alfabetisk
    
    //nivå 2
    System.out.println(". Create a recipe");
    System.out.println(". Check if the frigdge have enough ingredient to make the recipe");
    System.out.println(". Add a recipe");
    System.out.println(". View suggested recipe");
    
    System.out.println(". View All Recipes");
    
    System.out.println(". Exit");
  }
  
  private int getUserChoice() {
    System.out.println("Enter your choice: ");
    while (!scanner.hasNext()) {
      System.out.println("Please enter a valid choice number");
      scanner.next();
    }
    return scanner.nextInt();
  }
}
