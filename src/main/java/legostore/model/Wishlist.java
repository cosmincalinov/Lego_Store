package legostore.model;

import java.util.HashSet;
import java.util.Set;

public class Wishlist {
    private final Set<LegoSet> sets = new HashSet<>();
    private final Client owner;

    public Wishlist(Client owner) {
        this.owner = owner;
    }

    public boolean addLegoSet(LegoSet set) {
        boolean added = sets.add(set);
        if(added) {
            set.addObserver(owner);
        }
        return added;
    }

    public boolean removeLegoSet(LegoSet set) {
        boolean removed = sets.remove(set);
        if (removed) {
            set.removeObserver(owner);
        }
        return removed;
    }

    public Set<LegoSet> getWishlistItems() {
        return new HashSet<>(sets);
    }

    @Override
    public String toString() {
        if (sets.isEmpty()) return "Wishlist is empty.";
        StringBuilder sb = new StringBuilder("Wishlist:\n");
        for (LegoSet set : sets) {
            sb.append(set).append("\n");
        }
        return sb.toString();
    }
}
