package edu.ntnu.idi.idatt;

import java.time.LocalDate;

/**
 * attributtes are the outer layer classes: CookingBook and FoodStorage
 */
public class UI {
    private FoodStorage foodStorage;
    private Cookbook cookingBook;

    /**
     *Initalizes cookingbook and foodStorage
     */
    public void init(){
        foodStorage = new FoodStorage();
        cookingBook = new Cookbook();
    }
    public void start(){
        new Item("Mel", 2000, "gram", LocalDate.of(2028, 2, 19), 30);
        new Item("Melk", 100, "Milliliter", LocalDate.of(2024, 12, 15), 30);
        new Item("Egg", 10, "Stykker", LocalDate.of(2025, 12, 24), 3);
        new Item("Margarin", 1000, "gram", LocalDate.of(2025, 12, 24), 30);
        new Item("Sukker", 3000, "gram", LocalDate.of(2025, 12, 24), 40);
        //expired items
        new Item("Eple", 2, "Stykker", LocalDate.of(2000, 12, 15), 20);
        new Item("Appelsin", 5, "Stykker", LocalDate.of(1900, 12, 15), 20);

    }
}
