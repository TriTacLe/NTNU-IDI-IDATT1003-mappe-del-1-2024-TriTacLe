//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
import java.time.LocalDate;


public class test {
    public static void main(String[] args) {
        // Get today's date
        LocalDate today = LocalDate.now();
        System.out.println("Today's date: " + today);  // Debugging output

        // Set best before date as 10 days from today
        LocalDate bestBeforeDate = today.plusDays(1200);
        System.out.println("Best before date: " + bestBeforeDate);  // Debugging output

        // Create a new Item object
        Item apple = new Item("Apple", 12, "pieces", bestBeforeDate, 3.0);

        // Output the object and its attributes
        System.out.println(apple);
    }
}   
