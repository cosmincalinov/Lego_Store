package legostore;

import legostore.dao.ClientDao;
import legostore.dao.LegoSetDao;
import legostore.db.DatabaseUtil;
import legostore.model.*;
import legostore.repository.*;
import legostore.service.AuditService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static LegoSetRepository repo = new LegoSetRepository(new LegoSetDao());
    // private static ClientRepository clientRepo = new ClientRepository();

    private static Connection connection;

    static {
        try {
            connection = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClientDao clientDao = new ClientDao(connection);

    public Main() throws SQLException {
    }

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addNewClient(clientDao ,scanner);
                    break;
                case "2":
                    addNewLegoSet(scanner);
                    break;
                case "3":
                    addSaleToLegoSet(clientDao, scanner);
                    break;
                case "4":
                    addItemToWishlist(clientDao,scanner);
                    break;
                case "5":
                    removeItemFromWishlist(clientDao, scanner);
                    break;
                case "6":
                    listWishlist(clientDao, scanner);
                    break;
                case "7":
                    listAllLegoSets();
                    break;
                case "8":
                    listAllClients(clientDao);
                    break;
                case "9":
                    listLegoSetDetails(scanner);
                    break;
                case "10":
                    createNewMinifig(scanner);
                    break;
                case "0":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Add a new client");
        System.out.println("2. Add a new Lego set");
        System.out.println("3. Add sale to a lego set");
        System.out.println("4. Add item to wishlist");
        System.out.println("5. Remove item from wishlist");
        System.out.println("6. List wishlist");
        System.out.println("7. List all Lego sets");
        System.out.println("8. List all clients");
        System.out.println("9. View Lego set details");
        System.out.println("0. Exit");
    }

    public static void addNewLegoSet(Scanner scanner) {
        try {
            System.out.print("Enter the set ID: ");
            long setId = Long.parseLong(scanner.nextLine());

            if (repo.containsSet(setId)) {
                System.out.println("A set with this ID already exists.");
                return;
            }

            System.out.print("Enter the name of the set: ");
            String name = scanner.nextLine();

            System.out.print("Enter the number of pieces: ");
            int pieces = Integer.parseInt(scanner.nextLine());

            System.out.print("Available themes: ");
            for (Theme t : Theme.values()) System.out.print(t + " ");
            System.out.println();
            System.out.print("Enter the theme: ");
            Theme theme = Theme.fromInput(scanner.nextLine());

            System.out.print("Available age groups: ");
            for (AgeGroup ag : AgeGroup.values()) System.out.print(ag + " ");
            System.out.println();
            System.out.print("Enter the age group (toddler, child, teen, adult): ");
            AgeGroup ageGroup = AgeGroup.fromInput(scanner.nextLine());

            System.out.print("Enter the price: ");
            double price = Double.parseDouble(scanner.nextLine());

            LegoSet set = new LegoSet(setId, pieces, name, theme, price, ageGroup);
            repo.addSet(set);
            System.out.println("Lego set added successfully.");
            AuditService.getInstance().logAction("add_new_set");
        } catch (Exception e) {
            System.out.println("Failed to add Lego set: " + e.getMessage());
        }
    }

    public static void listAllLegoSets() {
        try {
            for (LegoSet set : repo.getAllLegoSets()) {
                System.out.println(set);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve Lego sets from database: " + e.getMessage());
        }
    }

    public static void listLegoSetDetails(Scanner scanner) {
        System.out.print("Enter the set ID: ");
        long setId = Long.parseLong(scanner.nextLine());
        try {
            LegoSet set = repo.getLegoSetFromDatabase(setId);
            if (set == null) {
                System.out.println("No set found with that ID in the database.");
            } else {
                System.out.println(set);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Lego set: " + e.getMessage());
        }
    }

    public static void addNewClient(ClientDao clientDao, Scanner scanner) {
        System.out.print("Enter client ID: ");
        long id = Long.parseLong(scanner.nextLine());
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        Client client = new Client(id, firstName, lastName, phone);

        try {
            clientDao.insertClient(client);
            System.out.println("Client added successfully!");
            AuditService.getInstance().logAction("add_new_client");
        } catch (SQLException e) {
            System.out.println("Error adding client: " + e.getMessage());
        }
    }

    public static void listAllClients(ClientDao clientDao) {
        try {
            List<Client> clients = clientDao.getAllClients();
            if (clients.isEmpty()) {
                System.out.println("No clients found.");
            } else {
                for (Client client : clients) {
                    System.out.println(client);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving clients: " + e.getMessage());
        }
    }

    public static void addItemToWishlist(ClientDao clientDao, Scanner scanner) {
        System.out.print("Enter client ID: ");
        long clientId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Lego Set ID to add to wishlist: ");
        long legoSetId = Long.parseLong(scanner.nextLine());
        try {
            clientDao.addLegoSetToWishlist(clientId, legoSetId);
            System.out.println("Lego set added to wishlist.");
        } catch (SQLException e) {
            System.out.println("Failed to add to wishlist: " + e.getMessage());
        }
    }

    public static void removeItemFromWishlist(ClientDao clientDao, Scanner scanner) {
        System.out.print("Enter client ID: ");
        long clientId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Lego Set ID to remove from wishlist: ");
        long legoSetId = Long.parseLong(scanner.nextLine());
        try {
            clientDao.removeLegoSetFromWishlist(clientId, legoSetId);
            System.out.println("Lego set removed from wishlist.");
        } catch (SQLException e) {
            System.out.println("Failed to remove from wishlist: " + e.getMessage());
        }
    }

    public static void listWishlist(ClientDao clientDao, Scanner scanner) {
        System.out.print("Enter client ID: ");
        long clientId = Long.parseLong(scanner.nextLine());
        try {
            Client client = clientDao.getClientById(clientId);
            Set<LegoSet> wishlist = clientDao.getWishlist(clientId, client);
            if (wishlist.isEmpty()) {
                System.out.println("Wishlist is empty.");
            } else {
                System.out.println("Wishlist:");
                for (LegoSet set : wishlist) {
                    System.out.println(set);
                    if (set.isOnSale()) {
                        String message = "Lego set '" + set.getName() + "' is now on sale for $" + set.getEffectivePrice() + ".";
                        set.notifyObservers(message);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve wishlist: " + e.getMessage());
        }
    }

    public static void createNewMinifig(Scanner scanner) {
        System.out.println("Let's build a new Minifig!");

        // 1. Select head
        System.out.println("Enter head piece traits:");
        System.out.println(" 1. Color (e.g. 'red'): ");
        String headCol = scanner.nextLine();
        System.out.println(" 2. Price (e.g. '17.5'): ");
        double headPrice = Double.parseDouble(scanner.nextLine());
        Piece head = new Piece(PieceType.HEAD, headCol, headPrice);

        // 2. Select body
        System.out.println("Enter body piece traits:");
        System.out.println(" 1. Color (e.g. 'blue'): ");
        String bodyCol = scanner.nextLine();
        System.out.println(" 2. Price (e.g. '15.5'): ");
        double bodyPrice = Double.parseDouble(scanner.nextLine());
        Piece body = new Piece(PieceType.BODY, bodyCol, bodyPrice);

        // 3. Select legs
        System.out.println("Enter legs piece traits:");
        System.out.println(" 1. Color (e.g. 'green'): ");
        String legsCol = scanner.nextLine();
        System.out.println(" 2. Price (e.g. '13.5'): ");
        double legsPrice = Double.parseDouble(scanner.nextLine());
        Piece legs = new Piece(PieceType.LEGS, legsCol, legsPrice);

        // 4. Select accessory
        System.out.println("Enter accessory piece traits:");
        System.out.println(" 1. Color (e.g. 'yellow'): ");
        String accCol = scanner.nextLine();
        System.out.println(" 2. Price (e.g. '11.5'): ");
        double accPrice = Double.parseDouble(scanner.nextLine());
        Piece acc = new Piece(PieceType.ACCESSORY, accCol, accPrice);

        // 5. Name the minifig
        System.out.println("Give your minifig a name: ");
        String minifigName = scanner.nextLine();

        // 6. Create minifig
        HashMap<PieceType, Piece> fig = new HashMap<>();
        fig.put(PieceType.HEAD, head);
        fig.put(PieceType.BODY, body);
        fig.put(PieceType.LEGS, legs);
        fig.put(PieceType.ACCESSORY, acc);

        Minifigure minifig = new Minifigure(minifigName, fig);
        System.out.println("You built: " + minifig);

        // 7. Ask to buy
        System.out.println("Would you like to buy this minifig? (y/n): ");
        String buy = scanner.nextLine().trim().toLowerCase();
        if (buy.equals("y")) {
            System.out.println("Congratulations, you bought " + minifigName + "!");
        } else {
            System.out.println("Minifig creation complete. Not purchased.");
        }
    }

    public static void addSaleToLegoSet(ClientDao clientDao, Scanner scanner) throws SQLException {
        System.out.print("Enter client ID: ");
        long clientId = Long.parseLong(scanner.nextLine());

        System.out.print("Enter the set ID to put on sale: ");
        long saleSetId = Long.parseLong(scanner.nextLine());

        Client client = clientDao.getClientById(clientId);
        if (client == null) {
            System.out.println("No client found with that ID.");
            return;
        }

        Set<LegoSet> wishlist = clientDao.getWishlist(clientId, client); // addObserver(client) happens here

        LegoSet setToSale = null;
        for (LegoSet set : wishlist) {
            if (set.getId() == saleSetId) {
                setToSale = set;
                break;
            }
        }

        if (setToSale != null) {
            System.out.println("Current price: " + setToSale.getPrice());
            System.out.print("Enter new sale price: ");
            double newSalePrice = Double.parseDouble(scanner.nextLine());
            if (newSalePrice >= setToSale.getPrice()) {
                System.out.println("Sale price must be less than current price.");
                return;
            }
            setToSale.setSalePrice(newSalePrice); // Notifies observer(s) in memory!
            try {
                // Update the database with the new sale price
                repo.updateSalePrice(setToSale.getId(), newSalePrice);
                System.out.println(setToSale.getName() + " is now on sale for " + newSalePrice + "!");
            } catch (SQLException e) {
                System.out.println("Failed to update sale price in database: " + e.getMessage());
            }
        } else {
            System.out.println("Set not found in wishlist!");
        }
    }
}