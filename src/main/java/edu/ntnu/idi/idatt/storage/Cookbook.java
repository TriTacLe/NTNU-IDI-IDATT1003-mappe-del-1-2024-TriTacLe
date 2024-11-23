package edu.ntnu.idi.idatt.storage; //import java.time.LocalDate;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;

import java.util.*;

public class Cookbook {
  //private ArrayList<Recipe> recipes;
  private HashMap<String, ArrayList<Recipe>> recipes;
  
  public Cookbook() {
    this.recipes = new HashMap<>();
  }
  
  public void addRecipeToCookbook(Recipe recipe) {
    recipes.putIfAbsent(recipe.getName(), new ArrayList<>());
    ArrayList<Recipe> recipeArrayList = recipes.get(recipe.getName());
    
    if (!recipeArrayList.contains(recipe)) {
      recipeArrayList.add(recipe);
      System.out.println("Recipe added: " + recipe);
    } else {
      System.out.println("Recipe for " + recipe.getName() + " already exist");
    }
  }
  
  public HashMap<String, ArrayList<Recipe>> getCookbook() {
    recipes.forEach((key, value) -> {
      System.out.println("Recipe Name: " + key);
      value.forEach(System.out::println);
    });
    return recipes;
  }
  
  public ArrayList<Recipe> getSuggestedRecipes(FoodStorage foodStorage) {
    /*compare all the items and quantity of the items in recipes in the cookbook
    with the items and quantity of the items in foodStorage*/
    ArrayList<Recipe> suggestedRecipes = new ArrayList<>();
    
    recipes.forEach((recipeName, recipeList) -> {
      //Iterate over each recipe
      recipeList.forEach(recipe -> {
        boolean canMakeRecepe = recipe.getItemsList().stream()
            .allMatch(recipeItem -> {
              String itemName = recipeItem.getName();
              Double neededQuantity = recipeItem.getQuantity();
              
              //check if foodstorage has this item
              List<Item> storageItems = foodStorage.getItems().get(itemName);
              //sum quantities of the same item
              double availableQuantity = storageItems.stream()
                  .mapToDouble(Item::getQuantity)
                  .sum();
              
              return availableQuantity >= neededQuantity;
            });
        if (canMakeRecepe) {
          suggestedRecipes.add(recipe);
        }
      });
    });
    return suggestedRecipes;
  }
  
}