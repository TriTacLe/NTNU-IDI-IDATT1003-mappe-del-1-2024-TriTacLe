package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.Ingredient;
import edu.ntnu.idi.idatt.model.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Ingredient Class")
class IngredientTest {
  
  @Nested
  @DisplayName("Tests for Ingredient Creation")
  class CreationTests {
    
    @Test
    @DisplayName("Should create Ingredient with valid attributes")
    void shouldCreateIngredientWithValidAttributes() {
      try {
        Ingredient ingredient = new Ingredient("Protein Bar", 2.5, Unit.PIECES, LocalDate.now().plusDays(10), 3.5);
        assertNotNull(ingredient);
        assertEquals("Protein Bar", ingredient.getName());
        assertEquals(2.5, ingredient.getQuantity());
        assertEquals(Unit.PIECES, ingredient.getUnit());
        assertEquals(LocalDate.now().plusDays(10), ingredient.getExpirationDate());
        assertEquals(3.5, ingredient.getPrice());
      } catch (IllegalArgumentException e) {
        fail("Exception should not have been thrown for valid input: " + e.getMessage());
      }
      
    }
    
    @Test
    @DisplayName("Should throw exception for empty name")
    void shouldThrowExceptionForEmptyName() {
      try {
        new Ingredient("", 2.5, Unit.GRAM, LocalDate.now().plusDays(10), 3.5);
        fail("Expected IllegalArgumentException for empty name");
      } catch (IllegalArgumentException e) {
        assertEquals("Name cannot be empty or blank", e.getMessage());
      }
    }
    
    @Test
    @DisplayName("Should throw exception for negative or invalid quantity")
    void shouldThrowExceptionForNegativeQuantity() {
      try {
        double invalidQuantity = -1.0;
        new Ingredient("Protein Bar", invalidQuantity, Unit.KILOGRAM, LocalDate.now().plusDays(10), 3.5);
        fail("Expected IllegalArgumentException for invalid quantity: " + invalidQuantity);
      } catch (IllegalArgumentException e) {
        assertEquals("Quantity cannot be negative/or another type than double", e.getMessage());
      }
    }
    
    @Test
    @DisplayName("Should throw exception for null unit")
    void shouldThrowExceptionForNullUnit() {
      try {
        new Ingredient("Protein Bar", 2.5, null, LocalDate.now().plusDays(10), 3.5);
        fail("Expected IllegalArgumentException for null unit");
      } catch (IllegalArgumentException e) {
        assertEquals("Unit cannot be empty or null: null", e.getMessage());
      }
      ;
    }
    
    @Test
    @DisplayName("Should throw exception for negative price")
    void shouldThrowExceptionForNegativePrice() {
      try {
        double invalidPrice = -3.5;
        new Ingredient("Protein Bar", 2.5, Unit.KILOGRAM, LocalDate.now().plusDays(10), invalidPrice);
        fail("Expected IllegalArgumentException for invalid price: " + invalidPrice);
      } catch (IllegalArgumentException e) {
        assertEquals("Price cannot be negative/or another type than double", e.getMessage());
      }
    }
  }
  
  @Nested
  @DisplayName("Tests for Ingredient Attributes and Methods")
  class AttributeTests {
    
    private final Ingredient ingredient = new Ingredient("Protein Bar", 2.0, Unit.KILOGRAM, LocalDate.now().plusDays(5), 10.0);
    
    @Test
    @DisplayName("Should update quantity correctly")
    void shouldUpdateQuantity() {
      try {
        ingredient.updateQuantity(3.0);
        assertEquals(5.0, ingredient.getQuantity());
      } catch (IllegalArgumentException e) {
        fail("Exception should not have been thrown while updating quantity: " + e.getMessage());
      }
    }
    
    @Test
    @DisplayName("Should set quantity correctly")
    void shouldSetQuantityCorrectly() {
      try {
        ingredient.setQuantity(4.0);
        assertEquals(4.0, ingredient.getQuantity());
      } catch (IllegalArgumentException e) {
        fail("Exception should not have been thrown while setting quantity" + e.getMessage());
      }
    }
    
    @Test
    @DisplayName("Should throw exception for invalid quantity in setter")
    void shouldThrowExceptionForInvalidQuantityInSetter() {
      try {
        ingredient.setQuantity(-1.0);
        fail("Expected IllegalArgumentException for invalid quantity");
      } catch (IllegalArgumentException e) {
        assertEquals("Quantity cannot be negative/or another type than double", e.getMessage());
      }
    }
    
    @Test
    @DisplayName("Should return correct expiration date")
    void shouldReturnCorrectExpirationDate() {
      assertEquals(LocalDate.now().plusDays(5), ingredient.getExpirationDate());
    }
    
    @Test
    @DisplayName("toString should return formatted string")
    void shouldReturnFormattedToString() {
      String expectedString = "Protein Bar (2.0 kg) Expires: " + LocalDate.now().plusDays(5) + " Price: 10.0 kr";
      assertEquals(expectedString, ingredient.toString());
    }
  }
  
  @Nested
  @DisplayName("Tests how robust Ingredient class is")
  class RobustTests {
    
    @Test
    @DisplayName("Should throw exception for null expiration date")
    void shouldThrowExceptionForNullExpirationDate() {
      try {
        new Ingredient("Creatine", 2.5, Unit.KILOGRAM, null, 15.0);
        fail("Expected IllegalArgumentException for null expiration date");
      } catch (IllegalArgumentException e) {
        assertEquals("Expiration date cannot be null", e.getMessage());
      }
    }
    
    @Test
    @DisplayName("Should allow creation without expiration date")
    void shouldAllowCreationWithoutExpirationDate() {
      try {
        Ingredient ingredient = new Ingredient("Creatine", 3.0, Unit.KILOGRAM, 10.0);
        assertNotNull(ingredient);
        assertEquals("Creatine", ingredient.getName());
        assertEquals(3.0, ingredient.getQuantity());
        assertEquals(Unit.KILOGRAM, ingredient.getUnit());
        assertEquals(10.0, ingredient.getPrice());
        assertNull(ingredient.getExpirationDate());
      } catch (IllegalArgumentException e) {
        fail("Should not throw exception for valid input" + e.getMessage());
      }
      
    }
  }
}
