import java.util.ArrayList;
import java.util.List;

public class Fridge {
    private List<Item> items;

    public Fridge (){
        this.items = new ArrayList<>();
    }

    //metoder for å legge til varer
    public void addItem(Item item){  
        items.add(item);
    }
}   