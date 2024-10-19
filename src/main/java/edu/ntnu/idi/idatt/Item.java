import java.time.LocalDate;


public class Item {
    // Attributter
    private final String name;
    private double quantity;
    private final String unit;
    private LocalDate bestBeforeDate;
    private double pricePerUnit;

    /**
     * konstruktør, med validering av inndata/input
     * @param name
     * @param quantity
     * @param unit
     * @param bestBeforDate
     * @param pricePerUnit
     */
    public Item (String name, double quantity, String unit, LocalDate bestBeforDate, double pricePerUnit) {
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
        if(bestBeforeDate == null || bestBeforeDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Best before date cannot be in the past");
        }
        if(pricePerUnit < 0) {
            throw new IllegalArgumentException("Price per unit cannot be negative");
        }

        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.bestBeforeDate = bestBeforeDate;
        this.pricePerUnit = pricePerUnit;
    }
    
    @Override
    public String toString() {
        return "Item{name='" + name + "', quantity=" + quantity + ", unit='" + unit + "', bestBeforeDate=" + bestBeforeDate + ", pricePerUnit=" + pricePerUnit + '}';
    }
        
}