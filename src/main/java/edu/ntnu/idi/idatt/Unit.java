package edu.ntnu.idi.idatt;

public enum Unit {
  GRAM("g", 0.001),
  KILOGRAM("kg" 1.0),
  LITRE("L", 1.0),
  MILLILITRE("mL", 0.001),
  PIECEs("pcs", 1.0);

  
  private final String symbol;
  
  Unit(String symbol){
    this.symbol = symbol;
  }
}
