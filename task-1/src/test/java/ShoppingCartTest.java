/*
    Vladimir Curiel - 10141415

    Cobertura mínima de pruebas:
        1.- addProduct con Product y cantidad válidos agrega ítem al carrito.
        2.- addProduct con Product nulo lanza NullPointerException.
        3.- addProduct con cantidad ≤ 0 lanza IllegalArgumentException.
        4.- addProduct con mismo Product suma cantidades existentes.
        5.- updateProductQuantity con Product nulo lanza NullPointerException.
        6.- updateProductQuantity con newQuantity negativo lanza IllegalArgumentException.
        7.- updateProductQuantity con newQuantity = 0 elimina el ítem del carrito.
        8.- updateProductQuantity con newQuantity > 0 actualiza la cantidad.
        9.- removeProduct con Product nulo lanza NullPointerException.
        10.- removeProduct elimina el ítem existente.
        11.- getTotal calcula correctamente el total de todos los ítems.
*/

import org.example.entities.Product;
import org.example.entities.ShoppingCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    @Test
    @DisplayName("1. addProduct agrega ítem válido al carrito y getTotal refleja el subtotal.")
    public void testAddProductValid() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product("P600", "Camisa", 20.0);

        cart.addProduct(p, 2);

        assertEquals(40.0, cart.getTotal(), "El total debe ser 20.0 × 2 = 40.0.");
    }

    @Test
    @DisplayName("2. addProduct con Product nulo lanza NullPointerException.")
    public void testAddProductNullProduct() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(
                NullPointerException.class,
                () -> cart.addProduct(null, 1),
                "Se esperaba NullPointerException cuando el Product es nulo."
        );
    }

    @Test
    @DisplayName("3. addProduct con cantidad ≤ 0 lanza IllegalArgumentException.")
    public void testAddProductNonPositiveQuantity() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product("P601", "Pantalón", 30.0);

        assertThrows(
                IllegalArgumentException.class,
                () -> cart.addProduct(p, 0),
                "Se esperaba IllegalArgumentException para cantidad cero."
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> cart.addProduct(p, -3),
                "Se esperaba IllegalArgumentException para cantidad negativa."
        );
    }

    @Test
    @DisplayName("4. addProduct con mismo Product suma cantidades existentes.")
    public void testAddProductAccumulatesQuantity() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product("P602", "Zapatos", 50.0);

        cart.addProduct(p, 1); // subtotal = 50.0
        cart.addProduct(p, 2); // subtotal = 50.0 × 3 = 150.0
        assertEquals(150.0, cart.getTotal(), "El total debe ser 50.0 × 3 = 150.0.");
    }

    @Test
    @DisplayName("5. updateProductQuantity con Product nulo lanza NullPointerException.")
    public void testUpdateProductQuantityNullProduct() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(
                NullPointerException.class,
                () -> cart.updateProductQuantity(null, 1),
                "Se esperaba NullPointerException cuando el Product es nulo."
        );
    }

    @Test
    @DisplayName("6. updateProductQuantity con newQuantity negativo lanza IllegalArgumentException.")
    public void testUpdateProductQuantityNegative() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product("P603", "Sombrero", 15.0);
        cart.addProduct(p, 2);

        assertThrows(
                IllegalArgumentException.class,
                () -> cart.updateProductQuantity(p, -1),
                "Se esperaba IllegalArgumentException para newQuantity negativa."
        );
    }

    @Test
    @DisplayName("7. updateProductQuantity con newQuantity = 0 elimina el ítem del carrito.")
    public void testUpdateProductQuantityToZeroRemovesItem() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product("P604", "Bufanda", 10.0);
        cart.addProduct(p, 3); // subtotal inicial = 30.0

        cart.updateProductQuantity(p, 0);

        assertEquals(0.0, cart.getTotal(), "El total debe ser 0.0 tras eliminar el ítem.");
    }

    @Test
    @DisplayName("8. updateProductQuantity con newQuantity > 0 actualiza la cantidad.")
    public void testUpdateProductQuantityPositive() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product("P605", "Guantes", 12.5);
        cart.addProduct(p, 2); // subtotal inicial = 25.0

        cart.updateProductQuantity(p, 5);
        assertEquals(62.5, cart.getTotal(), "El total debe actualizarse a 12.5 × 5 = 62.5.");
    }

    @Test
    @DisplayName("9. removeProduct con Product nulo lanza NullPointerException.")
    public void testRemoveProductNullProduct() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(
                NullPointerException.class,
                () -> cart.removeProduct(null),
                "Se esperaba NullPointerException cuando el Product es nulo."
        );
    }

    @Test
    @DisplayName("10. removeProduct elimina el ítem existente del carrito.")
    public void testRemoveProductRemovesItem() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product("P606", "Corbata", 25.0);
        cart.addProduct(p, 2); // subtotal inicial = 50.0

        cart.removeProduct(p);
        assertEquals(0.0, cart.getTotal(), "El total debe ser 0.0 tras remover el ítem.");
    }

    @Test
    @DisplayName("11. getTotal calcula el total de múltiples ítems.")
    public void testGetTotalMultipleItems() {
        ShoppingCart cart = new ShoppingCart();
        Product p1 = new Product("P607", "Cinturón", 15.0);
        Product p2 = new Product("P608", "Reloj", 100.0);

        cart.addProduct(p1, 2); // subtotal p1 = 30.0
        cart.addProduct(p2, 1); // subtotal p2 = 100.0

        assertEquals(130.0, cart.getTotal(), "El total debe ser 30.0 + 100.0 = 130.0.");
    }
}