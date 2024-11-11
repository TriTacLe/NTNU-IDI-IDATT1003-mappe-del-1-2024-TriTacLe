package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;

public class Fridge {
    private ArrayList<Item> items;

    public Fridge (){
        this.items = new ArrayList<>();
    }

    //getter
    public ArrayList<Item> getItems(){
        return items;
    }

    //metoder for å legge til varer
    public void addItem(Item item){  
        items.add(item);
    }

    //finne en spesifikk vare
    public Item findItem(String name){
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
    /**
     * fjerne en spesifikk vare
     * @param name quantity
     */
    public void removeItem(String name, double quantity){
        Item item = findItem(name);
        //hvis ingrediens ikke er null og antall ingrediens > enn antall ingrent ønsket
        if (item != null){
            if (item.getQuantity() > quantity) {
               item.setQuantity(item.getQuantity() - quantity);
            } else {
                items.remove(item);
            } 
        } else {
            System.out.println("Varen finnes ikke");
        }
    }
    /**
    * //printer listen
    * @param item
    */
    public void printFridge(){
        System.out.println(items);
    }

    public ArrayList printExpiredItems(){
        ArrayList<Item> expiredItems = new ArrayList<Item>();
        double totalValue = 0;
        for (Item item : items) {
            if (item.getExpirationDate().isBefore(LocalDate.now())){
                expiredItems.add(item);
                System.out.println("Utgåtte varer: " + item.getQuantity() + " " + item.getUnit() + " " + item.getName() + ": " + item.getExpirationDate());
                totalValue += item.getPerUnitPrice();
            }
        }
        System.out.println("Kostnad for utgåttevarene: " + totalValue + " kr");
        return expiredItems;
    }

    public double totalValue(){
        double value = 0;
            for (Item item : items) {
                value += item.getPerUnitPrice();
            }
        return value;
    }
}   