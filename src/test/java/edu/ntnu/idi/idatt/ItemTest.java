package edu.ntnu.idi.idatt;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void shouldCreateItemWithValidAttributes() {
        Item item = new Item("Apple", 2.5, "Kg", LocalDate.now().plusDays(10), 3.5);
        assertEquals("Apple", item.getName());
        assertEquals(2.5, item.getQuantity());
        assertEquals("Kg", item.getUnit());
        assertEquals(LocalDate.now().plusDays(10), item.getExpirationDate());
        assertEquals(3.5, item.getPerUnitPrice());
    }

    @Test
    void shouldThrowExceptionForEmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item("", 2.5, "Kg", LocalDate.now().plusDays(10), 3.5);
        });
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNegativeQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item("Apple", -1.0, "Kg", LocalDate.now().plusDays(10), 3.5);
        });
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmptyUnit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item("Apple", 2.5, "", LocalDate.now().plusDays(10), 3.5);
        });
        assertEquals("Unit cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNegativePricePerUnit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item("Apple", 2.5, "Kg", LocalDate.now().plusDays(10), -3.5);
        });
        assertEquals("Price per unit cannot be negative", exception.getMessage());
    }

    @Test
    void shouldReturnCorrectQuantityAfterSetQuantity() {
        Item item = new Item("Apple", 2.5, "Kg", LocalDate.now().plusDays(10), 3.5);
        item.setQuantity(5.0);
        assertEquals(5.0, item.getQuantity());
    }

    @Test
    void getExpirationDateShouldReturnCorrectDate() {
        LocalDate expirationDate = LocalDate.now().plusDays(15);
        Item item = new Item("Milk", 1.0, "Liter", expirationDate, 1.5);
        assertEquals(expirationDate, item.getExpirationDate());
    }

    @Test
    void getUnitShouldReturnCorrectUnit() {
        Item item = new Item("Flour", 1.0, "Kg", LocalDate.now().plusDays(20), 0.8);
        assertEquals("Kg", item.getUnit());
    }

    @Test
    void getPerUnitPriceShouldReturnCorrectPrice() {
        Item item = new Item("Sugar", 3.0, "Kg", LocalDate.now().plusDays(20), 2.2);
        assertEquals(2.2, item.getPerUnitPrice());
    }

    @Test
    void testToStringShouldReturnFormattedString() {
        LocalDate expirationDate = LocalDate.of(2023, 12, 25);
        Item item = new Item("Butter", 0.5, "Kg", expirationDate, 4.0);
        String expectedString = "Butter 0.5 Kg 2023-12-25 4.0";
        assertEquals(expectedString, item.toString());
    }
}
