package org.example.entities;

public class ShoppingCartItem {
    private final Product product;
    private int quantity;

    public ShoppingCartItem(Product product, int quantity) {
        if (product == null) throw new NullPointerException("El producto no puede ser nulo.");
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("La cantidad debe ser mayor o igual a cero.");

        this.quantity = quantity;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}