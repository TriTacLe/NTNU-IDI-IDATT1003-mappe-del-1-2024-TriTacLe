package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.Utils.InputValidation;

import java.time.LocalDate;

/**
 * Class represent an item/grocery/ingredient
 * Consist of attributes name, quantity, unit for the quantity, expiration date and the price per unit
 */
public class Item {
  // Attributter
  private final String name;
  private final String nameLowercase;
  private double quantity;
  private final Unit unit;
  private LocalDate expirationDate;
  private final double pricePerUnit;
  private InputValidation inputValidation;
  
  /**
   * konstruktør to make an item (object) with (attributes) name, quantity, unit, expirations date price per unit
   * I also implemented input control
   *
   * @param name           items name
   * @param quantity       quanity of item
   * @param unit           unit for the quantity (SI-unit) ex: Kg
   * @param expirationDate expiration date for the item
   * @param pricePerUnit   price per unit of item
   */
  public Item(String name, double quantity, Unit unit, LocalDate expirationDate, double pricePerUnit) {
    //Input validering
    inputValidation.validateString(name, "Name");
    inputValidation.validateDouble(quantity, "Quantity");
    inputValidation.validationEnum(unit);
    //inputValidation.validateDateNotInPast(expirationDate, true, "Expiration date");
    inputValidation.validateDouble(pricePerUnit, "Price");
    
    this.name = name;
    this.nameLowercase = name.toLowerCase();
    this.quantity = quantity;
    this.unit = unit;
    this.expirationDate = expirationDate;
    this.pricePerUnit = pricePerUnit;
  }
  
  public Item(String name, double quantity, Unit unit, double pricePerUnit) {
    inputValidation.validateString(name, "Name");
    inputValidation.validateDouble(quantity, "Quantity");
    inputValidation.validationEnum(unit);
    inputValidation.validateDouble(pricePerUnit, "Price");
    
    this.name = name;
    this.nameLowercase = name.toLowerCase();
    this.quantity = quantity;
    this.unit = unit;
    this.pricePerUnit = pricePerUnit;
  }
  
  /***
   * Gets the name of the item
   * @return name of the item
   */
  public String getName() {
    return name;
  }
  
  /***
   * Gets the quantity of the item
   * @return quantity of the item
   */
  public double getQuantity() {
    return quantity;
  }
  
  /**
   * Gets the expiration date of the item
   *
   * @return new expiration date
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }
  
  /***
   * gets the unit of quantity
   * @return unit of quantity
   */
  public Unit getUnit() {
    return unit;
  }
  
  /***
   * gets the price of the item
   * @return
   */
  public double getPerUnitPrice() {
    return pricePerUnit;
  }
  
  /***
   * Sets the quantity of the item
   * @param quantity new quantity of the item
   */
  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }
  
  
  /**
   * Sets the expirations date of the item
   *
   * @param expirationDate
   */
  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }
  
  public void increaseQuantity(double quantity) {
    inputValidation.validateDouble(quantity, "Quantity");
    this.quantity += quantity;
  }
  
  
  /**
   * Skriver ut detaljene fra en vare (fra hint)
   *
   * @return detaljene
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    //if else forenklet
    String expirationDateOutput = (expirationDate != null) ? " Expires: " + expirationDate : "";
    return name + "(" + quantity + " " + unit.getSymbol() + ")" + expirationDateOutput + " Price: " + pricePerUnit + " kr"; // + ". Todays date: " + LocalDate.now();
  }
}