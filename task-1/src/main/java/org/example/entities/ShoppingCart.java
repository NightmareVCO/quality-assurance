package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<ShoppingCartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        if (product == null) throw new NullPointerException("El producto no puede ser nulo.");
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");

        for (ShoppingCartItem item : items) {
            boolean isAlreadyInCart = product.equals(item.getProduct());

            if (isAlreadyInCart) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        items.add(new ShoppingCartItem(product, quantity));
    }

    public void updateProductQuantity(Product product, int newQuantity) {
        if (product == null) throw new NullPointerException("El producto no puede ser nulo.");
        if (newQuantity < 0) throw new IllegalArgumentException("La cantidad debe ser mayor o igual a cero.");

        for (ShoppingCartItem item : items) {
            boolean isAlreadyInCart = product.equals(item.getProduct());

            if (isAlreadyInCart) {
                if (newQuantity == 0) removeProduct(product);
                else item.setQuantity(newQuantity);

                return;
            }
        }
    }

    public void removeProduct(Product product) {
        if (product == null) throw new NullPointerException("El producto no puede ser nulo.");

        items.removeIf(item -> product.equals(item.getProduct()));
    }

    public double getTotal() {
        double total = 0.0;

        for (ShoppingCartItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }
}