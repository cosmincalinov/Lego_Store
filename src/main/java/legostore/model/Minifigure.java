package legostore.model;

import java.util.HashMap;
import java.util.Objects;

public class Minifigure extends Product{
    private final HashMap<PieceType, Piece> properties;

    public Minifigure(String name, HashMap<PieceType, Piece> properties) {
        super(name, 0);
        this.properties = (properties != null) ? new HashMap<>(properties) : new HashMap<>();
    }

    @Override
    public double getPrice() {
        double price = 0;
        for(Piece piece : properties.values()) {
            price += piece.price();
        }
        return price;
    }

    public HashMap<PieceType, Piece> getProperties() { return new HashMap<>(properties); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Minifigure that)) return false;
        return this.getName().equals(that.getName())
                && Math.abs(getPrice() - that.getPrice()) < 0.00001
                && properties.equals(that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), properties);
    }
}
