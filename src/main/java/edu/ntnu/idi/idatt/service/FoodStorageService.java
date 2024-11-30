package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.Utils.UserInputHandler;
import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.storage.FoodStorage;

import java.time.LocalDate;
import java.util.List;

public class FoodStorageService {
  private FoodStorage foodStorage;
  private UserInputHandler inputHandler;
  
  public FoodStorageService(FoodStorage foodStorage, UserInputHandler inputHandler) {
    this.foodStorage = foodStorage;
    this.inputHandler = inputHandler;
  }
  
  public void handleAddItem() {
    try {
      final String name = inputHandler.getValidatedString("Enter item name:", "Item name cannot be empty/blank", "name");
      double quantity = inputHandler.getValidatedDouble("Enter quantity:", "Invalid input for quantity", "quantity");
      final Unit unit = inputHandler.getValidatedUnit("Enter unit (kg, g, L, mL, pcs):", "Invalid unit");
      final LocalDate expirationDate = inputHandler.getValidatedDate("Enter a date in the format yyyy-mm-dd", "Please enter a date in the format yyyy-mm-dd");
      final double pricePerUnit = inputHandler.getValidatedDouble("Enter price per unit:", "Invalid input for price", "price");
      
      Item item = new Item(name, quantity, unit, expirationDate, pricePerUnit);
      foodStorage.addItemToFoodStorage(item);
      
      System.out.println("Item added: " + item);
      
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
  
  public void handleSearchItem() {
    try {
      String name = inputHandler.getValidatedString("Enter item name:", "Item name cannot be empty/blank", "name");
      
      if (foodStorage == null) {
        throw new IllegalArgumentException("Food storage is not initialized.");
      }
      
      List<Item> searchStatus = foodStorage.searchForItemsInFoodStorage(name.toLowerCase());
      if (searchStatus != null) {
        System.out.println("Item found: ");
        searchStatus.forEach(item -> System.out.println(" - " + item));
      } else {
        System.out.println("Item does not exist.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid input: " + e.getMessage());
    }
  }
  
  public void handleRemoveItem() {
    try {
      String name = inputHandler.getValidatedString(
          "Enter the name of the item to be removed from the food storage: ",
          "Item name cannot be empty/blank", "name"
      ).toLowerCase();
      
      List<Item> matchingItems = foodStorage.searchForItemsInFoodStorage(name);
      if (matchingItems == null || matchingItems.isEmpty()) {
        throw new IllegalArgumentException("Item " + name + " does not exist.");
      }
      double totalQuantity = matchingItems.stream()
          .mapToDouble(Item::getQuantity)
          .sum();
      
      double quantity = inputHandler.getValidatedDouble(
          "Enter how much quantity of the item: " + matchingItems.getFirst().getName()
              + "(" + totalQuantity + matchingItems.getFirst().getUnit().getSymbol()
              + ") to be removed: ", "Invalid quantity", "quantity"
      );
      
      double removedQuantity = foodStorage.removeItemFromFoodStorage(name, quantity);
      
      System.out.println("Removed " + removedQuantity + matchingItems.getFirst().getUnit().getSymbol() + " from " + matchingItems.getFirst().getName());
      
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
  
  public void handleDisplayExpiredItems() {
    /*getExpiredItems method are being called from the foodStorage class
    And the result is being assigned to the expiredItems variable*/
    try {
      List<Item> expiredItems = foodStorage.getExpiredItems();
      
      if (expiredItems == null || expiredItems.isEmpty()) {
        System.out.println("No items is expired! Nice!");
        return;
      }
      
      double totalValue = foodStorage.calculateTotalValue(expiredItems.stream());
      
      System.out.println("Expired items");
      expiredItems.forEach(item -> System.out.println("- " + item));
      System.out.printf("Total value of expired items: %.2f kr%n", totalValue); //2 desimaler
    } catch (IllegalArgumentException e) {
      System.out.println("Error while trying to display expired items: " + e.getMessage());
    }
  }
  
  public void handleTotalValue() {
    try {
      if (foodStorage == null || foodStorage.isEmpty()) {
        System.out.println("Food storage is empty");
        return;
      }
      double totalValue = foodStorage.calculateTotalValue(foodStorage.getItems().values().stream()
          .flatMap(List::stream));
      
      System.out.println("The total value of the food storage is: " + totalValue + " kr");
    } catch (IllegalArgumentException e) {
      System.out.println("Error while getting the total value of food storage: " + e.getMessage());
    }
  }
  
  public void handleViewItemsBeforeDate() {
    LocalDate date = inputHandler.getValidatedDate("Enter a date (yyyy-mm-dd) to view items expiring before it: ", "Please enter a date in the format yyyy-mm-dd");
    try {
      List<Item> itemsBeforeDate = foodStorage.getItemsExpiringBefore(date);
      if (itemsBeforeDate == null || itemsBeforeDate.isEmpty()) {
        System.out.println("No items expire before: " + date);
      } else {
        System.out.println("----Items expiring before date: " + date + "----");
        //itemsBeforeDate.forEach(System.out::println);
        itemsBeforeDate.forEach(item -> System.out.println("- " + item));
        
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error while retrieving items: " + e.getMessage());
    }
  }
  
  public void handleDisplayFoodStorageAlphabetically() {
    System.out.println("----Food storage sorted out alphabetically by name----");
    if (foodStorage == null || foodStorage.isEmpty()) {
      System.out.println("Food storage is empty. These is not items in food storage.");
      return;
    }
    try {
      foodStorage.getFoodStorageAlphabetically();
    } catch (IllegalArgumentException e) {
      System.out.println("Error while displaying the food storage from a-z: " + e.getMessage());
    }
  }
}
