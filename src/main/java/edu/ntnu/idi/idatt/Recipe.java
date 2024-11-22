package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Recipe {
  private final String name;
  private final String description;
  private final String procedure;
  private final int recipeServes;
  private final ArrayList<Item> itemsList;
  
  public Recipe(String name, String description, String procedure, int recipeServes) {
    validateStringParameter(name, "name");
    validateStringParameter(description, "description");
    validateStringParameter(procedure, "procedure");
    validateIntParameter(recipeServes, "recipe serves");
    this.name = name;
    this.description = description;
    this.procedure = procedure;
    this.recipeServes = recipeServes;
    this.itemsList = new ArrayList<>();
  }
  
  public String getName() {
    return name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public String getProcedure() {
    return procedure;
  }
  
  public List<Item> getItemsList() {
    return itemsList;
  }
  
  private void validateStringParameter(String value, String parameterName)
      throws IllegalArgumentException {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Argument: " + parameterName + " cannot be blank/empty");
    }
  }
  
  private void validateIntParameter(int value, String parameterName)
      throws IllegalArgumentException {
    if (value <= 0) {
      throw new IllegalArgumentException("Argument: " + parameterName + " cannot be zero or negative");
    }
  }
  
  /**
   * Adds item objects to the recipe (itemsList ArrayList)
   *
   * @param item
   */
  public void addItemToRecipe(Item item) {
    Optional<Item> existingItem = itemsList.stream()
        .filter(itemInRegister -> itemInRegister.getName().equals(item.getName()))
        .findAny();
    
    if (existingItem.isPresent()) {
      existingItem.get().increaseQuantity(item.getQuantity());
      System.out.println(item + "already exist in recipe: " + getName());
      System.out.println("New quantity of item " + item + " is:" + existingItem.get().getQuantity());
    } else {
      itemsList.add(item);
      System.out.println(item + " added to the recipe.");
      
    }
  }
  
  /**
   * Checks if there are enough items in the food storage to prepare the recipe.
   * <p>
   * This method iterates through the list of items required for the recipe and compares
   * the total quantity available in the food storage with the quantity needed for each
   * item. It prints out whether there are enough items in the storage and displays the
   * required and available quantities for each item.
   * </p>
   *
   * @param foodStorage The food storage containing a collection of items in the kitchen.
   *                    This is used to check the availability of each item required for
   *                    the recipe.
   */
  public void hasEnoughItemsForRecipe(FoodStorage foodStorage) {
    boolean enoughItems = itemsList.stream()
        .allMatch(recipeItem -> {
          double totalAvailable = foodStorage.getItems().values().stream()
              .flatMap(List::stream)
              .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
              .mapToDouble(Item::getQuantity)
              .sum();
          return totalAvailable >= recipeItem.getQuantity();
        });
    
    if (enoughItems) {
      System.out.println("There is enough items in the storage to make this recipe: " + getName());
      itemsList.forEach(recipeItem -> {
        double totalQuantityAvailable = foodStorage.getItems().values().stream()
            .flatMap(List::stream)
            .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
            .mapToDouble(Item::getQuantity)
            .sum();
        
        System.out.println(" - " + recipeItem.getName()
            + ": Required = " + recipeItem.getQuantity()
            + ", Available: " + totalQuantityAvailable);
      });
    } else {
      System.out.println("There is not enough to make this recipe: " + getName());
      itemsList.forEach(recipeItem -> {
        double totalAvailable = foodStorage.getItems().values().stream()
            .flatMap(List::stream)
            .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
            .mapToDouble(Item::getQuantity)
            .sum();
        
        if (totalAvailable > 0) {
          System.out.println(" - " + recipeItem.getName()
              + ": Required = " + recipeItem.getQuantity()
              + ", Available: " + totalAvailable);
        } else {
          System.out.println(" - " + recipeItem.getName()
              + ": Required = " + recipeItem.getQuantity()
              + ", Available: 0 (Not in foodStorage)");
        }
      });
    }
  }
  
  
  // Displays recipe ingredients
  public void printIngredientsRecipe() {
    System.out.println(itemsList);
  }
  
  // Shows procedure for making the recipe
  public void procedure() {
    System.out.println("Overview of ingredients and their quantities: ");
    for (Item ingredient : itemsList) {
      System.out.println(ingredient.getName() + " quantity: " + ingredient.getQuantity() + " " + ingredient.getUnit());
    }
    System.out.println(getDescription());
  }
  
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (description != null && !description.isEmpty()) {
      stringBuilder.append("Description: ").append(description).append("\n");
    }
    if (procedure != null && !procedure.isEmpty()) {
      stringBuilder.append("Procedure: ").append(procedure).append("\n");
    }
    if (itemsList != null && !itemsList.isEmpty()) {
      stringBuilder.append("Items: \n");
      for (Item item : itemsList) {
        stringBuilder.append(" - ").append(item);
      }
    }
    return stringBuilder.toString();
  }
}
