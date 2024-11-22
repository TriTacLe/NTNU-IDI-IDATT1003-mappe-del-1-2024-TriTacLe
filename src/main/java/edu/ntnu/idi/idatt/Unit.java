package edu.ntnu.idi.idatt;

public enum Unit {
  GRAM("g", 0.001),
  KILOGRAM("kg", 1.0),
  LITRE("L", 1.0),
  MILLILITRE("mL", 0.001),
  PIECES("pcs", 1.0);


private final String symbol;
private final Double conversionNumber;

Unit(String symbol, Double conversionNumber) {
  this.symbol = symbol;
  this.conversionNumber = conversionNumber;
}
  
}
