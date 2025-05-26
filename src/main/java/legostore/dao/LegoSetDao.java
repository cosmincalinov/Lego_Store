package legostore.dao;

import legostore.db.DatabaseUtil;
import legostore.model.LegoSet;
import legostore.model.Theme;
import legostore.model.AgeGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LegoSetDao {

    public void insertLegoSet(LegoSet legoSet) throws SQLException {
        String sql = "INSERT INTO lego_sets (name, piece_count, theme, price, age_group) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, legoSet.getName());
            stmt.setInt(2, legoSet.getPieceCount());
            stmt.setString(3, legoSet.getTheme().toString());
            stmt.setDouble(4, legoSet.getPrice());
            stmt.setString(5, legoSet.getAgeGroup().toString());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    legoSet.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public LegoSet getLegoSetById(long id) throws SQLException {
        String sql = "SELECT * FROM lego_sets WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new LegoSet(
                        rs.getLong("id"),
                        rs.getInt("piece_count"),
                        rs.getString("name"),
                        Theme.valueOf(rs.getString("theme")),
                        rs.getDouble("price"),
                        AgeGroup.valueOf(rs.getString("age_group"))
                );
            }
            return null;
        }
    }

    public void updateLegoSet(LegoSet legoSet) throws SQLException {
        String sql = "UPDATE lego_sets SET name = ?, piece_count = ?, theme = ?, price = ?, age_group = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, legoSet.getName());
            stmt.setInt(2, legoSet.getPieceCount());
            stmt.setString(3, legoSet.getTheme().toString());
            stmt.setDouble(4, legoSet.getPrice());
            stmt.setString(5, legoSet.getAgeGroup().toString());
            stmt.setLong(6, legoSet.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteLegoSet(long id) throws SQLException {
        String sql = "DELETE FROM lego_sets WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<LegoSet> listAllLegoSets() throws SQLException {
        String sql = "SELECT * FROM lego_sets";
        List<LegoSet> sets = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sets.add(new LegoSet(
                        rs.getLong("id"),
                        rs.getInt("piece_count"),
                        rs.getString("name"),
                        Theme.valueOf(rs.getString("theme").toUpperCase()),
                        rs.getDouble("price"),
                        AgeGroup.valueOf(rs.getString("age_group"))
                ));
            }
        }
        return sets;
    }
}