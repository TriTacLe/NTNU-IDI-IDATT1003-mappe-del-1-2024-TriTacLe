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
   * Check if fridge has enough items to make the recipe.
   *
   * @param foodStorage the storage to check against
   */
  public void hasEnoughItemsForRecipe(FoodStorage foodStorage) {
    boolean enoughItems = itemsList.stream()
        .allMatch(recipeItem ->
            foodStorage.getItems().values().stream()
                .flatMap(List::stream)
                .anyMatch(storageItem -> storageItem.getName().equals(recipeItem.getName())
                    && storageItem.getQuantity() >= recipeItem.getQuantity()
                )
        );
    
    if (enoughItems) {
      System.out.println("There is enough items in the storage to make this recipe: " + getName());
      itemsList.forEach(recipeItem -> foodStorage.getItems().values().stream()
          .flatMap(List::stream)
          .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
          .forEach(storageItem -> {
            System.out.println((" - " + recipeItem.getName() + ": Required = " + recipeItem.getQuantity()
                + ", Available: " + storageItem.getQuantity()));
          }));
    } else {
      System.out.println("There is not enough to make this recipe: " + getName());
      itemsList.forEach(recipeItem -> {
        Optional<Item> missingItems = foodStorage.getItems().values().stream()
            .flatMap(List::stream)
            .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
            .findAny();
        System.out.println(" - " + recipeItem.getName() + ": Required = " + recipeItem.getQuantity()
            + ", Available: " + missingItems.map(Item::getQuantity).orElse(0.0)
        );
      });
    }
  }
  
  // Adds an item to the ingredients list
  public void addItemToRecipe(Item item) {
    itemsList.add(item);
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
    itemsList.forEach(item -> stringBuilder.append(item));
    return stringBuilder.toString();
  }
}
