//import static org.junit.Assert.assertEquals;
//import org.junit.Test;


public class test {
    public static void main(String[] args) {

        Fridge fridge = new Fridge();

        fridge.addItem(new Item("Melk", 4, "liter", 30));
        fridge.addItem(new Item("Ost", 2, "Kg", 20));

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
