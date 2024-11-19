package edu.ntnu.idi.idatt;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.*;

/**
 * Name, getName, as key, and Item object as the value
 */
public class FoodStorage {
//    private HashMap<String, Item> items;
    private HashMap<String, ArrayList<Item>> items;

    /**
     * Constructor
     */
    public FoodStorage(){
        this.items = new HashMap<>();}

    /**
     * getter
     * @return items hastmap
     */
    public HashMap<String, ArrayList<Item>> getItems() {
        return items;
    }

    /**
     * A method that adds a item to the storage.
     * If the item already exist (check by name) the quantity increases with the quantity of the added item.
     * If not it is getting added to the map
     * @param item
     */
    public void addItem(Item item) {
        // Update or make a new arrayList for item arguments name
        items.putIfAbsent(item.getName(), new ArrayList<>());
        ArrayList<Item> itemArrayList = items.get(item.getName());

        /*check if item and its attributes already exist
        Checks the attributes pricePerUnit and expirationDate
         */
        Optional<Item> matchingItem = itemArrayList.stream()
                .filter(existingItem ->
                        //existingItem.getUnit().equals(item.getUnit()) &&
                        existingItem.getExpirationDate().equals(item.getExpirationDate()) &&
                        existingItem.getPerUnitPrice() == item.getPerUnitPrice())
                .findFirst();

        if (matchingItem.isPresent()){
            matchingItem.get().increaseQuantity(item.getQuantity());
        } else {
            itemArrayList.add(item);
        }
        /*
        items.entrySet().stream()
                .filter(itemEntry -> itemEntry.getKey().equals(item.getName()))
                .findFirst()
                .ifPresentOrElse(
                        // If found, increase the quantity with increaseQUantity method of the existing item
                        entry -> entry.getValue().increaseQuantity(item.getQuantity()),
                        // If not found, add the item to the map with put method
                        () -> items.put(item.getName(), item)
                );

         */
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
        if (!items.containsKey(name)){
            System.out.println(name + " does not exist in the food storage");
        }

        ArrayList<Item> itemArrayList = items.get(name);

        //filter the list to exclude expired items and sort by expiration date
        List<Item> nonExpiredItems = itemArrayList.stream()
                .filter(item -> !item.getExpirationDate().isBefore(LocalDate.now())) //Exclude expired items
                .sorted(Comparator.comparing(Item::getExpirationDate)) //Sort by expiration date
                .collect(Collectors.toList());

        if (nonExpiredItems.isEmpty()){
            System.out.println("No non-expired items of " + name);
        }

    }

    /**
     * prints out all expired items plus how much it costs
     * @return
     */
    public void getExpiredItems(){
        //ArrayList<Item> expiredItems = new ArrayList<Item>();
        //double totalValue = 0;
        List<Item> expiredItems = items.values().stream()
                .filter(item -> item.getExpirationDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        if (expiredItems.isEmpty()){
            System.out.println("No items is expired! Nice!");
            return;
        }

        double totalValue = expiredItems.stream()
                //.filter(item -> item.getExpirationDate().isBefore(LocalDate.now()))
                .mapToDouble(Item::getPerUnitPrice)
                .sum();
        System.out.println("Expired items");
        expiredItems.forEach(System.out::println);
        System.out.printf("Total cost of expired items: %.2f kr%n", totalValue); //2 desimaler

        /*
        for (Item item : items.values()) {
            if (item.getExpirationDate().isBefore(LocalDate.now())){
                expiredItems.add(item);
                System.out.println("Expired items: " + item.getQuantity() + " " + item.getUnit() + " " + item.getName() + ": " + item.getExpirationDate());
                System.out.println("Expired items: " + item);
                totalValue += item.getPerUnitPrice();
            }
        }
        System.out.println("Total cost for the expired items: " + totalValue + " kr");
        return expiredItems;
         */
    }

    /**
     * Using streams to find every item with a expirationdate before input date
     * @param date items date
     */
    public void getExpiredItemsBeforeDate(LocalDate date){
        System.out.println("Every item with expiration date before: " + date);
        List<Item> expiredItems = items.values().stream()
                .filter(item -> item.getExpirationDate().isBefore(date))
                .collect(Collectors.toList());
        expiredItems.forEach(System.out::println);
    }

    /**
     * Prints out total value. Checks if storage is empty
     */
    public void totalValue(){
        if(items.isEmpty()){
            System.out.println("Food storage is empty");
            return;
        }
        double totalValue = items.values().stream()
                .mapToDouble(Item::getPerUnitPrice)
                .sum();
        System.out.println("The total value of the food storage is: " + totalValue + " kr");
    }

    /**
     * Method that sorts out the items map alphabetically by name using streams sorted method
     */
    public void sortAlphabetically(){
        System.out.println("Food storage sorted out alphabetically by name:");
        items.values().stream()
                .sorted(Comparator.comparing(Item::getName))
                .forEach(System.out::println);
        /*
        items.keySet().stream()
                .sorted(Comparator.comparing(String::toString))
                .forEach(System.out::println);
         */
    }

    /**
     * returns every items in items map, food storage
     * @return
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();//Saves all
        stringBuilder.append("Items in storage:\n");
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}

