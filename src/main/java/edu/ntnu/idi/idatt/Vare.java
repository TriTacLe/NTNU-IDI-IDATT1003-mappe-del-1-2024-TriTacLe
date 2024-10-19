import java.util.Date;

public class Vare {
    // Attributter
    private final String name;
    private double quantity;
    private final String unit;
    private Date bestBeforeDate;
    private double pricePerUnit;

    /**
     * konstruktør, med validering av inndata
     * @param name
     * @param quantity
     * @param unit
     * @param bestBeforDate
     * @param pricePerUnit
     */
    public Vare (String name, double quantity, String unit, Date bestBeforDate, double pricePerUnit) {
        if(name == null || name.isEmpty()) {throw new IllegalArgumentException("Navn kan ikke være tomt");}
        if(quantity < 0) {throw new IllegalArgumentException("Mengden kan ikke være tomt");}
        if(unit == null || unit.isEmpty()) {throw new IllegalArgumentException("Enheten kan ikke være tomt");}
        if(bestBeforDate.before(new Date())) {throw new IllegalArgumentException("best før datoen kan ikke være satt i fortiden");}
        if(pricePerUnit < 0) {throw new IllegalArgumentException("Prisen kan ikke være negativ");}  

        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.bestBeforeDate = bestBeforeDate;
        this.pricePerUnit = pricePerUnit;

    }
}