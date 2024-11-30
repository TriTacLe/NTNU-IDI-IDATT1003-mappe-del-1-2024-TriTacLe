package edu.ntnu.idi.idatt.console;

import edu.ntnu.idi.idatt.Utils.DummyData;
import edu.ntnu.idi.idatt.Utils.UserInputHandler;
import edu.ntnu.idi.idatt.storage.Cookbook;
import edu.ntnu.idi.idatt.storage.FoodStorage;
import edu.ntnu.idi.idatt.service.CookbookService;
import edu.ntnu.idi.idatt.service.FoodStorageService;

import java.util.Arrays;
import java.util.Scanner;

public class UserInterface {
  
  private FoodStorageService foodStorageService;
  private CookbookService cookbookService;
  private Scanner scanner;
  private UserInputHandler inputHandler;
  
  public void init() {
    scanner = new Scanner(System.in);
    inputHandler = new UserInputHandler(scanner);
    
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookbook = new Cookbook();
    
    DummyData dummyData = new DummyData();
    dummyData.loadDummyData(foodStorage, cookbook);
    
    foodStorageService = new FoodStorageService(foodStorage, inputHandler);
    cookbookService = new CookbookService(cookbook, foodStorage, inputHandler);
  }
  
  public void start() {
    System.out.println("Welcome to the food conservation app");
    boolean running = true;
    while (running) {
      displayMenu();
      int choice = inputHandler.getValidatedInt("Enter your choice: ", "Invalid choice. Please try again.", "menu choice");
      
      switch (choice) {
        case 1 -> foodStorageService.handleAddItem();
        case 2 -> foodStorageService.handleSearchItem();
        case 3 -> foodStorageService.handleRemoveItem();
        case 4 -> foodStorageService.handleDisplayExpiredItems();
        case 5 -> foodStorageService.handleTotalValue();
        case 6 -> foodStorageService.handleViewItemsBeforeDate();
        case 7 -> foodStorageService.handleDisplayFoodStorageAlphabetically();
        case 8 -> cookbookService.handleAddRecipeToCookbook();
        case 9 -> cookbookService.handleViewHasEnoughItemsForRecipe();
        case 10 -> cookbookService.handleSuggestedRecipe();
        case 11 -> cookbookService.handleDisplayCookbook();
        case 12 -> {
          System.out.println("Thank you for taking the effort to save food!");
          running = false;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }
  
  private void displayMenu() {
    System.out.println("\nMenu:");
    Arrays.stream(MenuOption.values())
        .map(MenuOption::getDescription)
        .forEach(System.out::println);
  }
  
  public enum MenuOption {
    ADD_GROCERY_TO_FOODSTORAGE("1. Add a grocery (update quantity)"),
    SEARCH_GROCERY("2. Search for a grocery"),
    REMOVE_GROCERY("3. Remove grocery quantity"),
    VIEW_EXPIRED_GROCERIES("4. View expired groceries and get their cost"),
    TOTAL_VALUE("5. Get total value of all groceries"),
    VIEW_GROCERIES_BEFORE_DATE("6. View all groceries expiring before a date"),
    VIEW_ALL_GROCERIES("7. View all groceries (alphabetically)"),
    ADD_RECIPE_TO_COOKBOOK("8. Add a recipe to the cookbook"),
    CHECK_INGREDIENTS("9. Check if the fridge has enough ingredients for a recipe"),
    SUGGEST_RECIPES("10. View suggested recipes from the cookbook"),
    VIEW_COOKBOOK("11. View all recipes in the cookbook"),
    EXIT("12. Exit");
    
    private final String description;
    
    MenuOption(String description) {
      this.description = description;
    }
    
    public String getDescription() {
      return description;
    }
  }
}
