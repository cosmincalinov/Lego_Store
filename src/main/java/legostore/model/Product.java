package legostore.model;

import java.util.Objects;

public abstract class Product {
    private final String name;
    private final double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " $" + price;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Product) {
            Product p = (Product) o;
            return name.equals(p.name) && Math.abs(price - p.price) < 0.00001;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
