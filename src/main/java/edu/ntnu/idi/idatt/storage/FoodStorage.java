package edu.ntnu.idi.idatt.storage;

import edu.ntnu.idi.idatt.model.Ingredient;
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
 * Name, getName, as key, and Ingredient object as the value.
 *
 * @author Tri Tac Le
 * @since
 */
public class FoodStorage {
  //private HashMap<String, Ingredient> items;
  private final HashMap<String, ArrayList<Ingredient>> items;
  
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
  public HashMap<String, ArrayList<Ingredient>> getIngredients() {
    return items;
  }
  
  /**
   * A method that adds a item to the storage.
   * If the item already exist (check by name) the quantity increases with the quantity of the added item.
   * If not it is getting added to the map
   *
   * @param item
   */
  public void addIngredientToFoodStorage(Ingredient item) {
    // Update or make a new arrayList for item arguments name
    items.putIfAbsent(item.getName().toLowerCase(), new ArrayList<>());
    ArrayList<Ingredient> itemArrayList = items.get(item.getName().toLowerCase());
  
    /*check if item and its attributes already exist
    Checks the attributes pricePerUnit, expirationDate and unit
     */
    Optional<Ingredient> matchingIngredient = itemArrayList.stream()
        .filter(existingIngredient ->
            existingIngredient.getExpirationDate().equals(item.getExpirationDate())
                && existingIngredient.getPrice() == item.getPrice())
        .findFirst();
    
    if (matchingIngredient.isPresent()) {
      matchingIngredient.get().updateQuantity(item.getQuantity());
    } else {
      itemArrayList.add(item);
    }
  }
  
  public boolean itemExist(String name) {
    return items.containsKey(name);
  }
  
  /**
   * @param nameIngredient that represent the name attribute of the item. Can be called with getName
   *                       It also represents the key for the map
   */
  public List<Ingredient> searchForIngredientsInFoodStorage(String nameIngredient) {
    if (nameIngredient == null) {
      throw new NullPointerException("Ingredient name cannot be null");
    }
    return items.entrySet().stream()
        //.filter(entry -> entry.getKey().toLowerCase().equals(nameIngredient.toLowerCase()))
        .filter(entry -> entry.getKey().equalsIgnoreCase(nameIngredient))
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
  public double removeIngredientFromFoodStorage(String name, double quantity) {
    name = name.toLowerCase();
    ArrayList<Ingredient> itemArrayList = items.get(name);
    
    if (itemArrayList == null || itemArrayList.isEmpty()) {
      throw new IllegalArgumentException("Ingredient does not exist in storage.");
    }
    
    List<Ingredient> sortedList = getSorteIngredientsByExpirationsDate(itemArrayList);
    
    double totalQuantity = sortedList.stream()
        .mapToDouble(Ingredient::getQuantity)
        .sum();
    
    if (quantity > totalQuantity) {
      throw new IllegalArgumentException("Invalid quantity to remove: " + quantity + ". Available: " + totalQuantity);
    }
    
    double initialQuantity = quantity;
    Iterator<Ingredient> iterator = sortedList.iterator();
    
    while (iterator.hasNext() && quantity > 0) {
      Ingredient item = iterator.next();
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
  
  
  public List<Ingredient> getSorteIngredientsByExpirationsDate(List<Ingredient> itemArrayList) {
    return itemArrayList.stream()
        .filter(item -> item.getExpirationDate() != null) //Avoid nullpoointer when using comparator
        .sorted(Comparator.comparing(Ingredient::getExpirationDate))
        .collect(Collectors.toList());
  }
  
  /**
   * prints out all expired items plus how much it costs
   *
   * @return
   */
  public List<Ingredient> getExpiredIngredients() {
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
  public double calculateTotalValue(Stream<Ingredient> itemsStream) {
    if (itemsStream == null) {
      throw new IllegalArgumentException("Ingredients stream cannot be null");
    }
    return itemsStream
        //.mapToDouble(item -> item.getQuantity() * item.getPerUnitPrice()) // Uncomment if quantity is relevant
        .mapToDouble(Ingredient::getPrice)
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
  public List<Ingredient> getIngredientsExpiringBefore(LocalDate date) {
    return items.values().stream()
        .flatMap(List::stream)
        .filter(item -> item.getExpirationDate().isBefore(date))
        .sorted(Comparator.comparing(Ingredient::getExpirationDate))
        .collect(Collectors.toList());
  }
  
  public List<Ingredient> getIngredientsExpiringAfter(LocalDate date) {
    return items.values().stream()
        .flatMap(List::stream)
        .filter(item -> item.getExpirationDate().isAfter(date))
        .sorted(Comparator.comparing(Ingredient::getExpirationDate))
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
              .mapToDouble(Ingredient::getQuantity)
              .sum();
          if (items.get(key).size() > 1) {
            System.out.println(items.get(key).getFirst().getName() + " (Quantity: " + quantity + "):");
            items.get(key).forEach(item -> System.out.println("- " + item));
          } else {
            System.out.println(items.get(key).getFirst());
          }
        });
  }
  
  public boolean hasEnoughIngredientsForRecipe(FoodStorage foodStorage, Recipe recipe) {
    return recipe.getIngredientsList().stream()
        .allMatch(recipeIngredient -> {
          double totalAvailable = foodStorage.getIngredients().values().stream()
              .flatMap(List::stream)
              .filter(storageIngredient -> storageIngredient.getName().equals(recipeIngredient.getName()))
              .mapToDouble(storageIngredient -> {
                if (!storageIngredient.getUnit().equals(recipeIngredient.getUnit())) {
                  return storageIngredient.getUnit().convertValue(storageIngredient.getQuantity(), recipeIngredient.getUnit());
                }
                return storageIngredient.getQuantity();
              })
              .sum();
          return totalAvailable >= recipeIngredient.getQuantity();
        });
  }
  
  public Map<String, Double> getIngredientAvailabilityForRecipe(FoodStorage foodStorage, Recipe recipe) {
    Map<String, Double> availability = new HashMap<>();
    recipe.getIngredientsList().forEach(recipeIngredient -> {
      double totalAvailable = foodStorage.getIngredients().values().stream()
          .flatMap(List::stream)
          .filter(storageIngredient -> storageIngredient.getName().equals(recipeIngredient.getName()))
          .mapToDouble(Ingredient::getQuantity)
          .sum();
      availability.put(recipeIngredient.getName(), totalAvailable);
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
    stringBuilder.append("Ingredients in storage:\n");
    
    for (Map.Entry<String, ArrayList<Ingredient>> entry : items.entrySet()) {
      //stringBuilder.append(entry.getKey()).append(":\n").append(entry.getValue().toString()).append("\n");
      
      entry.getValue().forEach(item -> stringBuilder.append("  - ").append(item).append("\n"));
    }
    return stringBuilder.toString();
  }
}

