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
    System.out.println("Welcome to the Food Conservation App");
    System.out.println("Type 'help' for instructions or 'exit' to quit.");
    boolean running = true;
    while (running) {
      displayMenu();
      String choice = scanner.nextLine().trim();
      
      switch (choice.toLowerCase()) {
        case "1":
          foodStorageService.handleAddItem();
          break;
        case "2":
          foodStorageService.handleSearchItem();
          break;
        case "3":
          foodStorageService.handleRemoveItem();
          break;
        case "4":
          foodStorageService.handleDisplayExpiredItems();
          break;
        case "5":
          foodStorageService.handleTotalValue();
          break;
        case "6":
          foodStorageService.handleViewItemsBeforeDate();
          break;
        case "7":
          foodStorageService.handleDisplayFoodStorageAlphabetically();
          break;
        case "8":
          cookbookService.handleAddRecipeToCookbook();
          break;
        case "9":
          cookbookService.handleViewHasEnoughItemsForRecipe();
          break;
        case "10":
          cookbookService.handleSuggestedRecipe();
          break;
        case "11":
          cookbookService.handleDisplayCookbook();
          break;
        case "help":
          displayHelp();
          break;
        case "exit":
          System.out.println("Thank you for using the Food Conservation App! Goodbye.");
          running = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again or type 'help' for guidance.");
          break;
      }
    }
  }
  
  private void displayMenu() {
    System.out.println("\nMain Menu:");
    Arrays.stream(MenuOption.values())
        .map(MenuOption::getDescription)
        .forEach(System.out::println);
    System.out.println("Enter the corresponding number to make a selection, 'help' for instructions, or 'exit' to quit.");
  }
  
  private void displayHelp() {
    System.out.println("HELP MENU:");
    System.out.println("1: Add Grocery - Add or update a grocery item in the food storage.");
    System.out.println("2: Search Grocery - Find a grocery item by name.");
    System.out.println("3: Remove Grocery - Remove or reduce the quantity of a grocery item.");
    System.out.println("4: View Expired Groceries - Lists all expired items and their total cost.");
    System.out.println("5: Total Value - View the total value of items in food storage.");
    System.out.println("6: View Groceries Before Date - List items expiring before a specific date.");
    System.out.println("7: View All Groceries - Display all groceries sorted alphabetically.");
    System.out.println("8: Add Recipe - Add a recipe to the cookbook.");
    System.out.println("9: Check Ingredients - Check if you have enough ingredients for a recipe.");
    System.out.println("10: Suggest Recipes - Suggest recipes based on available ingredients.");
    System.out.println("11: View Cookbook - Display all recipes in the cookbook.");
    System.out.println("exit: Quit the application.");
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
    VIEW_COOKBOOK("11. View all recipes in the cookbook");
    
    private final String description;
    
    MenuOption(String description) {
      this.description = description;
    }
    
    public String getDescription() {
      return description;
    }
  }
}

