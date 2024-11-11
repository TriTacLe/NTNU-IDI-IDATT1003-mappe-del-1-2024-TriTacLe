package edu.ntnu.idi.idatt;
import java.util.*;

/**
 * Name, getName, as key and Item object as the value
 */
public class FoodStorage {
    private HashMap<String, Item> items;

    /**
     * Constructor
     */
    public FoodStorage(){
        this.items = new HashMap<>();}

    /**
     * getter
     * @return items hastmap
     */
    public HashMap<String, Item> getItems(){return items;}

    /**
     * A method that adds a item to the storage.
     * If the item already exist (check by name) the quantity increases with the quantity of the added item.
     * If not it is getting added to the map
     * @param item
     */
    public void addItem(Item item) {
        // Check if the item already exists in the map by name, getName
        /*
        items.merge(
                item.getName(), // Key for the map entry, getName
                item,           // Value to add if the key (item) do not already exist in the map
                (existingItem, newItem) -> { // BiFunction to handle the case where the key already exists
                    // If the item with the same name (key) already exists, increase its quantity
                    existingItem.increaseQuantity(newItem.getQuantity());
                    // Return the existing item (with updated quantity) to keep it in the map
                    return existingItem;
                }
        );
         */
        items.entrySet().stream()
                .filter(itemEntry -> itemEntry.getKey().equals(item.getName()))
                .findFirst()
                .ifPresentOrElse(
                        // If found, increase the quantity with increaseQUantity method of the existing item
                        entry -> entry.getValue().increaseQuantity(item.getQuantity()),
                        // If not found, add the item to the map with put method
                        () -> items.put(item.getName(), item)
                );
    }
    /**
     * @param nameItem that represent the name attribute of the item. Can be called with getName
     * It also represents the key for the map
     */
    public void findItemByName(String nameItem) {
        if (items.containsKey(nameItem)){
            System.out.println("Item found: " + items.get(nameItem));
        } else {
            System.out.println("Item do not exist");
        }
    }

    /**
     * Updated!!
     * If the item exists in the map and if the item's quantity is greater than the amount to remove
     * Reduce the item's quantity
     * If the quantity is less than or equal to the amount to remove, remove the item from the map
     * @param name name of the item
     * @param quantity quantity of the item
     */
    public void removeItem(String name, double quantity) {
        Item item = items.get(name);
        if (item == null) {
            System.out.println(name + " does not exist in the food storage");
            return;
        }

        double newQuantity = item.getQuantity() - quantity;
        if (newQuantity > 0) {
            item.setQuantity(newQuantity);
            System.out.println("Quantity after " + quantity + " items removed: " + newQuantity);
        } else {
            items.remove(name);
            System.out.println(item.getName() + " is removed from food storage");
        }
    }

    public void printFoodStorage(){
        System.out.println(items);
    }


}

