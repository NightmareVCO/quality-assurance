/*
    Vladimir Curiel - 10141415

    Cobertura mínima de pruebas:
        1.- Crear ShoppingCartItem con datos válidos.
        2.- Lanzar NullPointerException si el product es nulo.
        3.- Lanzar IllegalArgumentException si quantity ≤ 0 en el constructor.
        4.- setQuantity con valor negativo lanza IllegalArgumentException.
        5.- setQuantity con cero actualiza quantity a cero.
        6.- getSubtotal calcula correctamente el subtotal (precio × cantidad).
*/

import org.example.entities.Product;
import org.example.entities.ShoppingCartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartItemTest {

    @Test
    @DisplayName("1. Crear ShoppingCartItem válido y verificar getters.")
    public void testCreateValidShoppingCartItem() {
        Product product = new Product("P500", "Camisa", 20.0);
        ShoppingCartItem item = new ShoppingCartItem(product, 3);

        assertEquals(product, item.getProduct(), "El product debe coincidir con el pasado al constructor.");
        assertEquals(3, item.getQuantity(), "La cantidad debe ser 3.");
        assertEquals(60.0, item.getSubtotal(), "El subtotal debe ser precio × cantidad = 60.0.");
    }

    @Test
    @DisplayName("2. Crear ShoppingCartItem con product nulo lanza NullPointerException.")
    public void testCreateWithNullProduct() {
        assertThrows(
                NullPointerException.class,
                () -> new ShoppingCartItem(null, 2),
                "Se esperaba NullPointerException cuando el product es nulo."
        );
    }

    @Test
    @DisplayName("3a. Crear ShoppingCartItem con quantity cero lanza IllegalArgumentException.")
    public void testCreateWithZeroQuantity() {
        Product product = new Product("P501", "Pantalón", 30.0);
        assertThrows(
                IllegalArgumentException.class,
                () -> new ShoppingCartItem(product, 0),
                "Se esperaba IllegalArgumentException para quantity cero en el constructor."
        );
    }

    @Test
    @DisplayName("3b. Crear ShoppingCartItem con quantity negativa lanza IllegalArgumentException.")
    public void testCreateWithNegativeQuantity() {
        Product product = new Product("P502", "Zapatos", 50.0);
        assertThrows(
                IllegalArgumentException.class,
                () -> new ShoppingCartItem(product, -5),
                "Se esperaba IllegalArgumentException para quantity negativa en el constructor."
        );
    }

    @Test
    @DisplayName("4. setQuantity con valor negativo lanza IllegalArgumentException.")
    public void testSetQuantityNegative() {
        Product product = new Product("P503", "Sombrero", 15.0);
        ShoppingCartItem item = new ShoppingCartItem(product, 2);

        assertThrows(
                IllegalArgumentException.class,
                () -> item.setQuantity(-1),
                "Se esperaba IllegalArgumentException al llamar setQuantity con valor negativo."
        );
    }

    @Test
    @DisplayName("5. setQuantity con cero actualiza quantity a cero.")
    public void testSetQuantityToZero() {
        Product product = new Product("P504", "Bufanda", 10.0);
        ShoppingCartItem item = new ShoppingCartItem(product, 5);

        item.setQuantity(0);
        assertEquals(0, item.getQuantity(), "La cantidad debe actualizarse a cero.");
        assertEquals(0.0, item.getSubtotal(), "El subtotal con quantity cero debe ser 0.0.");
    }

    @Test
    @DisplayName("6. getSubtotal calcula correctamente el subtotal tras actualizar quantity.")
    public void testGetSubtotalAfterQuantityChange() {
        Product product = new Product("P505", "Guantes", 12.5);
        ShoppingCartItem item = new ShoppingCartItem(product, 4);

        // Subtotal inicial
        assertEquals(50.0, item.getSubtotal(), "Subtotal inicial debe ser 12.5 × 4 = 50.0.");

        // Cambiar cantidad y verificar
        item.setQuantity(6);
        assertEquals(75.0, item.getSubtotal(), "Subtotal tras actualizar quantity a 6 debe ser 12.5 × 6 = 75.0.");
    }
}