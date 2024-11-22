package edu.ntnu.idi.idatt; //import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
      System.out.println("The cookbook does not contain any recipes.");
    } else {
      System.out.println("Recipes in the cookbook: ");
      
      for (Map.Entry<String, ArrayList<Recipe>> entry : recipes.entrySet()) {
        System.out.println("Recipe Name: " + entry.getKey());
        
        for (Recipe recipe : entry.getValue()) {
          System.out.println(recipe);
        }
      }
    }
  }
  
  
  public void suggestionRecipe(FoodStorage foodStorage) {
    /*compare all the items and quantity of the items in recipes in the cookbook
    with
    the items and quantity of the items in foodStorage
    
    //Iterate over each recipe
      //check if foodstorage has this item
      
      //check if the quantity is enough
     
    
     */
  }
  
}