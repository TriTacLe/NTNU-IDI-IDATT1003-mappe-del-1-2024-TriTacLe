package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.storage.FoodStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the foodStorage class.
 */
class FoodStorageTest {
  private FoodStorage foodStorage;
  
  /**
   * Initializes a new FoodStorage instance before each test
   * Why: Ensures each test starts with a clean FoodStorage object, avoiding side effects from previous tests.
   */
  @BeforeEach
  void setUp() {
    foodStorage = new FoodStorage();
  }
  
  /**
   * Tests adding items to foodStorage, including when an item already exist
   */
  @Test
  void testAddItemToFoodStorage() {
    //Arrange
    Item item1 = new Item("Apple", 5, Unit.KILOGRAM, LocalDate.now().plusDays(10), 20);
    //Act
    foodStorage.addItemToFoodStorage(item1);
    //Assert
    assertTrue(foodStorage.itemExist("Apple")); //the apple item exist in storage
    assertEquals(1, foodStorage.getItems().get("apple").size()); //there is only one ArrayList for apple in the hashmap
    
    //Add another Apple with the same expiration date, unit and price.
    Item item2 = new Item("Apple", 3, Unit.KILOGRAM, LocalDate.now().plusDays(10), 20);
    foodStorage.addItemToFoodStorage(item2);
    //Verifies that the quantity is updated and the there is only one arrayList for Apple.
    assertEquals(1, foodStorage.getItems().get("apple").size());
    assertEquals(8, foodStorage.getItems().get("apple").get(0).getQuantity());
    
    //Add a Apple with different attributes
    Item item3 = new Item("Apple", 2, Unit.KILOGRAM, LocalDate.now().plusDays(15), 22);
    foodStorage.addItemToFoodStorage(item3);
    assertEquals(2, foodStorage.getItems().get("Apple").size());
    
    //negative test
    assertNotNull(foodStorage);
    assertThrows(NullPointerException.class, () -> foodStorage.addItemToFoodStorage(null));
  }
  
  /**
   * Tests searching for an item instance in foodStorage class
   */
  @Test
  void testSearchForItemInFoodStorage() {
    Item item = new Item("Milk", 1, Unit.LITRE, LocalDate.now().plusDays(5), 15);
    foodStorage.addItemToFoodStorage(item);
    
    Item result1 = foodStorage.searchForItemInFoodStorage("Milk");
    Item result2 = foodStorage.searchForItemInFoodStorage("mIlK");//test case in-sensitive
    
    assertNotNull(result1);
    assertEquals("Milk", result1.getName());
    assertNotNull(result2);
    assertEquals("Milk", result2.getName());
    
    //Negative tests
    assertNull(foodStorage.searchForItemInFoodStorage("Boxing"));
    assertThrows(NullPointerException.class, () -> foodStorage.searchForItemInFoodStorage(null));
  }
  
  /**
   * Tests removing items from foodStorage
   */
  @Test
  void testRemoveItemFromFoodStorage() {
    Item item1 = new Item("Orange", 3, Unit.KILOGRAM, LocalDate.now().plusDays(5), 25);
    Item item2 = new Item("Orange", 2, Unit.KILOGRAM, LocalDate.now().plusDays(10), 25);
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    double removedQuantity = foodStorage.removeItemFromFoodStorage("Orange", 4);
    assertEquals(1, foodStorage.getItems().get("Orange").getFirst().getQuantity());
    assertNotNull(foodStorage);
    foodStorage.removeItemFromFoodStorage("Orange", 1);
    assertFalse(foodStorage.itemExist("Orange"));
    //negative tests
    assertThrows(IllegalArgumentException.class, () -> foodStorage.removeItemFromFoodStorage("Orange", 1));
    assertThrows(IllegalArgumentException.class, () -> foodStorage.removeItemFromFoodStorage("Apple", 2));
    assertThrows(NullPointerException.class, () -> foodStorage.removeItemFromFoodStorage(null, 1));
  }
  
