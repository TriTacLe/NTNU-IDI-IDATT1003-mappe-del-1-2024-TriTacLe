package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.Utils.InputValidation;
import edu.ntnu.idi.idatt.storage.FoodStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Recipe {
  private final String name;
  private final String description;
  private final String procedure;
  private final double portions;
  private final ArrayList<Item> itemsList;
  private InputValidation inputValidation;
  
  public Recipe(String name, String description, String procedure, double portions) {
    inputValidation.validateString(name, "Name of the recipe cannot be blank/empty");
    inputValidation.validateString(description, "Description cannot be blank/empty");
    inputValidation.validateString(procedure, "Procedure cannot be blank/empty");
    inputValidation.validateDouble(portions, "Portions cannot negative");
    
    this.name = name;
    this.description = description;
    this.procedure = procedure;
    this.portions = portions;
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
  
  public double getPortions() {
    return portions;
  }
  
  public List<Item> getItemsList() {
    return itemsList;
  }
  
  /**
   * Adds item objects to the recipe (itemsList ArrayList)
   *
   * @param item
   */
  public boolean addItemToRecipe(Item item) {
    Optional<Item> existingItem = itemsList.stream()
        .filter(itemInRegister -> itemInRegister.getName().equals(item.getName()))
        .findAny();
    
    if (existingItem.isPresent()) {
      existingItem.get().updateQuantity(item.getQuantity());
      return false;
    } else {
      itemsList.add(item);
      return true;
    }
  }
  /*
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
  /*
  public void hasEnoughItemsForRecipe(FoodStorage foodStorage) {
    boolean enoughItems = itemsList.stream()
        .allMatch(recipeItem -> {
          double totalAvailable = foodStorage.getItems().values().stream()
              .flatMap(List::stream)
              .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
              .mapToDouble(storageItem -> {
                if (!storageItem.getUnit().equals(recipeItem.getUnit())) {
                  return storageItem.getUnit().converter(storageItem.getQuantity(), recipeItem.getUnit());
                }
                return storageItem.getQuantity();
              })
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
   */
  
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (name != null && !name.isBlank()) {
      stringBuilder.append("Name: ").append(name).append("\n");
    }
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
