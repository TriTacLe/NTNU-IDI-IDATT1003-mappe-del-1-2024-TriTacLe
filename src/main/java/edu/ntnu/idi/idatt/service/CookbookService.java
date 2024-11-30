package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.Utils.UserInputHandler;
import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.storage.Cookbook;
import edu.ntnu.idi.idatt.storage.FoodStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CookbookService {
  private final Cookbook cookbook;
  private final FoodStorage foodStorage;
  private final UserInputHandler inputHandler;
  
  public CookbookService(Cookbook cookbook, FoodStorage foodStorage, UserInputHandler inputHandler) {
    this.cookbook = cookbook;
    this.foodStorage = foodStorage;
    this.inputHandler = inputHandler;
  }
  
  /**
   *
   */
  public void handleAddRecipeToCookbook() {
    try {
      final String nameRecipe = inputHandler.getValidatedString("Enter recipe name: ", "Recipe name cannot be empty/blank", "recipe name");
      final String description = inputHandler.getValidatedString("Enter a description: ", "Description cannot be empty/blank", "description");
      final String procedure = inputHandler.getValidatedString("Enter the procedure: ", "Procedure cannot be empty/blank", "procedure");
      final double portions = inputHandler.getValidatedDouble("Enter how many people this recipe is for", "Portions cannot be negative", "portions");
      
      Recipe recipe = new Recipe(nameRecipe, description, procedure, portions);
      
      try {
        int choice = inputHandler.getValidatedInt("How would you like to add ingredients? 1. Add manually 2. Choose from available food storage items. Enter your choice: ", "Invalid input. Enter 1 or 2.", "choice");
        
        switch (choice) {
          case 1 -> addItemsManually(recipe);
          case 2 -> addItemsFromStorage(recipe);
          default -> System.out.println("Invalid choice.");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid choice input. Please enter a valid option (1 or 2)");
        return;
      }
      
      boolean success = cookbook.addRecipeToCookbook(recipe);
      if (success) {
        System.out.println("Recipe added to the cookbook successfully:");
        System.out.println(recipe);
      } else {
        System.out.println("Recipe for " + recipe.getName() + " already exists in the cookbook.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error while creating recipe: " + e.getMessage());
    }
  }
  
  private void addItemsManually(Recipe recipe) {
    try {
      int totalItems = (int) inputHandler.getValidatedDouble("Enter how many items you want this recipe to have", "Total items cannot be negative", "Total items");
      for (int i = 0; i < totalItems; i++) {
        System.out.println("Item: " + (i + 1));
        final String name = inputHandler.getValidatedString("Enter item name:", "Item name cannot be empty/blank", "name");
        double quantity = inputHandler.getValidatedDouble("Enter quantity:", "Invalid input for quantity", "quantity");
        final Unit unit = inputHandler.getValidatedUnit("Enter unit (kg, g, L, mL, pcs):", "Invalid unit");
        final double pricePerUnit = inputHandler.getValidatedDouble("Enter price per unit:", "Invalid input for price", "price");
        
        Item item = new Item(name, quantity, unit, pricePerUnit);
        recipe.addItemToRecipe(item);
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding items manually: " + e.getMessage());
    }
  }
  
  
  private void addItemsFromStorage(Recipe recipe) {
    try {
      int totalItems = (int) inputHandler.getValidatedDouble("Enter how many items you want this recipe to have", "Total items cannot be negative/other type than int or double", "total items");
      
      System.out.println("Every item that has not expired and can be used in a recipe:");
      List<Item> availableItems = foodStorage.getItemsExpiringAfter(LocalDate.now());
      availableItems.forEach(item -> System.out.println("- " + item));
      
      for (int i = 0; i < totalItems; i++) {
        System.out.println("Item: " + (i + 1) + " of " + totalItems);
        handleItemAddition(recipe);
      }
    } catch (IllegalArgumentException e) {
      System.out.println("An error occurred while adding items from storage: " + e.getMessage());
    }
  }
  
  private void handleItemAddition(Recipe recipe) {
    try {
      String itemKey;
      List<Item> items;
      
      do {
        itemKey = inputHandler.getValidatedString("Enter the item you want", "Error: Could not retrieve the item name.", "name").toLowerCase();
        
        items = getItemsFromStorage(itemKey);
        if (items == null || items.isEmpty()) {
          System.out.println("Item not found in storage. Please enter a valid item.");
        }
      } while (items == null || items.isEmpty());
      
      double totalAvailableQuantity = items.stream()
          .mapToDouble(Item::getQuantity)
          .sum();
      
      System.out.println("Total available quantity for " + itemKey + ": " + totalAvailableQuantity + ". Please read: Remember this is total quantity of " + itemKey + " in the food storage. How much you want to add to the recipe is an unrealized amount so it will not be deducted from the real amount.");
      
      int requestedQuantity = inputHandler.getValidatedInt("Enter quantity to add:", "Invalid input for quantity.", "requested quantity");
      
      if (requestedQuantity > totalAvailableQuantity && !confirmAdditionExceedingQuantity()) {
        System.out.println("Item not added to the recipe.");
        return;
      }
      
      allocateItemsToRecipe(recipe, items, requestedQuantity);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid input: " + e.getMessage());
    }
  }
  
  private List<Item> getItemsFromStorage(String itemKey) {
    try {
      if (foodStorage.getItems() == null || !foodStorage.getItems().containsKey(itemKey)) {
        return null;
      }
      
      List<Item> items = new ArrayList<>(foodStorage.getItems().get(itemKey));
      items.sort(Comparator.comparing(Item::getExpirationDate));
      return items;
    } catch (IllegalArgumentException e) {
      System.out.println("Error while retrieving items from storage: " + e.getMessage());
      return null;
    }
  }
  
  private boolean confirmAdditionExceedingQuantity() {
    try {
      String proceed = inputHandler.getValidatedString("Requested quantity exceeds available items. Do you still want to add this item? (yes/no)", "Invalid input. Please answer yes or no.", "yes/no input");
      return proceed.equalsIgnoreCase("yes");
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid input: " + e.getMessage());
      return false;
    }
  }
  
  private void allocateItemsToRecipe(Recipe recipe, List<Item> items, int requestedQuantity) {
    try {
      double remainingToUse = requestedQuantity;
      
      for (Item item : items) {
        if (remainingToUse <= 0) break;
        
        double availableQuantity = item.getQuantity();
        double toAllocate = Math.min(availableQuantity, remainingToUse);
        remainingToUse -= toAllocate;
        
        Item itemToAdd = new Item(item.getName(), toAllocate, item.getUnit(), item.getPerUnitPrice());
        recipe.addItemToRecipe(itemToAdd);
        System.out.println("Added item: " + itemToAdd.getName() + " (" + toAllocate + " " + itemToAdd.getUnit().getSymbol() + ") to the recipe.");
      }
      
      if (remainingToUse > 0) {
        System.out.println("Could not use the full quantity. Short by " + remainingToUse);
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error while try to allocate items to the recipe: " + e.getMessage());
    }
  }
  
  
  public void handleViewHasEnoughItemsForRecipe() {
    try {
      if (cookbook == null || cookbook.getRecipes().isEmpty()) {
        System.out.println("The cookbook is empty. Add some recipes first.");
        return;
      }
      
      System.out.println("Select a recipe to check if you have enough ingredients:");
      List<Recipe> allRecipes = new ArrayList<>(cookbook.getRecipes().values());
      for (int i = 0; i < allRecipes.size(); i++) {
        System.out.println((i + 1) + ". " + allRecipes.get(i).getName());
      }
      
      int recipeChoice = inputHandler.getValidatedInt("Enter the number of the recipe: ", "Invalid! Please enter a valid number.", "recipe choice");
      
      if (recipeChoice < 1 || recipeChoice > allRecipes.size()) {
        System.out.println("Invalid choice. Returning to the menu.");
        return;
      }
      
      Recipe selectedRecipe = allRecipes.get(recipeChoice - 1);
      
      boolean enoughItems = foodStorage.hasEnoughItemsForRecipe(foodStorage, selectedRecipe);
      Map<String, Double> availability = foodStorage.getItemAvailabilityForRecipe(foodStorage, selectedRecipe);
      
      if (enoughItems) {
        System.out.println("There are enough items in the storage to make this recipe: " + selectedRecipe.getName());
        selectedRecipe.getItemsList().forEach(recipeItem -> {
          double available = availability.getOrDefault(recipeItem.getName(), 0.0);
          System.out.println(" - " + recipeItem.getName()
              + ": Required = " + recipeItem.getQuantity()
              + ", Available: " + available);
        });
      } else {
        System.out.println("There is not enough to make this recipe: " + selectedRecipe.getName());
        selectedRecipe.getItemsList().forEach(recipeItem -> {
          double available = availability.getOrDefault(recipeItem.getName(), 0.0);
          if (available > 0) {
            System.out.println(" - " + recipeItem.getName()
                + ": Required = " + recipeItem.getQuantity()
                + ", Available: " + available);
          } else {
            System.out.println(" - " + recipeItem.getName()
                + ": Required = " + recipeItem.getQuantity()
                + ", Available: 0 (Not in foodStorage)");
          }
        });
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid input: " + e.getMessage());
    } catch (NullPointerException e) {
      System.out.println("An error occurred: Make sure that your food storage and cookbook are initialized." + e.getMessage());
      //Exception handles all the exceptions
    } catch (Exception e) {
      System.out.println("An unknown error occurred: " + e.getMessage());
    }
  }
  
  
  public void handleSuggestedRecipe() {
    try {
      List<Recipe> suggestedRecipes = cookbook.getSuggestedRecipes(foodStorage);
      
      if (suggestedRecipes.isEmpty()) {
        System.out.println("No recipes can be made with the current food storage.");
        return;
      }
      
      System.out.println("You can make the following recipes with the current food storage:");
      suggestedRecipes.forEach(recipe -> System.out.println("- " + recipe.getName()));
    } catch (Exception e) {
      System.out.println("An error occurred while suggesting recipes: " + e.getMessage());
    }
  }
  
  public void handleDisplayCookbook() {
    try {
      HashMap<String, Recipe> cookbookContents = cookbook.getRecipes();
      if (cookbookContents.isEmpty()) {
        System.out.println("The cookbook does not contain any recipes.");
        return;
      }
      System.out.println("Recipes in the cookbook: ");
      System.out.println();
      cookbookContents.forEach((recipeName, recipe) -> {
        System.out.println("Recipe Name: " + recipeName);
        displayRecipe(recipe);
      });
    } catch (Exception e) {
      System.out.println("Error: Something unwanted has happened.");
    }
  }
  
  /**
   * Helper
   *
   * @param recipe
   */
  private void displayRecipe(Recipe recipe) {
    System.out.println("Recipe Name: " + recipe.getName());
    System.out.println("Description: " + recipe.getDescription());
    System.out.println("Procedure: " + recipe.getProcedure());
    System.out.println("Portions: " + recipe.getPortions());
    System.out.println("Ingredients:");
    recipe.getItemsList().forEach(item -> {
      System.out.println(" - " + item.getName() + ": " + item.getQuantity() + " " + item.getUnit().getSymbol());
    });
    System.out.println();
  }
  //System.out.println(recipe.toString());
  
}