  /**
   * Tests retrieving expired items from foodStorage.
   */
  @Test
  void testGetExpiredItems() {
    Item item1 = new Item("tomahawk", 1, Unit.PIECES, LocalDate.now().minusDays(1), 1);
    Item item2 = new Item("Chicken", 1, Unit.KILOGRAM, LocalDate.now().plusDays(5), 90);
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    
    List<Item> expiredItems = foodStorage.getExpiredItems();
    assertEquals(1, expiredItems.size());
    assertEquals("tomahawk", expiredItems.getFirst().getName());
    
    //Negative tests
    assertTrue(foodStorage.getExpiredItems().stream()
        .noneMatch(item -> item.getName().equals("Chicken")));
    foodStorage = new FoodStorage();
    assertTrue(foodStorage.getExpiredItems().isEmpty());
  }
  
  /**
   * Tests getting items with expiration date before a date
   */
  @Test
  void testGetItemsExpirationDateBefore() {
    Item item1 = new Item("Salt", 1, Unit.GRAM, LocalDate.of(3000, 10, 10), 60);
    Item item2 = new Item("Steak", 1, Unit.KILOGRAM, LocalDate.now().plusDays(10), 40);
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    
    List<Item> itemsExpiringBeforeDate = foodStorage.getItemsExpiringBefore(LocalDate.now().plusDays(12));
    
    assertEquals(1, itemsExpiringBeforeDate.size());
    assertEquals("Steak", itemsExpiringBeforeDate.getFirst().getName());
    
    //negative
    assertTrue(foodStorage.getItemsExpiringBefore(LocalDate.now().plusDays(1)).isEmpty());
    assertThrows(NullPointerException.class, () -> foodStorage.getItemsExpiringBefore(null));
  }
  
  /**
   * Test checking if
   */
  @Test
  void testSortAlphabetically() {
    Item item1 = new Item("Banan", 5.0, Unit.PIECES, LocalDate.now().plusDays(10), 30.0);
    Item item2 = new Item("Apple", 5.0, Unit.PIECES, LocalDate.now().plusDays(7), 20.0);
    Item item3 = new Item("Apple", 2.0, Unit.PIECES, LocalDate.now().plusDays(20), 22.0);
    
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    foodStorage.addItemToFoodStorage(item3);
    
    foodStorage.getFoodStorageAlphabetically();
    
    assertEquals("Apple", foodStorage.getItems().get("Apple").getFirst());
    assertEquals("Apple", foodStorage.getItems().get("Apple").get(1));
    assertEquals("Banan", foodStorage.getItems().get("Banan").getLast());
    
    ArrayList<Item> appleItems = foodStorage.getItems().get("Apple");
    assertNotNull(appleItems);
    
    ArrayList<Item> bananaItems = foodStorage.getItems().get("Banana");
    assertNotNull(bananaItems);
  }
  
  /**
   *
   */
  @Test
  void testTotalValue() {
    Item item1 = new Item("Eggs", 12, Unit.PIECES, LocalDate.now().plusDays(5), 2);
    Item item2 = new Item("Creatine", 1000, Unit.GRAM, LocalDate.of(2028, 2, 10), 15);
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    
    double totalValue = foodStorage.calculateTotalValue(foodStorage.getItems().values().stream().flatMap(List::stream));
    double expectedTotalValue = item1.getPerUnitPrice() + item2.getPerUnitPrice();
    
    //assertEquals(17, totalValue);
    assertEquals(expectedTotalValue, totalValue, 0.01);
    
    //negative
    assertThrows(IllegalArgumentException.class, () -> foodStorage.calculateTotalValue(null));
    foodStorage = new FoodStorage();
    assertEquals(0, foodStorage.calculateTotalValue(foodStorage.getItems().values().stream().flatMap(List::stream)));
  }
  
  /**
   * Tests checking if FoodStorage is empty.
   */
  @Test
  void testIsEmpty() {
    assertTrue(foodStorage.isEmpty());
    Item item = new Item("Banana", 10, Unit.KILOGRAM, LocalDate.now().plusDays(7), 15);
    foodStorage.addItemToFoodStorage(item);
    assertFalse(foodStorage.isEmpty());
    foodStorage.removeItemFromFoodStorage("Banana", 10);
    assertTrue(foodStorage.isEmpty());
    foodStorage = new FoodStorage();
    assertTrue(foodStorage.isEmpty());
  }
}
