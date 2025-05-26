package legostore.dao;

import legostore.model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
