//import java.time.LocalDate;
import java.util.Date;

public class Item {
    // Attributter
    private final String name;
    private double quantity;
    private final String unit;
    //private LocalDate expirationDate;
    private Date expirationDate;
    private double pricePerUnit;

    /**
     * konstruktør, med validering av inndata/input
     * @param name
     * @param quantity
     * @param unit
     * @param expirationDate
     * @param pricePerUnit
     */
    //public Item (String name, double quantity, String unit,/*  Date expirationDate */, double pricePerUnit) {
    public Item (String name, double quantity, String unit, double pricePerUnit) {
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
        /*if(expirationDate == null || expirationDate.isBefore(Date.now())) {
            throw new IllegalArgumentException("Best before date cannot be in the past");
        }
        */
        if(pricePerUnit < 0) {
            throw new IllegalArgumentException("Price per unit cannot be negative");
        }
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        //this.expirationDate = expirationDate;
        this.pricePerUnit = pricePerUnit;
    }
    //getters
    public String getName(){
        return name;
    }

    public double getQuantity(){
        return quantity;
    }

    //oppdaterer mengden
    public void setQuantity(double quantity){
        this.quantity = quantity;

    }

    public Date getExpirationDate(){
        return expirationDate;
    }

    public String getUnit(){
        return unit;
    }

    public double getPrice(){
        return pricePerUnit;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name + " " + quantity + " " + unit + " " + pricePerUnit;
        //return name + quantity + expirationDate + unit + pricePerUnit;
    }
    
}