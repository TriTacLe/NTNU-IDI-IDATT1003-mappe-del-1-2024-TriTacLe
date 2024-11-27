package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.storage.FoodStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
  
  private Recipe recipe;
  private Item item1;
  private Item item2;
  private Item item3;
  private Item item4;
  private FoodStorage foodStorage;
  
  private static final double ITEM1_INITIAL_QUANTITY = 20;
  private static final double ITEM2_INITIAL_QUANTITY = 30;
  private static final double ITEM3_INITIAL_QUANTITY = 40;
  private static final double ITEM4_INITIAL_QUANTITY = 20;
  private static final double ITEM1_ADDITIONAL_QUANTITY = 20;
  
  @BeforeEach
  void setUp() {
    //items
    item1 = new Item("Apple", 20, Unit.PIECES, LocalDate.of(2000, 12, 15), ITEM1_INITIAL_QUANTITY);
    item2 = new Item("Milk", 100, Unit.DESILITRE, LocalDate.of(2024, 12, 15), ITEM2_INITIAL_QUANTITY);
    item3 = new Item("Sugar", 3000, Unit.GRAM, LocalDate.of(2025, 12, 24), ITEM3_INITIAL_QUANTITY);
    item4 = new Item("Orange", 5, Unit.PIECES, LocalDate.of(1900, 12, 15), ITEM4_INITIAL_QUANTITY);
    //recipe
    recipe = new Recipe("Cake", "Delicious chocolate cake", "Mix all ingredients and bake", 4);
    //foodstorage
    foodStorage = new FoodStorage();
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
  }
  
  @Test
  void testCreateRecipe() {
    assertNotNull(recipe, "Recipe should be created.");
    assertEquals("Cake", recipe.getName(), "Name should be 'Cake'");
    assertEquals("Delicious chocolate cake", recipe.getDescription(), "Description should be 'Delicious chocolate cake'");
    assertEquals("Mix all ingredients and bake", recipe.getProcedure(), "Procedure should match");
    assertEquals(4, recipe.getRecipeServes(), "Recipe serves should be 4");
  }
  
  @Test
  void testAddItemToRecipe() {
    // Add item1 to the recipe
    recipe.addItemToRecipe(item1);
    assertTrue(recipe.getItemsList().contains(item1), "Item1 should be added to the recipe.");
    
    // Add item3 (Sugar) to the recipe
    recipe.addItemToRecipe(item3);
    assertTrue(recipe.getItemsList().contains(item3), "Item3 (Sugar) should be added to the recipe.");
    
    // Add a new item to the recipe (item4)
    recipe.addItemToRecipe(item4);
    assertTrue(recipe.getItemsList().contains(item4), "Item4 (Orange) should be added to the recipe.");
    
    // Add item1 again (should increase the quantity)
    Item item1Duplicate = new Item("Apple", 20, Unit.PIECES, LocalDate.of(2000, 12, 15), ITEM1_ADDITIONAL_QUANTITY);
    recipe.addItemToRecipe(item1Duplicate);
    assertEquals(ITEM1_INITIAL_QUANTITY + ITEM1_ADDITIONAL_QUANTITY, item1.getQuantity(),
        "Quantity of Apple should be updated to the new total.");
  }
  
  @Test
  void testHasEnoughItemsForRecipe() {
    recipe.addItemToRecipe(item1);  // Apple
    recipe.addItemToRecipe(item2);  // Milk
    
    // Check if the recipe has enough ingredients in the food storage
    recipe.hasEnoughItemsForRecipe(foodStorage);
    // Expected output should confirm that the storage has enough quantities of Apple and Milk
  }
  
  @Test
  void testHasNotEnoughItemsForRecipe() {
    // Simulate the case where eggs are missing
    foodStorage.getItems().clear();
    foodStorage.addItemToFoodStorage(new Item("Eggs", 10, Unit.PIECES, LocalDate.of(2025, 12, 24), 3));
    
    recipe.addItemToRecipe(item1);  // Apple
    recipe.addItemToRecipe(item2);  // Milk
    
    // The recipe should report that there are not enough items
    recipe.hasEnoughItemsForRecipe(foodStorage);
    // Expected output should indicate insufficient quantities for some items
  }
  
  @Test
  void testInvalidConstructorArguments() {
    assertThrows(IllegalArgumentException.class, () -> new Recipe("", "description", "procedure", 4),
        "Constructor should throw IllegalArgumentException for an empty name.");
    
    assertThrows(IllegalArgumentException.class, () -> new Recipe("Recipe", "", "procedure", 4),
        "Constructor should throw IllegalArgumentException for an empty description.");
    
    assertThrows(IllegalArgumentException.class, () -> new Recipe("Recipe", "description", "", 4),
        "Constructor should throw IllegalArgumentException for an empty procedure.");
    
    assertThrows(IllegalArgumentException.class, () -> new Recipe("Recipe", "description", "procedure", -1),
        "Constructor should throw IllegalArgumentException for negative recipeServes.");
  }
  
  @Test
  void testToString() {
    recipe.addItemToRecipe(item1);
    recipe.addItemToRecipe(item2);
    
    String expectedOutput = "Name: Cake\n" +
        "Description: Delicious chocolate cake\n" +
        "Procedure: Mix all ingredients and bake\n" +
        "Items: \n" +
        " - Apple(20.0 Pieces) Expires: 2000-12-15 Price: 20.0 kr \n" +
        " - Milk(100.0 Milliliters) Expires: 2024-12-15 Price: 30.0 kr \n";
    
    assertEquals(expectedOutput, recipe.toString(), "The toString method should output the correct recipe details.");
  }
}
