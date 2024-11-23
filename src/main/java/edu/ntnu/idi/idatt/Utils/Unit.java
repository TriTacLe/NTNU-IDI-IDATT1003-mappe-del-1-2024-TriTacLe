package edu.ntnu.idi.idatt.Utils;

import edu.ntnu.idi.idatt.model.Item;

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
  
}
