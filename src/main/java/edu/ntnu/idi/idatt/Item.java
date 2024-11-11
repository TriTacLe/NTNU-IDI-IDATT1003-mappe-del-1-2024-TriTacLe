package edu.ntnu.idi.idatt;

//import java.time.LocalDate;

import java.time.LocalDate;

/**
 * Class represent an item/grocery/ingredient
 * Consist of attributes name, quantity, unit for the quantity, expiration date and the price per unit
 */
public class Item {
    // Attributter
    private final String name;
    private double quantity;
    private final String unit;
    private LocalDate expirationDate;
    //private Date expirationDate;
    private double pricePerUnit;

    /**
     * konstruktør to make an item (object) with (attributes) name, quantity, unit, expirations date price per unit
     * I also implemented input control
     * @param name items name
     * @param quantity quanity of item
     * @param unit unit for the quantity (SI-unit) ex: Kg
     * @param expirationDate expiration date for the item
     * @param pricePerUnit price per unit of item
     */
    public Item (String name, double quantity, String unit, LocalDate expirationDate, double pricePerUnit) {
    //public Item (String name, double quantity, String unit, double pricePerUnit) {
        //Input validering
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if(unit == null || unit.isEmpty()) {
            throw new IllegalArgumentException("Unit cannot be empty");
        }
        /*
        if(expirationDate == null || expirationDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Best before date cannot be in the past");
        }
        */

        if(pricePerUnit < 0) {
            throw new IllegalArgumentException("Price per unit cannot be negative");
        }
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
        this.pricePerUnit = pricePerUnit;
    }
    /***
     * Gets the name of the item
     * @return name of the item
     */
    public String getName(){return name;}
    /***
     * Gets the quantity of the item
     * @return quantity of the item
     */
    public double getQuantity(){return quantity;}
    /**
     * Gets the expiration date of the item
     * @return new expiration date
     */
    public LocalDate getExpirationDate(){return expirationDate;}

    /***
     * gets the unit of quantity
     * @return unit of quantity
     */
    public String getUnit(){return unit;}
    /***
     * gets the price of the item
     * @return
     */
    public double getPerUnitPrice(){return pricePerUnit;}
    /***
     * Sets the quantity of the item
     * @param quantity new quantity of the item
     */
    public void setQuantity(double quantity){this.quantity = quantity;}
    /**
     * Sets the expirations date of the item
     * @param expirationDate
     */
    public void setExpirationDate(LocalDate expirationDate){this.expirationDate = expirationDate;}

    public void increaseQuantity(double quantity){
        if (quantity < 0){
            throw new IllegalArgumentException("INcreased quantity cannot be negative");
        } else {
            this.quantity += quantity;
        }
    }
    /**
     * Skriver ut detaljene fra en vare (fra hint)
     * @return detaljene
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return /*name*/ "(" + quantity + " " + unit + ") Expires: " + expirationDate + " Price: " + pricePerUnit;// + ". Todays date: " + LocalDate.now();
    }
}