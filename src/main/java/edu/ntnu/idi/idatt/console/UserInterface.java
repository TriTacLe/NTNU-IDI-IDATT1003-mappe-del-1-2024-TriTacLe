package edu.ntnu.idi.idatt.console;

import edu.ntnu.idi.idatt.Utils.Unit;
import edu.ntnu.idi.idatt.storage.Cookbook;
import edu.ntnu.idi.idatt.storage.FoodStorage;
import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * attributtes are the outer layer classes: CookingBook and FoodStorage.
 */
public class UserInterface {
  private FoodStorage foodStorage;
  private Cookbook cookbook;
  private Scanner scanner;
  
  
  /**
   * Initalizes cookingbook and foodStorage.
   */
  public void init() {
    foodStorage = new FoodStorage();
    cookbook = new Cookbook();
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
    ADD_GROCERY_TO_FOODSTORAGE("2. Add a grocery (update quantity)"),
    SEARCH_GROCERY("3. Search for a grocery"),
    REMOVE_GROCERY("4. Remove grocery quantity"),
    VIEW_EXPIRED_GROCERIES("5. View expired groceries and get their cost"),
    TOTAL_VALUE("6. Get total value of all groceries"),
    VIEW_GROCERIES_BEFORE_DATE("7. View all groceries expiring before a date"),
    VIEW_ALL_GROCERIES("8. View all groceries (alphabetically)"),
    CREATE_RECIPE("9. Create a recipe"),
    CHECK_INGREDIENTS("10. Check if the fridge has enough ingredients for a recipe"),
    ADD_RECIPE_TO_COOKBOOK("11. Add a recipe to the cookbook"),
    SUGGEST_RECIPES("12. View suggested recipes from the cookbook"),
    VIEW_COOKBOOK("13. View all recipes in the cookbook"),
    EXIT("14. Exit");
    
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
      case REMOVE_GROCERY -> removeItem();
      case VIEW_EXPIRED_GROCERIES -> displayExpiredItems();
      case TOTAL_VALUE -> totalValue();
      case VIEW_GROCERIES_BEFORE_DATE -> viewItemsBeforeDate();
      case VIEW_ALL_GROCERIES -> displayFoodStorageAlphabetically();
      case ADD_RECIPE_TO_COOKBOOK -> addRecipeToCookbook(); //not finished
      case CHECK_INGREDIENTS -> hasEnoughItemsForRecipe();
      case SUGGEST_RECIPES -> suggestedRecipe(); //not finished
      case VIEW_COOKBOOK -> displayCookbook();
      case EXIT -> System.out.println("Thank you for taking the effort to save food!");
    }
  }
  
  //Methods
  
  //Foodstorage
  private void addItem() {
    System.out.print("Enter item name: ");
    String name = scanner.next();
    
    System.out.print("Enter quantity: ");
    double quantity = scanner.nextDouble();
    
    System.out.print("Enter unit (kg, g, L, mL, pcs): ");
    String unitInput = scanner.next().toUpperCase();
    Unit unit;
    try {
      unit = Unit.valueOf(unitInput);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid unit. Please enter a valid unit (e.g., g, kg, L, mL, pcs).");
      return;
    }
    
    System.out.print("Enter expiration date (yyyy-mm-dd): ");
    String dateInput = scanner.next();
    LocalDate expirationDate = LocalDate.parse(dateInput);
    
    System.out.print("Enter price per unit: ");
    double pricePerUnit = scanner.nextDouble();
    
    Item item = new Item(name, quantity, unit, expirationDate, pricePerUnit);
    foodStorage.addItemToFoodStorage(item);
    System.out.println("Item added: " + item);
  }
  
  public void removeItem() {
    System.out.print("Enter the name of the item to be removed from the food storage: ");
    String name = scanner.next();
    
    System.out.print("Enter how much (quantity) of the item that should be removed: ");
    double quantity = scanner.nextDouble();
    
    foodStorage.removeItemFromFoodStorage(name, quantity);
  }
  
  public void displayExpiredItems() {
    /*getExpiredItems method are being called from the foodStorage class
    And the result is being assigned to the expiredItems variable*/
    List<Item> expiredItems = foodStorage.getExpiredItems();
    
    if (expiredItems.isEmpty()) {
      System.out.println("No items is expired! Nice!");
    }
    
    double totalValue = foodStorage.calculateTotalValue(expiredItems);
    
    System.out.println("Expired items");
    expiredItems.forEach(item -> System.out.println("- " + item));
    System.out.printf("Total value of expired items: %.2f kr%n", totalValue); //2 desimaler
  }
  
  public void totalValue() {
    if (foodStorage.isEmpty()) {
      System.out.println("Food storage is empty");
    }
    double totalValue = foodStorage.totalValueOfFoodStorage();
    System.out.println("The total value of the food storage is: " + totalValue + " kr");
    
    foodStorage.totalValueOfFoodStorage();
  }
  
  public void viewItemsBeforeDate() {
    System.out.print("Enter a date (yyyy-mm-dd) to view items expiring before it: ");
    String inputDate = scanner.next();
    LocalDate date;
    try {
      date = LocalDate.parse(inputDate);
    } catch (DateTimeParseException e) {
      System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
      return;
    }
    
    List<Item> itemsBeforeDate = foodStorage.getItemsExpiringBefore(date);
    
    if (itemsBeforeDate.isEmpty()) {
      System.out.println("No items expire before: " + date);
    } else {
      itemsBeforeDate.forEach(System.out::println);
    }
  }
  
  public void displayFoodStorageAlphabetically() {
    System.out.println("Food storage sorted out alphabetically by name:");
    foodStorage.getFoodStorageAlphabetically();
  }
  
  //Cookbook
  
  /**
   *
   */
  public void addRecipeToCookbook() {
    cookbook.addRecipeToCookbook();
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
