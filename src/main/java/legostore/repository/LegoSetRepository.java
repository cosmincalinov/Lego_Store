package legostore.repository;

import legostore.dao.LegoSetDao;
import legostore.model.LegoSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class LegoSetRepository {
    private final Map<Long, LegoSet> sets = new HashMap<>();
    private final Map<Long, Integer> quantities = new HashMap<>();
    private final LegoSetDao dao;

    public LegoSetRepository(LegoSetDao dao) {
        this.dao = dao;
    }

    public List<LegoSet> getAllLegoSets() throws SQLException {
        return dao.listAllLegoSets();
    }

    public void addSet(LegoSet set) {
        if (set == null) throw new IllegalArgumentException("LegoSet cannot be null");
        try {
            if (dao.getLegoSetById(set.getId()) != null) {
                throw new IllegalArgumentException("A set with this ID already exists in the database.");
            }
            dao.insertLegoSet(set);
            sets.put(set.getId(), set);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add LegoSet to database: " + e.getMessage(), e);
        }
    }
    public LegoSet getSet(long id) {
        return sets.get(id);
    }

    public Collection<LegoSet> getAllSets() {
        return Collections.unmodifiableCollection(sets.values());
    }

    public LegoSet getLegoSetFromDatabase(long id) throws SQLException {
        return dao.getLegoSetById(id);
    }

    public void removeSet(long id) {
        sets.remove(id);
        quantities.remove(id);
    }

    public void setQuantity(long setId, int quantity) {
        quantities.put(setId, quantity);
    }

    public int getQuantity(long setId) {
        return quantities.getOrDefault(setId, 0);
    }

    public boolean containsSet(long id) {
        return sets.containsKey(id);
    }

    public int size() {
        return sets.size();
    }

    public void updateSalePrice(long legoSetId, double salePrice) throws SQLException {
        dao.updateSalePrice(legoSetId, salePrice);
    }

}