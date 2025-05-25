package legostore.repository;

import legostore.model.LegoSet;

import java.util.*;

public class LegoSetRepository {
    private final Map<Long, LegoSet> sets = new HashMap<>();

    public void addSet(LegoSet set) {
        if (set == null) throw new IllegalArgumentException("LegoSet cannot be null");
        sets.put(set.getId(), set);
    }

    public LegoSet getSet(long id) {
        return sets.get(id);
    }

    public Collection<LegoSet> getAllSets() {
        return Collections.unmodifiableCollection(sets.values());
    }

    public void removeSet(long id) {
        sets.remove(id);
    }

    public boolean containsSet(long id) {
        return sets.containsKey(id);
    }

    public int size() {
        return sets.size();
    }
}