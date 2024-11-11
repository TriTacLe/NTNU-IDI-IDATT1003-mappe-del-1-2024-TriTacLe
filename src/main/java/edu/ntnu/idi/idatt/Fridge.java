package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
//gamle klassen, oppdaterte klassen er FoodStoragae
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
        boolean found = false;
        for (Item item1 : items){
            if (item.equals(item1)){
                item.increaseQuantity(item.getQuantity());
                found = true;
                return;
            }
        }
        if (!found){
            items.add(item);
        }
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
    public void removeItem(String name, double quantity) {
        Item item = findItem(name);
        //hvis ingrediens ikke er null, altså finnes
        if (item != null) {
            //antall ingrediens > enn antall ingrent ønsket fjernet
            if (item.getQuantity() > quantity) {
                item.setQuantity(item.getQuantity() - quantity);//hvis item quantity er null
            } else {
                items.remove((item));
            }
        } else{
            System.out.println("Varen finnes ikke");
        }
    }
    /**
    * //printer listen
    * @param
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