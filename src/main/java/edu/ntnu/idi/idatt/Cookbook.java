package edu.ntnu.idi.idatt; //import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;

public class Cookbook {
  //private ArrayList<Recipe> recipes;
  private HashMap<String, ArrayList<Recipe>> recipes;
  
  public Cookbook() {
    this.recipes = new HashMap<>();
  }
  
  
  public void addRecipe(Recipe recipe) {
    recipes.putIfAbsent(recipe.getName(), new ArrayList<>());
    ArrayList<Recipe> recipeArrayList = recipes.get(recipe.getName());
    
    if (!recipeArrayList.contains(recipe)) {
      recipeArrayList.add(recipe);
      System.out.println("Recipe added: " + recipe);
    } else {
      System.out.println("Recipe for " + recipe.getName() + " already exist");
    }
  }
  
  public void displayCookbook() {
    if (recipes.isEmpty()) {
      System.out.println("The cookbook does not contain any recipes");
    } else {
      System.out.println("Recipes in the cookbook: ");
      recipes.forEach((key, value) -> System.out.println(" - " + key + ": " + value));
    }
    
  }
  
  /**
   * looper over alle ingredienser i fridge
   * sjekker om ingredienser i recipe finnes i fridge
   * hvis ja, sjekk om antallet av hver ingrediens i recipe
   * er mindre eller lik antallet ingredienser i fridge
   * Hvis ja kan man lage det -> anbefal det
   * hvis nei kan man ikke lage det.
   *
   * @param fridge fridge
   */
  
  public void suggestionRecipe(Fridge fridge) {
    ArrayList<Recipe> suggestedRecipes = new ArrayList<Recipe>();
    /*
    for (Recipe recipe : recipes) {
      boolean found = true;
      for (Item recipeItem : recipe.getItems()) {
        boolean itemFound = false;
        for (Item fridgeItem : fridge.getItems()) {
          if (fridgeItem.getName().equals(recipeItem.getName())) {
            if (fridgeItem.getQuantity() >= recipeItem.getQuantity()) {
              itemFound = true;
              break; //går ut av loopen da varen er funnet, sparer effektivitet
            }
          }
        }
        if (!itemFound) {
          found = false;
          break; //går ut av loopen da retten kan ikke lages
        }
      }
      if (found) {
        suggestedRecipes.add(recipe);
      }
    }
    
    System.out.println("Suggested recipes: ");
    for (Recipe recipe : suggestedRecipes) {
      System.out.println(recipe.getName());
    }
  }
  
     */
  }
}