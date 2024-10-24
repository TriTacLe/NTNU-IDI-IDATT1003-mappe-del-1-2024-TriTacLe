import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Fridge fridge = new Fridge();
        
        fridge.addItem(new Item("Mel", 2000, "gram", LocalDate.of(2028, 2, 19), 30));
        fridge.addItem(new Item("Melk", 100, "Milliliter", LocalDate.of(2024, 12, 15), 30));
        fridge.addItem(new Item("Egg", 10, "Stykker", LocalDate.of(2025, 12, 24), 3));
        fridge.addItem(new Item("Margarin", 1000, "gram", LocalDate.of(2025, 12, 24), 30));
        fridge.addItem(new Item("Sukker", 3000, "gram", LocalDate.of(2025, 12, 24), 40));
        fridge.addItem(new Item("Eple", 2, "Stykker", LocalDate.of(2000, 12, 15), 20)); // expired
        fridge.addItem(new Item("Appelsin", 5, "Stykker", LocalDate.of(1900, 12, 15), 20)); // expired

        Recipe vaffelRecipe = createVaffelRecipe();
        CookingBook cookingBook = new CookingBook();
        cookingBook.addRecipe(vaffelRecipe);

        boolean exit = false;

        while (!exit) {
            System.out.println("\nFridge Menu");
            System.out.println("1. Print fridge contents");
            System.out.println("2. Add item to fridge");
            System.out.println("3. Remove item from fridge");
            System.out.println("4. View expired items");
            System.out.println("5. View total value of items");
            System.out.println("6. View a recipe");
            System.out.println("7. View cooking book");
            System.out.println("8. Suggest recipe based on fridge contents");
            System.out.println("9. Exit");
            System.out.print("Choose an option (1-9): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); //consume newline

            switch (choice) {
                case 1:
                    System.out.println("Fridge Contents:");
                    fridge.printFridge();
                    break;
                case 2:
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    double quantity = scanner.nextDouble();
                    System.out.print("Enter unit: ");
                    String unit = scanner.next();
                    System.out.print("Enter expiration date (YYYY-MM-DD): ");
                    String dateInput = scanner.next();
                    LocalDate expirationDate = LocalDate.parse(dateInput);
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    fridge.addItem(new Item(name, quantity, unit, expirationDate, price));
                    System.out.println(name + " added to fridge.");
                    break;
                case 3:
                    System.out.print("Enter item name to remove: ");
                    String removeName = scanner.nextLine();
                    System.out.print("Enter quantity to remove: ");
                    double removeQuantity = scanner.nextDouble();
                    fridge.removeItem(removeName, removeQuantity);
                    System.out.println(removeQuantity + " " + removeName + " removed from fridge.");
                    break;
                case 4:
                    System.out.println("Expired Items:");
                    fridge.printExpiredItems();
                    break;
                case 5:
                    System.out.println("Total value of items in the fridge: " + fridge.totalValue() + " kr");
                    break;
                case 6:
                    System.out.println("Recipe: Vaffelrøre");
                    vaffelRecipe.printIngredientsRecipe();
                    vaffelRecipe.procedure();
                    break;
                case 7:
                    System.out.println("Cooking Book:");
                    cookingBook.printCookingBook();
                    break;
                case 8:
                    System.out.println("Suggested Recipes based on fridge contents:");
                    cookingBook.suggestionRecipe(fridge);
                    break;
                case 9:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 9.");
            }
        }
        
        scanner.close();
    }

    private static Recipe createVaffelRecipe() {
        String description = "\nVaffeloppskrift!!!!!!!: " +
        "\n1. Ha mel og sukker i en bolle." +
        "\n2. Spe med litt av melken om gangen." +
        "\n3. Rør inn egg og tilset."+
        "\n4. Stek vaflene i et vaffeljern.";

        Recipe vaffelRecipe = new Recipe("Vaffelrøre", description);
        vaffelRecipe.addItemForRecipe(new Item("Mel", 500, "gram", LocalDate.of(2028, 2, 19), 30));
        vaffelRecipe.addItemForRecipe(new Item("Melk", 40, "Milliliter", LocalDate.of(2024, 12, 12), 25));
        vaffelRecipe.addItemForRecipe(new Item("Egg", 3, "Stykker", LocalDate.of(2025, 5, 12), 3));
        vaffelRecipe.addItemForRecipe(new Item("Margarin", 100, "Gram", LocalDate.of(2025, 1, 20), 40));
        vaffelRecipe.addItemForRecipe(new Item("Sukker", 10, "gram", LocalDate.of(2027, 7, 4), 20));

        return vaffelRecipe;
    }
}
