package legostore.model;

import java.util.HashSet;
import java.util.Set;

public class LegoSet extends Product {
    private final long id;
    private final int pieceCount;
    private final Theme theme;
    private final AgeGroup ageGroup;
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

    @Override
    public String toString() {
        return "LegoSet{" +
                "id=" + id +
                ",name=" + getName() +
                ",theme=" + theme +
                ",ageGroup=" + ageGroup +
                ", pieceCount=" + pieceCount +
                // ", minifigures=" + minifigures +
                ", price=" + getPrice() +
                '}';
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
}