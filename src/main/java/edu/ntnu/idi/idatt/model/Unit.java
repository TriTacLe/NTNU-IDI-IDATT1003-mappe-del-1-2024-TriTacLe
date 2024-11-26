package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.Utils.InputValidation;

import java.util.Arrays;

public enum Unit {
  GRAM("g", UnitType.MASS, 0.001),
  KILOGRAM("kg", UnitType.MASS, 1.0),
  LITRE("L", UnitType.VOLUME, 1.0),
  DESILITRE("dL", UnitType.VOLUME, 0.1),
  MILLILITRE("mL", UnitType.VOLUME, 0.001),
  PIECES("pcs", UnitType.NUMBER, 1.0);
  
  private final String symbol;
  private final UnitType type;
  private final Double conversionNumber;
  private InputValidation inputValidation;
  private Item item;
  
  public enum UnitType {
    MASS,
    VOLUME,
    NUMBER
  }
  
  Unit(String symbol, UnitType type, Double conversionNumber) {
    this.symbol = symbol;
    this.type = type;
    this.conversionNumber = conversionNumber;
  }
  
  public String getSymbol() {
    return symbol;
  }
  
  public Double getConversionNumber() {
    return conversionNumber;
  }
  
  public Double converter(Double value, Unit targetUnit) {
    inputValidation.validationEnum(targetUnit);
    inputValidation.validationEnumUnitType(this.type, targetUnit.type);
    return (value * this.conversionNumber) / targetUnit.conversionNumber;
  }
  
  public Double convertToBaseUnit(Item item) {
    return item.getQuantity() * item.getUnit().getConversionNumber();
  }
  
  /**
   * Finds the Unit enum based on a user input string.
   *
   * @param input The string input by the user.
   * @return The corresponding Unit enum.
   * @throws IllegalArgumentException if the input does not match any Unit.
   */
  public static Unit fromSymbol(String input) {
    return Arrays.stream(Unit.values())
        .filter(unit -> unit.symbol.equalsIgnoreCase(input)) //ALlow case sensitive
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid unit: " + input));
  }
  
}
