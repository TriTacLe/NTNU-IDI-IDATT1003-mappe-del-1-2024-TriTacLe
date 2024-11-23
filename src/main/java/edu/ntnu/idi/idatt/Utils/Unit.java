package edu.ntnu.idi.idatt.Utils;

public enum Unit {
  //Mass units
  GRAM("g", UnitType.MASS, 0.001),
  KILOGRAM("kg", UnitType.MASS, 1.0),
  
  //Volum units
  LITRE("L", UnitType.VOLUME, 1.0),
  MILLILITRE("mL", UnitType.VOLUME, 0.001),
  
  //Count units
  PIECES("pcs", UnitType.NUMBER, 1.0);
  
  
  private final String symbol;
  private final UnitType type;
  private final Double conversionNumber;
  private InputValidation inputValidation;
  
  Unit(String symbol, UnitType type, Double conversionNumber) {
    this.symbol = symbol;
    this.type = type;
    this.conversionNumber = conversionNumber;
    this.inputValidation = inputValidation;
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
  
  public enum UnitType {
    MASS,
    VOLUME,
    NUMBER
  }
}
