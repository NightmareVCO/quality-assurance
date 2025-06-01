package org.example.entities;

import java.util.Objects;

public class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("El ID del producto no puede ser nulo o vacío.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío.");
        if (price < 0) throw new IllegalArgumentException("El precio del producto no puede ser negativo.");

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("El ID del producto no puede ser nulo o vacío.");

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío.");

        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("El precio del producto no puede ser negativo.");

        this.price = price;
    }

    @Override
    public boolean equals(Object anyObject) {
        if (anyObject == null) return false;
        if (!(anyObject instanceof Product product)) return false;

        if (this == anyObject) return true;

        return Objects.equals(this.id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
