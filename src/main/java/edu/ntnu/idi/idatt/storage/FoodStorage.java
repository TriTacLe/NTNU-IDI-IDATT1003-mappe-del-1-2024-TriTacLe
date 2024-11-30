package edu.ntnu.idi.idatt.storage;

import edu.ntnu.idi.idatt.model.Item;
import edu.ntnu.idi.idatt.model.Recipe;

import java.time.LocalDate;
//import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.Iterator;
import java.util.Objects;
import java.util.Comparator;
import java.util.Map;

/**
 * The FoodStorage class manages a collection of items,
 * allowing operations such as....
 *
 * <p>p>
 * Name, getName, as key, and Item object as the value.
 *
 * @author Tri Tac Le
 * @since
 */
public class FoodStorage {
  //private HashMap<String, Item> items;
  private final HashMap<String, ArrayList<Item>> items;
  
  /**
   * Constructor.
   */
  public FoodStorage() {
    this.items = new HashMap<>();
  }
  
  /**
   * getter
   *
   * @return items hastmap
   */
  public HashMap<String, ArrayList<Item>> getItems() {
    return items;
  }
  
  /**
   * A method that adds a item to the storage.
   * If the item already exist (check by name) the quantity increases with the quantity of the added item.
   * If not it is getting added to the map
   *
   * @param item
   */
  public void addItemToFoodStorage(Item item) {
    // Update or make a new arrayList for item arguments name
    items.putIfAbsent(item.getName().toLowerCase(), new ArrayList<>());
    ArrayList<Item> itemArrayList = items.get(item.getName().toLowerCase());
  
    /*check if item and its attributes already exist
    Checks the attributes pricePerUnit, expirationDate and unit
     */
    Optional<Item> matchingItem = itemArrayList.stream()
        .filter(existingItem ->
            existingItem.getExpirationDate().equals(item.getExpirationDate())
                && existingItem.getPerUnitPrice() == item.getPerUnitPrice())
        .findFirst();
    
    if (matchingItem.isPresent()) {
      matchingItem.get().updateQuantity(item.getQuantity());
    } else {
      itemArrayList.add(item);
    }
  }
  
  public boolean itemExist(String name) {
    return items.containsKey(name);
  }
  
  /**
   * @param nameItem that represent the name attribute of the item. Can be called with getName
   *                 It also represents the key for the map
   */
  public List<Item> searchForItemsInFoodStorage(String nameItem) {
    if (nameItem == null) {
      throw new NullPointerException("Item name cannot be null");
    }
    return items.entrySet().stream()
        //.filter(entry -> entry.getKey().toLowerCase().equals(nameItem.toLowerCase()))
        .filter(entry -> entry.getKey().equalsIgnoreCase(nameItem))
        .flatMap(entry -> entry.getValue().stream())
        .collect(Collectors.toList());
//        .findAny()
//        .orElse(null);
  }
  
  
  /**
   * If the item exists in the map and if the item's quantity is greater than the amount to remove
   * Reduce the item's quantity
   * If the quantity is less than or equal to the amount to remove, remove the item from the map.
   *
   * @param name     name of the item
   * @param quantity quantity of the item
   */
  public double removeItemFromFoodStorage(String name, double quantity) {
    name = name.toLowerCase();
    ArrayList<Item> itemArrayList = items.get(name);
    
    if (itemArrayList == null || itemArrayList.isEmpty()) {
      throw new IllegalArgumentException("Item does not exist in storage.");
    }
    
    List<Item> sortedList = getSorteItemsByExpirationsDate(itemArrayList);
    
    double totalQuantity = sortedList.stream()
        .mapToDouble(Item::getQuantity)
        .sum();
    
    if (quantity > totalQuantity) {
      throw new IllegalArgumentException("Invalid quantity to remove: " + quantity + ". Available: " + totalQuantity);
    }
    
    
    double initialQuantity = quantity;
    Iterator<Item> iterator = sortedList.iterator();
    
    while (iterator.hasNext() && quantity > 0) {
      Item item = iterator.next();
      if (item.getQuantity() > quantity) {
        item.setQuantity(item.getQuantity() - quantity);
        quantity = 0;
      } else {
        quantity -= item.getQuantity();
        iterator.remove();
        //itemArrayList.remove(item);
      }
    }
    itemArrayList.clear();
    itemArrayList.addAll(sortedList);
    
    if (itemArrayList.isEmpty()) {
      items.remove(name);
    }
    return initialQuantity - quantity;
  }
  
  
  public List<Item> getSorteItemsByExpirationsDate(List<Item> itemArrayList) {
    return itemArrayList.stream()
        .filter(item -> item.getExpirationDate() != null) //Avoid nullpoointer when using comparator
        .sorted(Comparator.comparing(Item::getExpirationDate))
        .collect(Collectors.toList());
  }
  
