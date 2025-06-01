/*
    Vladimir Curiel - 10141415

    Cobertura mínima de pruebas:
        1.- Crear Product con datos válidos.
        2.- Lanzar excepción si el id es nulo.
        3.- Lanzar excepción si el nombre es nulo.
        4.- Lanzar excepción si el precio es negativo o cero.
        5.- equals devuelve true para mismos IDs.
        6.- equals devuelve false para diferentes IDs o comparado con otro tipo.
        7.- Verificar que hashCode sea consistente con equals.
        8.- Verificar comportamiento de equals frente a null.
*/

import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    @DisplayName("1. Crear Product válido y verificar getters.")
    public void testCreateValidProduct() {
        Product product = new Product("P100", "Tablet", 299.99);

        assertEquals("P100", product.getId(), "El id debe ser 'P100'.");
        assertEquals("Tablet", product.getName(), "El nombre debe ser 'Tablet'.");
        assertEquals(299.99, product.getPrice(), "El precio debe ser 299.99.");
    }

    @Test
    @DisplayName("2. Crear Product con id nulo lanza NullPointerException.")
    public void testCreateProductWithNullId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, "Mouse", 25.0),
                "Se esperaba NullPointerException para id nulo."
        );
    }

    @Test
    @DisplayName("3. Crear Product con nombre nulo lanza NullPointerException.")
    public void testCreateProductWithNullName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P101", null, 45.0),
                "Se esperaba NullPointerException para nombre nulo."
        );
    }

    @Test
    @DisplayName("4. Crear Product con precio negativo lanza IllegalArgumentException.")
    public void testCreateProductWithNegativePrice() {
         assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P102", "Teclado", -10.0),
                "Se esperaba IllegalArgumentException para precio negativo."
        );
    }

    @Test
    @DisplayName("5. equals devuelve true para dos Products con mismo id.")
    public void testEqualsSameIds() {
        Product p1 = new Product("P200", "Phone", 800.0);
        Product p2 = new Product("P200", "Phone Pro", 950.0);

        assertEquals(p1, p2, "Dos productos con mismo id deben ser iguales.");
    }

    @Test
    @DisplayName("6a. equals devuelve false para Products con distintos ids.")
    public void testEqualsDifferentIds() {
        Product p1 = new Product("P201", "Laptop", 1200.0);
        Product p2 = new Product("P202", "Laptop Pro", 1500.0);

        assertNotEquals(p1, p2, "Productos con diferentes ids no deben ser iguales.");
    }

    @Test
    @DisplayName("6b. equals devuelve false al comparar Product con otro tipo.")
    public void testEqualsWithDifferentType() {
        Product p = new Product("P203", "Speaker", 99.0);
        Object notAProduct = "P203";

        assertNotEquals(notAProduct, p, "equals debe devolver false al comparar con String.");
    }

    @Test
    @DisplayName("7. hashCode coincide para Products iguales según equals.")
    public void testHashCodeConsistentWithEquals() {
        Product p1 = new Product("P300", "Desk", 250.0);
        Product p2 = new Product("P300", "Desk Deluxe", 300.0);

        assertEquals(p1, p2, "equals debe ser true para mismo id.");
        assertEquals(p1.hashCode(), p2.hashCode(),
                "hashCode debe ser igual para Products considerados iguales.");
    }

    @Test
    @DisplayName("8. equals devuelve false al comparar con null.")
    public void testEqualsWithNull() {
        Product p = new Product("P400", "Chair", 75.0);
        assertNotEquals(null, p, "equals debe devolver false cuando se compara con null.");
    }
}