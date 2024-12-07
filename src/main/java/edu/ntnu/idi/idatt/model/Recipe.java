package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.Utils.InputValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Recipe {
  private final String name;
  private final String description;
  private final String procedure;
  private final double portions;
  private final ArrayList<Ingredient> ingredientsList;
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
    this.ingredientsList = new ArrayList<>();
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
  
  public List<Ingredient> getIngredientsList() {
    return ingredientsList;
  }
  
  /**
   * Adds ingredient objects to the recipe (ingredientsList ArrayList)
   *
   * @param ingredient
   */
  public void addIngredientToRecipe(Ingredient ingredient) {
    try {
      if (ingredient == null) {
        throw new IllegalArgumentException("Ingredient cannot be null");
      }
      Optional<Ingredient> existingIngredient = ingredientsList.stream()
          .filter(ingredientInRegister -> ingredientInRegister.getName().equals(ingredient.getName()))
          .findAny();
      
      if (existingIngredient.isPresent()) {
        existingIngredient.get().updateQuantity(ingredient.getQuantity());
      } else {
        ingredientsList.add(ingredient);
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding ingredient to recipe: " + e.getMessage());
    }
  }
  
  
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
    if (ingredientsList != null && !ingredientsList.isEmpty()) {
      stringBuilder.append("Ingredients: \n");
      for (Ingredient ingredient : ingredientsList) {
        stringBuilder.append(" - ").append(ingredient).append("\n");
      }
    }
    return stringBuilder.toString();
  }
}
