package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.storage.FoodStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FoodStorageTest {
  
  private FoodStorage foodStorage;
  
  @BeforeEach
  void setUp() {
    foodStorage = new FoodStorage();
  }
  
  @Test
  void testAddItem() {
    Item apple = new Item("Apple", 5.0, "kg", LocalDate.now().plusDays(10), 20.0);
    foodStorage.addItemToFoodStorage(apple);
    
    ArrayList<Item> items = foodStorage.getItems().get("Apple");
    assertNotNull(items);
    assertEquals(1, items.size());
    assertEquals(apple, items.get(0));
  }
  
  @Test
  void testAddDuplicateItem() {
    Item apple1 = new Item("Apple", 3.0, "kg", LocalDate.now().plusDays(10), 20.0);
    Item apple2 = new Item("Apple", 2.0, "kg", LocalDate.now().plusDays(10), 20.0);
    
    foodStorage.addItemToFoodStorage(apple1);
    foodStorage.addItemToFoodStorage(apple2);
    
    ArrayList<Item> items = foodStorage.getItems().get("Apple");
    assertNotNull(items);
    assertEquals(1, items.size());
    assertEquals(5.0, items.get(0).getQuantity());
  }
  
  @Test
  void testRemoveItem() {
    Item milk = new Item("Milk", 10.0, "liters", LocalDate.now().plusDays(7), 15.0);
    foodStorage.addItemToFoodStorage(milk);
    
    foodStorage.removeItem("Milk", 5.0);
    
    ArrayList<Item> items = foodStorage.getItems().get("Milk");
    assertNotNull(items);
    assertEquals(1, items.size());
    assertEquals(5.0, items.get(0).getQuantity());
  }
  
  @Test
  void testRemoveMoreThanAvailableQuantity() {
    Item milk = new Item("Milk", 10.0, "liters", LocalDate.now().plusDays(7), 15.0);
    foodStorage.addItemToFoodStorage(milk);
    
    foodStorage.removeItem("Milk", 15.0);
    
    assertNull(foodStorage.getItems().get("Milk"));
  }
  
  @Test
  void testFindItemByName() {
    Item bread = new Item("Bread", 1.0, "kg", LocalDate.now().plusDays(5), 25.0);
    foodStorage.addItemToFoodStorage(bread);
    
    foodStorage.findItemByName("Bread");
    
    ArrayList<Item> items = foodStorage.getItems().get("Bread");
    assertNotNull(items);
    assertEquals(1, items.size());
  }
  
  @Test
  void testGetExpiredItems() {
    Item cheese = new Item("Cheese", 2.0, "kg", LocalDate.now().minusDays(1), 40.0);
    Item yogurt = new Item("Yogurt", 1.0, "liters", LocalDate.now().plusDays(10), 20.0);
    
    foodStorage.addItemToFoodStorage(cheese);
    foodStorage.addItemToFoodStorage(yogurt);
    
    foodStorage.getExpiredItems();
    
    assertTrue(foodStorage.getItems().get("Yogurt") != null);
    assertTrue(foodStorage.getItems().get("Cheese") != null);
  }
  
  @Test
  void testGetItemsExpirationDateBefore() {
    Item apple = new Item("Apple", 5.0, "kg", LocalDate.now().plusDays(3), 20.0);
    Item banana = new Item("Banana", 2.0, "kg", LocalDate.now().plusDays(5), 15.0);
    
    foodStorage.addItemToFoodStorage(apple);
    foodStorage.addItemToFoodStorage(banana);
    
    foodStorage.getItemsExpirationDateBefore(LocalDate.now().plusDays(4));
    
    assertNotNull(foodStorage.getItems().get("Apple"));
  }
  
  @Test
  void testSortAlphabetically() {
    Item orange = new Item("Orange", 10.0, "kg", LocalDate.now().plusDays(10), 30.0);
    Item apple = new Item("Apple", 5.0, "kg", LocalDate.now().plusDays(7), 20.0);
    
    foodStorage.addItemToFoodStorage(orange);
    foodStorage.addItemToFoodStorage(apple);
    
    foodStorage.sortAlphabetically();
    
    ArrayList<Item> appleItems = foodStorage.getItems().get("Apple");
    assertNotNull(appleItems);
    
    ArrayList<Item> orangeItems = foodStorage.getItems().get("Orange");
    assertNotNull(orangeItems);
  }
  
  @Test
  void testTotalValue() {
    Item rice = new Item("Rice", 10.0, "kg", LocalDate.now().plusDays(30), 50.0);
    Item wheat = new Item("Wheat", 5.0, "kg", LocalDate.now().plusDays(20), 40.0);
    
    foodStorage.addItemToFoodStorage(rice);
    foodStorage.addItemToFoodStorage(wheat);
    
    double expectedTotalValue = rice.getPerUnitPrice() + wheat.getPerUnitPrice();
    
    foodStorage.totalValue();
    
    assertEquals(expectedTotalValue, 90.0, 0.01);
  }
}
