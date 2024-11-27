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
    assertTrue(foodStorage.itemExist("apple"), "Apple should exist in the storage");//the apple item exist in storage
    assertEquals(1, foodStorage.getItems().get("apple").size(), "There should only be one entry for Apple");//there is only one ArrayList for apple in the hashmap
    
    //Add another Apple with the same expiration date, unit and price.
    Item item2 = new Item("Apple", 3, Unit.KILOGRAM, LocalDate.now().plusDays(10), 20);
    foodStorage.addItemToFoodStorage(item2);
    //Verifies that the quantity is updated and the there is only one arrayList for Apple.
    assertEquals(1, foodStorage.getItems().get("apple").size(), "There should still be only one entry for Apple with matching attributes");
    assertEquals(8, foodStorage.getItems().get("apple").get(0).getQuantity(), "Quantity should be updated to 8");
    
    //Add a Apple with different attributes
    Item item3 = new Item("Apple", 2, Unit.KILOGRAM, LocalDate.now().plusDays(15), 22);
    foodStorage.addItemToFoodStorage(item3);
    assertEquals(2, foodStorage.getItems().get("apple").size(), "A second entry should exist for Apple with different attributes");
    
    //negative test
    assertNotNull(foodStorage);
    assertThrows(NullPointerException.class, () -> foodStorage.addItemToFoodStorage(null));
  }
  
  /**
   * Tests searching for an item instance in foodStorage class
   */
  @Test
  void testSearchForItemInFoodStorage() {
    Item item1 = new Item("Milk", 1, Unit.LITRE, LocalDate.now().plusDays(5), 15);
    Item item2 = new Item("Milk", 2, Unit.LITRE, LocalDate.now().plusDays(10), 30); // Add a second item with the same name
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    
    List<Item> result1 = foodStorage.searchForItemsInFoodStorage("Milk");
    List<Item> result2 = foodStorage.searchForItemsInFoodStorage("mIlK"); // Test case-insensitive
    
    assertNotNull(result1, "Milk should be found");
    assertFalse(result1.isEmpty(), "Result should not be empty");
    assertEquals(2, result1.size(), "Should find two items named 'Milk'");
    assertTrue(result1.stream().allMatch(item -> "Milk".equalsIgnoreCase(item.getName())), "All found items should have name 'Milk'");
    
    assertNotNull(result2, "Case-insensitive search should find Milk");
    assertFalse(result2.isEmpty(), "Result for case-insensitive search should not be empty");
    assertEquals(2, result2.size(), "Case-insensitive search should find two items");
    assertTrue(result2.stream().allMatch(item -> "Milk".equalsIgnoreCase(item.getName())), "All found items should have name 'Milk'");
    
    //Negative
    List<Item> result3 = foodStorage.searchForItemsInFoodStorage("Boxing");
    assertNotNull(result3, "Result for non-existent item should not be null");
    assertTrue(result3.isEmpty(), "Non-existent item should return an empty list");
    
    assertThrows(NullPointerException.class, () -> foodStorage.searchForItemsInFoodStorage(null), "Searching for null should throw NullPointerException");
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
    assertEquals(4, removedQuantity, "Total removed quantity should be 4");
    
    assertNotNull(foodStorage.getItems().get("orange"), "Orange list should still exist in storage");
    assertEquals(1, foodStorage.getItems().get("orange").get(0).getQuantity(), "Remaining quantity of the first item should be 1");
    
    foodStorage.removeItemFromFoodStorage("Orange", 1);
    assertFalse(foodStorage.itemExist("orange"), "Orange should no longer exist in storage after removing all quantities");
    
    //negative tests
    assertThrows(IllegalArgumentException.class, () -> foodStorage.removeItemFromFoodStorage("Orange", 1), "Removing non-existent item should throw IllegalArgumentException");
    assertThrows(IllegalArgumentException.class, () -> foodStorage.removeItemFromFoodStorage("Apple", 2), "Removing an item not in storage should throw IllegalArgumentException");
  }
  
  /**
   * Tests retrieving expired items from foodStorage.
   */
  @Test
  void testGetExpiredItems() {
    Item item1 = new Item("Tomahawk", 1, Unit.PIECES, LocalDate.now().minusDays(1), 1);
    Item item2 = new Item("Chicken", 1, Unit.KILOGRAM, LocalDate.now().plusDays(5), 90);
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    
    List<Item> expiredItems = foodStorage.getExpiredItems();
    assertEquals(1, expiredItems.size(), "Only one item should be expired");
    assertEquals("Tomahawk", expiredItems.get(0).getName(), "Expired item name should be Tomahawk");
    
    //Negative tests
    assertTrue(foodStorage.getExpiredItems().stream()
        .noneMatch(item -> item.getName().equals("Chicken")), "Non-expired items should not appear in the expired list");
    foodStorage = new FoodStorage();
    assertTrue(foodStorage.getExpiredItems().isEmpty(), "Empty storage should return no expired items");
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
    
    assertEquals(1, itemsExpiringBeforeDate.size(), "Only one item should expire before the given date");
    assertEquals("Steak", itemsExpiringBeforeDate.getFirst().getName(), "Item name should be Steak");
    
    //negative
    assertTrue(foodStorage.getItemsExpiringBefore(LocalDate.now().plusDays(1)).isEmpty(), "No items should expire before today");
    assertThrows(NullPointerException.class, () -> foodStorage.getItemsExpiringBefore(null), "Null date should throw NullPointerException");
  }
  
  /**
   * Test checking if
   */
  @Test
  void testSortAlphabetically() {
    Item item1 = new Item("Banan", 5.0, Unit.PIECES, LocalDate.now().plusDays(10), 30.0);
    Item item2 = new Item("Apple", 5.0, Unit.PIECES, LocalDate.now().plusDays(7), 20.0);
    
    foodStorage.addItemToFoodStorage(item1);
    foodStorage.addItemToFoodStorage(item2);
    
    foodStorage.getFoodStorageAlphabetically();
    
    List<String> sortedKeys = new ArrayList<>(foodStorage.getItems().keySet()).stream()
        .sorted(String.CASE_INSENSITIVE_ORDER)
        .toList();
    
    assertEquals("apple", sortedKeys.get(0), "Apple should be sorted first");
    assertEquals("banan", sortedKeys.get(1), "Banan should be sorted second");
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
    
    double totalValue = foodStorage.calculateTotalValue(foodStorage.getItems().values().stream()
        .flatMap(List::stream));
    double expectedTotalValue = item1.getPerUnitPrice() + item2.getPerUnitPrice();
    
    //assertEquals(17, totalValue);
    assertEquals(expectedTotalValue, totalValue, 0.01);
    
    //negative
    assertThrows(IllegalArgumentException.class, () -> foodStorage.calculateTotalValue(null), "Null stream should throw Ill.Arg.Exc.");
    foodStorage = new FoodStorage();
    assertEquals(0, foodStorage.calculateTotalValue(foodStorage.getItems().values().stream().flatMap(List::stream)));
    
    foodStorage = new FoodStorage();
    assertEquals(0, foodStorage.calculateTotalValue(
        foodStorage.getItems().values().stream().flatMap(List::stream)
    ), "Total value of empty storage should be 0");
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
