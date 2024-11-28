package edu.ntnu.idi.idatt.console;

import edu.ntnu.idi.idatt.Utils.DummyData;
import edu.ntnu.idi.idatt.Utils.InputValidation;
import edu.ntnu.idi.idatt.Utils.UserInputHandler;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.storage.Cookbook;
import edu.ntnu.idi.idatt.storage.FoodStorage;
import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * attributtes are the outer layer classes: CookingBook and FoodStorage.
 */
public class UserInterface {
  private FoodStorage foodStorage;
  private Cookbook cookbook;
  private Scanner scanner;
  private DummyData dummyData;
  private UserInputHandler inputHandler;
  
  
  /**
   * Initalizes cookingbook and foodStorage.
   */
  public void init() {
    scanner = new Scanner(System.in);
    inputHandler = new UserInputHandler(scanner);
    foodStorage = new FoodStorage();
    cookbook = new Cookbook();
    dummyData = new DummyData();
    dummyData.loadDummyData(foodStorage, cookbook);
  }
  
  /**
   * .
   */
  public void start() {
    System.out.println("Welcome to the food conservation app");
    boolean running = true;
    while (running) {
      displayMenu();
      int choiceInput = getUserChoice();
      MenuOption selectOption = MenuOption.values()[choiceInput - 1];
      if (selectOption == MenuOption.EXIT) {
        running = false;
      } else {
        handleUserChoice(choiceInput);
      }
    }
    System.out.println("You are welcome back anytime");
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
  
  private void displayMenu() {
    System.out.println("\nMenu:");
    Arrays.stream(MenuOption.values())
        .map(MenuOption::getDescription)
        .forEach(System.out::println);
  }
  
  private int getUserChoice() {
    System.out.println("Enter your choice: ");
    while (!scanner.hasNext()) {
      System.out.println("Please enter a valid choice number");
      scanner.next();
    }
    return scanner.nextInt();
  }
  
  private void handleUserChoice(int choice) {
    MenuOption[] options = MenuOption.values();
    if (choice < 1 || choice > options.length) {
      System.out.println("Invalid choice. Please try again.");
      return;
    }
    
    MenuOption selectedOption = options[choice - 1];
    
    switch (selectedOption) {
      case ADD_GROCERY_TO_FOODSTORAGE -> addItem();
      case SEARCH_GROCERY -> searchItem();
      case REMOVE_GROCERY -> removeItem();
      case VIEW_EXPIRED_GROCERIES -> displayExpiredItems();
      case TOTAL_VALUE -> totalValue();
      case VIEW_GROCERIES_BEFORE_DATE -> viewItemsBeforeDate();
      case VIEW_ALL_GROCERIES -> displayFoodStorageAlphabetically();
      case ADD_RECIPE_TO_COOKBOOK -> addRecipeToCookbook(); //not finished
      case CHECK_INGREDIENTS -> hasEnoughItemsForRecipe(); //not finished
      case SUGGEST_RECIPES -> suggestedRecipe(); //not finished
      case VIEW_COOKBOOK -> displayCookbook();
      case EXIT -> System.out.println("Thank you for taking the effort to save food!");
    }
  }
  
  //Methods
  
  //Foodstorage
  private void addItem() {
    try {
      final String name = inputHandler.getValidatedString("Enter item name:", "Item name cannot be empty/blank");
      double quantity = inputHandler.getValidatedDouble("Enter quantity:", "Invalid input for quantity");
      final Unit unit = inputHandler.getValidatedUnit("Enter unit (kg, g, L, mL, pcs):", "Invalid unit");
      final LocalDate expirationDate = inputHandler.getValidatedDate("Enter a date in the format yyyy-mm-dd", "Please enter a date in the format yyyy-mm-dd");
      final double pricePerUnit = inputHandler.getValidatedDouble("Enter price per unit:", "Invalid input for price");
      
      Item item = new Item(name, quantity, unit, expirationDate, pricePerUnit);
      foodStorage.addItemToFoodStorage(item);
      
      System.out.println("Item added: " + item);
      
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
  
  public void searchItem() {
    try {
      UserInputHandler inputHandler = new UserInputHandler(new Scanner(System.in));
      String name = inputHandler.getValidatedString("Enter item name:", "Item name cannot be empty/blank");
      
      if (foodStorage == null) {
        throw new IllegalArgumentException("Food storage is not initialized.");
      }
      
      List<Item> searchStatus = foodStorage.searchForItemsInFoodStorage(name.toLowerCase());
      if (searchStatus != null) {
        System.out.println("Item found: ");
        searchStatus.forEach(item -> System.out.println(" - " + item));
      } else {
        System.out.println("Item does not exist.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid input: " + e.getMessage());
    }
  }
  
  public void removeItem() {
    try {
      String name = inputHandler.getValidatedString(
          "Enter the name of the item to be removed from the food storage: ",
          "Item name cannot be empty/blank"
      ).toLowerCase();
      
      List<Item> matchingItems = foodStorage.searchForItemsInFoodStorage(name);
      if (matchingItems == null || matchingItems.isEmpty()) {
        throw new IllegalArgumentException("Item " + name + " does not exist.");
      }
      double totalQuantity = matchingItems.stream()
          .mapToDouble(Item::getQuantity)
          .sum();
      
      double quantity = inputHandler.getValidatedDouble(
          "Enter how much quantity of the item: " + matchingItems.getFirst().getName()
              + "(" + totalQuantity + matchingItems.getFirst().getUnit().getSymbol()
              + ") to be removed: ", "Invalid quantity"
      );
      
      double removedQuantity = foodStorage.removeItemFromFoodStorage(name, quantity);
      
      System.out.println("Removed " + removedQuantity + matchingItems.getFirst().getUnit().getSymbol() + " from " + matchingItems.getFirst().getName());
      
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
  
  public void displayExpiredItems() {
    /*getExpiredItems method are being called from the foodStorage class
    And the result is being assigned to the expiredItems variable*/
    try {
      List<Item> expiredItems = foodStorage.getExpiredItems();
      
      if (expiredItems == null || expiredItems.isEmpty()) {
        System.out.println("No items is expired! Nice!");
        return;
      }
      
      double totalValue = foodStorage.calculateTotalValue(expiredItems.stream());
      
      System.out.println("Expired items");
      expiredItems.forEach(item -> System.out.println("- " + item));
      System.out.printf("Total value of expired items: %.2f kr%n", totalValue); //2 desimaler
    } catch (IllegalArgumentException e) {
      System.out.println("Error while trying to display expired items: " + e.getMessage());
    }
  }
  
  public void totalValue() {
    try {
      if (foodStorage == null || foodStorage.isEmpty()) {
        System.out.println("Food storage is empty");
        return;
      }
      double totalValue = foodStorage.calculateTotalValue(foodStorage.getItems().values().stream()
          .flatMap(List::stream));
      
      System.out.println("The total value of the food storage is: " + totalValue + " kr");
    } catch (IllegalArgumentException e) {
      System.out.println("Error while getting the total value of food storage: " + e.getMessage());
    }
  }
  
  public void viewItemsBeforeDate() {
    LocalDate date = inputHandler.getValidatedDate("Enter a date (yyyy-mm-dd) to view items expiring before it: ", "Please enter a date in the format yyyy-mm-dd");
    try {
      List<Item> itemsBeforeDate = foodStorage.getItemsExpiringBefore(date);
      if (itemsBeforeDate == null || itemsBeforeDate.isEmpty()) {
        System.out.println("No items expire before: " + date);
      } else {
        System.out.println("Items expiring before date: " + date + ": ");
        //itemsBeforeDate.forEach(System.out::println);
        itemsBeforeDate.forEach(item -> System.out.println("- " + item));
        
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error while retrieving items: " + e.getMessage());
    }
  }
  
  public void displayFoodStorageAlphabetically() {
    System.out.println("Food storage sorted out alphabetically by name:");
    if (foodStorage == null || foodStorage.isEmpty()) {
      System.out.println("Food storage is empty. These is not items in food storage.");
      return;
    }
    try {
      foodStorage.getFoodStorageAlphabetically();
    } catch (IllegalArgumentException e) {
      System.out.println("Error while displaying the food storage from a-z: " + e.getMessage());
    }
  }
  
  //Cookbook
  
  /**
   *
   */
  public void addRecipeToCookbook() {
    final String nameRecipe = inputHandler.getValidatedString("Enter recipe name: ", "Recipe name cannot be empty/blank");
    final String description = inputHandler.getValidatedString("Enter a description: ", "Description cannot be empty/blank");
    final String procedure = inputHandler.getValidatedString("Enter the procedure: ", "Procedure cannot be empty/blank");
    final double portions = inputHandler.getValidatedDouble("Enter how many people this recipe is for", "Portions cannot be negative");
    Recipe recipe = new Recipe(nameRecipe, description, procedure, portions);
    double choiceDouble = inputHandler.getValidatedDouble("Would you add recipe manually: 1. Chose from existing ingrediens: 2.", "Error");
    int choice = (int) choiceDouble;
    
    switch (choice) {
      case 1:
        int totalItemsCaseOne = (int) inputHandler.getValidatedDouble("Enter how many items you want this recipe to have", "Total items cannot be negative");
        for (int i = 0; i < totalItemsCaseOne; i++) {
          System.out.println("Item: " + i);
          final String name = inputHandler.getValidatedString("Enter item name:", "Item name cannot be empty/blank");
          double quantity = inputHandler.getValidatedDouble("Enter quantity:", "Invalid input for quantity");
          final Unit unit = inputHandler.getValidatedUnit("Enter unit (kg, g, L, mL, pcs):", "Invalid unit");
          final double pricePerUnit = inputHandler.getValidatedDouble("Enter price per unit:", "Invalid input for price");
          Item item = new Item(name, quantity, unit, pricePerUnit);
          recipe.addItemToRecipe(item);
          cookbook.addRecipeToCookbook(recipe);
        }
        break;
      case 2:
        int totalItemsCaseTwo = (int) inputHandler.getValidatedDouble("Enter how many items you want this recipe to have", "Total items cannot be negative");
        
        System.out.println("Every item that has not been expired and can be used in a recipe");
        List<Item> foodStorageBeforeDate = foodStorage.getItemsExpiringAfter(LocalDate.now());
        foodStorageBeforeDate.forEach(item -> System.out.println("- " + item));
        
        for (int i = 0; i < totalItemsCaseTwo; i++) {
          String itemStringKey = inputHandler.getValidatedString("Enter the item you want", "Error getting the item");
          
          if (foodStorage.getItems() != null || foodStorage.getItems().containsKey(itemStringKey.toLowerCase())) {
            List<Item> itemsRetrieved = foodStorage.getItems().get(itemStringKey.toLowerCase());
            
            if (itemsRetrieved != null && !itemsRetrieved.isEmpty()) {
              itemsRetrieved.sort(Comparator.comparing(Item::getExpirationDate));
              Item itemGotten = itemsRetrieved.get(0);
              recipe.addItemToRecipe(itemGotten);
              System.out.println("Added item: " + itemGotten.getName() + " to the recipe.");
            } else {
              System.out.println("No items found with this name");
            }
          } else {
            System.out.println("Item is not found in the storage");
          }
          cookbook.addRecipeToCookbook(recipe);
        }
    }
  }
  
  
  public void hasEnoughItemsForRecipe() {
    
  }
  
  public void suggestedRecipe() {
    cookbook.getSuggestedRecipes(foodStorage);
  }
  
  public void displayCookbook() {
    HashMap<String, ArrayList<Recipe>> cookbookContents = cookbook.getCookbook();
    
    if (cookbookContents.isEmpty()) {
      System.out.println("The cookbook does not contain any recipes.");
      return;
    }
    System.out.println("Recipes in the cookbook: ");
    cookbookContents.forEach((recipeName, recipes) -> {
      System.out.println("Recipe Name: " + recipeName);
      recipes.forEach(this::displayRecipe);
    });
  }
  
  /**
   * Helper
   *
   * @param recipe
   */
  private void displayRecipe(Recipe recipe) {
    System.out.println(recipe.toString());
  }
}
