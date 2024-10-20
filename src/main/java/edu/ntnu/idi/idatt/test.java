//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
import java.time.LocalDate;

public class test {
    public static void main(String[] args) {

        Fridge fridge = new Fridge();

        /*DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(userInput, dateFormat);    
        */
        
        fridge.addItem(new Item("Melk", 4, "liter", LocalDate.of(2024, 12, 15), 30));
        //fridge.addItem(new Item("Ost", 2, "Kg", 20));

        System.out.println("Dato idag " + LocalDate.now());

        //add
        System.out.print("Fridge: ");fridge.printFridge();

        //find
        System.out.println("Melk: " + fridge.findItem("Melk"));
        
        //remove item
        double quantity = 1;
        fridge.removeItem("Melk", quantity);
        System.err.print("Fridge etter å ha fjernet melk: "); fridge.printFridge();

        //total price
        System.err.println("Total pris: " + fridge.totalValue());
    }
}   
