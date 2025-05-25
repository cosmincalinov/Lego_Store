package legostore.repository;

import legostore.model.Client;
import java.util.*;

public class ClientRepository {
    private final Map<Long, Client> clients = new HashMap<>();

    public boolean addClient(Client client) {
        if (clients.containsKey(client.getId())) {
            return false;
        }
        clients.put(client.getId(), client);
        return true;
    }

    public Client getClient(long id) {
        return clients.get(id);
    }

    public boolean containsClient(long id) {
        return clients.containsKey(id);
    }

    public Collection<Client> getAllClients() {
        return clients.values();
    }
}
