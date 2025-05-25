package legostore.model;

import java.util.HashSet;
import java.util.Set;

public class Wishlist {
    private final Set<LegoSet> wishlist;

    public Wishlist() {
        wishlist = new HashSet<>();
    }

    public boolean addLegoSet(LegoSet set) {
        return wishlist.add(set);
    }

    public boolean removeLegoSet(LegoSet set) {
        return wishlist.remove(set);
    }

    public Set<LegoSet> getWishlistItems() {
        return wishlist;
    }

    @Override
    public String toString() {
        if (wishlist.isEmpty()) return "Wishlist is empty.";
        StringBuilder sb = new StringBuilder("Wishlist:\n");
        for (LegoSet set : wishlist) {
            sb.append(set).append("\n");
        }
        return sb.toString();
    }
}
