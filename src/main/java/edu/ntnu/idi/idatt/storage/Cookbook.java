package edu.ntnu.idi.idatt.storage;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookbook {
  private HashMap<String, Recipe> recipes;
  
  public Cookbook() {
    this.recipes = new HashMap<>();
  }
  
  public HashMap<String, Recipe> getRecipes() {
    return recipes;
  }
  
  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe the recipe to add
   * @return true if the recipe was successfully added, false if it already exists
   */
  public boolean addRecipeToCookbook(Recipe recipe) {
    if (recipes.containsKey(recipe.getName())) {
      return false; // Recipe already exists
    }
    recipes.put(recipe.getName(), recipe);
    return true;
  }

//  /**
//   * Displays all recipes in the cookbook.
//   *
//   * @return a map of all recipes
//   */
//  public HashMap<String, Recipe> getCookbook() {
//    recipes.forEach((key, value) -> {
//      System.out.println("Recipe Name: " + key);
//      System.out.println(value);
//    });
//    return recipes;
//  }
  
  /**
   * Suggests recipes that can be made based on the current food storage.
   *
   * @param foodStorage the food storage to compare against
   * @return a list of suggested recipes
   */
  public List<Recipe> getSuggestedRecipes(FoodStorage foodStorage) {
    List<Recipe> suggestedRecipes = new ArrayList<>();
    
    if (foodStorage == null || foodStorage.getItems() == null || recipes == null || recipes.isEmpty()) {
      return suggestedRecipes;
    }
    
    Map<String, List<Item>> normalizedStorage = new HashMap<>();
    foodStorage.getItems().forEach((key, value) -> {
      normalizedStorage.put(key.toLowerCase().trim(), value);
    });
    
    recipes.forEach((recipeName, recipe) -> {
      boolean canMakeRecipe = recipe.getItemsList().stream()
          .allMatch(recipeItem -> {
            String itemName = recipeItem.getName().toLowerCase().trim();
            double neededQuantity = recipeItem.getQuantity();
            
            List<Item> storageItems = normalizedStorage.get(itemName);
            
            if (storageItems == null || storageItems.isEmpty()) {
              return false;
            }
            
            double availableQuantity = storageItems.stream()
                .mapToDouble(storageItem -> {
                  if (!storageItem.getUnit().equals(recipeItem.getUnit())) {
                    try {
                      return storageItem.getUnit().convertValue(storageItem.getQuantity(), recipeItem.getUnit());
                    } catch (IllegalArgumentException e) {
                      return 0.0;
                    }
                  }
                  return storageItem.getQuantity();
                })
                .sum();
            
            return availableQuantity >= neededQuantity;
          });
      
      if (canMakeRecipe) {
        suggestedRecipes.add(recipe);
      }
    });
    return suggestedRecipes;
  }
  
}
