package legostore.dao;

import legostore.db.DatabaseUtil;
import legostore.model.AgeGroup;
import legostore.model.Client;
import legostore.model.LegoSet;
import legostore.model.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientDao {
    private final Connection connection;

    public ClientDao(Connection connection) {
        this.connection = connection;
    }

    public void insertClient(Client client) throws SQLException {
        String sql = "INSERT INTO clients (id, first_name, last_name, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, client.getId());
            stmt.setString(2, client.getFirstName());
            stmt.setString(3, client.getLastName());
            stmt.setString(4, client.getPhone());
            stmt.executeUpdate();
        }
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, phone FROM clients";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone")
                ));
            }
        }
        return clients;
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
                        Theme.valueOf(rs.getString("theme").replace(" ", "_").toUpperCase()),
                        rs.getDouble("price"),
                        AgeGroup.valueOf(rs.getString("age_group"))
                );
            }
            return null;
        }
    }

    public void addLegoSetToWishlist(long clientId, long legoSetId) throws SQLException {
        String sql = "INSERT INTO client_wishlist (client_id, lego_set_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, clientId);
            stmt.setLong(2, legoSetId);
            stmt.executeUpdate();
        }

        Client client = getClientById(clientId);
        LegoSet legoSet = getLegoSetById(legoSetId);

        if (client != null && legoSet != null) {
            legoSet.addObserver(client);
        }
    }

    public void removeLegoSetFromWishlist(long clientId, long legoSetId) throws SQLException {
        String sql = "DELETE FROM client_wishlist WHERE client_id = ? AND lego_set_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, clientId);
            stmt.setLong(2, legoSetId);
            stmt.executeUpdate();
        }
    }

    public Set<LegoSet> getWishlist(long clientId, Client client) throws SQLException {
        Set<LegoSet> wishlist = new HashSet<>();
        String sql = "SELECT l.* FROM lego_sets l " +
                "JOIN client_wishlist cw ON l.id = cw.lego_set_id " +
                "WHERE cw.client_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LegoSet set = new LegoSet(
                            rs.getLong("id"),
                            rs.getInt("piece_count"),
                            rs.getString("name"),
                            Theme.valueOf(rs.getString("theme").replace(" ", "_").toUpperCase()),
                            rs.getDouble("price"),
                            AgeGroup.valueOf(rs.getString("age_group"))
                    );
                    set.addObserver(client);
                    wishlist.add(set);
                }
            }
        }
        return wishlist;
    }

    public Client getClientById(long id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, phone FROM clients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("phone")
                    );
                }
                return null;
            }
        }
    }
}
