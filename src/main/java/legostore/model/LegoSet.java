package legostore.model;

import legostore.observer.Observer;
import legostore.observer.Subject;

import java.util.HashSet;
import java.util.Set;

public class LegoSet extends Product implements Subject {
    private final long id;
    private final int pieceCount;
    private final Theme theme;
    private final AgeGroup ageGroup;
    private boolean onSale = false;
    private double salePrice = 0.0;

    private Set<Observer> observers = new HashSet<>();

    // private final Set<Minifigure> minifigures;

    public LegoSet(long id,int pieceCount, /*Set<Minifigure> minifigures,*/
                   String name, Theme theme, double price, AgeGroup ageGroup) {
        super(name, price);
        this.id = id;
        if(pieceCount < 1) {
            throw new IllegalArgumentException("pieceCount must be greater than 0");
        }
        this.pieceCount = pieceCount;
        this.theme = theme;
        this.ageGroup = ageGroup;
        // this.minifigures = (minifigures != null) ? new HashSet<>(minifigures) : new HashSet<>();
    }

    public long getId() { return id; }
    public int getPieceCount() { return pieceCount; }
    public Theme getTheme() { return theme; }
    public AgeGroup getAgeGroup() { return ageGroup; }
    // public Set<Minifigure> getMinifigures() { return new HashSet<>(minifigures);}

    public void setSalePrice(double salePrice) {
        this.onSale = true;
        this.salePrice = salePrice;
        notifyObservers("Lego set '" + getName() + "' is now on sale for $" + salePrice + ".");
    }

    public void removeSale() {
        this.onSale = false;
        this.salePrice = 0.0;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public double getEffectivePrice() {
        return onSale ? salePrice : getPrice();
    }

    @Override
    public String toString() {
        String base = "LegoSet{" +
                "id=" + id +
                ",name=" + getName() +
                ",theme=" + theme +
                ",ageGroup=" + ageGroup +
                ", pieceCount=" + pieceCount +
                ", price=" + getPrice();
        if (onSale) base += ", SALE: " + salePrice;
        return base + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LegoSet that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

}