  /**
   * prints out all expired items plus how much it costs
   *
   * @return
   */
  public List<Item> getExpiredItems() {
    return items.values().stream()
        .filter(Objects::nonNull)
        .flatMap(List::stream) //Flatten nested ArrayList into stream
        .filter(item -> item != null && item.getExpirationDate() != null)
        .filter(item -> item.getExpirationDate().isBefore(LocalDate.now()))
        .collect(Collectors.toList());
  }
  
  /**
   * Calculates the total value of items provided in the stream.
   *
   * @param itemsStream Stream of items to calculate the total value for.
   * @return Total value of the items.
   */
  public double calculateTotalValue(Stream<Item> itemsStream) {
    if (itemsStream == null) {
      throw new IllegalArgumentException("Items stream cannot be null");
    }
    return itemsStream
        //.mapToDouble(item -> item.getQuantity() * item.getPerUnitPrice()) // Uncomment if quantity is relevant
        .mapToDouble(Item::getPerUnitPrice)
        .sum();
  }
  
  /**
   * Method that checks if items hashmap is empty.
   *
   * @return items.isEmpty a bolean (true/false) value after checking if items is empty
   */
  public boolean isEmpty() {
    return items.isEmpty();
  }
  
  /**
   * Using streams to find every item with a expirationdate before input date-
   *
   * @param date items date
   */
  public List<Item> getItemsExpiringBefore(LocalDate date) {
    return items.values().stream()
        .flatMap(List::stream)
        .filter(item -> item.getExpirationDate().isBefore(date))
        .sorted(Comparator.comparing(Item::getExpirationDate))
        .collect(Collectors.toList());
  }
  
  public List<Item> getItemsExpiringAfter(LocalDate date) {
    return items.values().stream()
        .flatMap(List::stream)
        .filter(item -> item.getExpirationDate().isAfter(date))
        .sorted(Comparator.comparing(Item::getExpirationDate))
        .collect(Collectors.toList());
  }
  
  /**
   * Method that sorts out the items map alphabetically by name using streams sorted method
   */
  public void getFoodStorageAlphabetically() {
    items.keySet().stream()
        .sorted(String.CASE_INSENSITIVE_ORDER) //sort key alphabetically case in-sensitive
        .forEach(key -> {
          double quantity = items.get(key).stream()
              .mapToDouble(Item::getQuantity)
              .sum();
          if (items.get(key).size() > 1) {
            System.out.println(items.get(key).getFirst().getName() + " (Quantity: " + quantity + "):");
            items.get(key).forEach(item -> System.out.println("- " + item));
          } else {
            System.out.println(items.get(key).getFirst());
          }
        });
  }
  
  public boolean hasEnoughItemsForRecipe(FoodStorage foodStorage, Recipe recipe) {
    return recipe.getItemsList().stream()
        .allMatch(recipeItem -> {
          double totalAvailable = foodStorage.getItems().values().stream()
              .flatMap(List::stream)
              .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
              .mapToDouble(storageItem -> {
                if (!storageItem.getUnit().equals(recipeItem.getUnit())) {
                  return storageItem.getUnit().converter(storageItem.getQuantity(), recipeItem.getUnit());
                }
                return storageItem.getQuantity();
              })
              .sum();
          return totalAvailable >= recipeItem.getQuantity();
        });
  }
  
  public Map<String, Double> getItemAvailabilityForRecipe(FoodStorage foodStorage, Recipe recipe) {
    Map<String, Double> availability = new HashMap<>();
    recipe.getItemsList().forEach(recipeItem -> {
      double totalAvailable = foodStorage.getItems().values().stream()
          .flatMap(List::stream)
          .filter(storageItem -> storageItem.getName().equals(recipeItem.getName()))
          .mapToDouble(Item::getQuantity)
          .sum();
      availability.put(recipeItem.getName(), totalAvailable);
    });
    return availability;
  }
  
  /**
   * returns every items in items map, food storage.
   *
   * @return toString
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(); //Saves all
    stringBuilder.append("Items in storage:\n");
    
    for (Map.Entry<String, ArrayList<Item>> entry : items.entrySet()) {
      //stringBuilder.append(entry.getKey()).append(":\n").append(entry.getValue().toString()).append("\n");
      
      entry.getValue().forEach(item -> stringBuilder.append("  - ").append(item).append("\n"));
    }
    return stringBuilder.toString();
  }
}

