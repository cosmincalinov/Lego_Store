package legostore;

import legostore.model.*;
import legostore.repository.*;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static LegoSetRepository repo = new LegoSetRepository();
    private static ClientRepository clientRepo = new ClientRepository();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addNewClient(scanner);
                    break;
                case "2":
                    addNewLegoSet(scanner);
                    break;
                case "3":
                    addSaleToLegoSet(scanner);
                    break;
                case "4":
                    addItemToWishlist(scanner);
                    break;
                case "5":
                    removeItemFromWishlist(scanner);
                    break;
                case "6":
                    listWishlist(scanner);
                    break;
                case "7":
                    listAllLegoSets();
                    break;
                case "8":
                    listAllClients();
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
            Theme theme = Theme.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Available age groups: ");
            for (AgeGroup ag : AgeGroup.values()) System.out.print(ag + " ");
            System.out.println();
            System.out.print("Enter the age group: ");
            AgeGroup ageGroup = AgeGroup.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Enter the price: ");
            double price = Double.parseDouble(scanner.nextLine());

            LegoSet set = new LegoSet(setId, pieces, name, theme, price, ageGroup);
            repo.addSet(set);
            System.out.println("Lego set added successfully.");
        } catch (Exception e) {
            System.out.println("Failed to add Lego set: " + e.getMessage());
        }
    }

    public static void listAllLegoSets() {
        for(LegoSet set : repo.getAllSets()) {
            System.out.println(set);
        }
    }

    public static void listLegoSetDetails(Scanner scanner) {
        System.out.print("Enter the set ID: ");
        long setId = Long.parseLong(scanner.nextLine());
        LegoSet set = repo.getSet(setId);
        if(set == null) {
            System.out.println("No set found with that ID.");
        } else {
            System.out.println(set);
        }
    }

    public static void addNewClient(Scanner scanner ) {
        try {
            System.out.println("Enter client ID:");
            long clientId = Long.parseLong(scanner.nextLine().trim());
            if (clientRepo.containsClient(clientId)) {
                System.out.println("A client with this ID already exists.");
                return;
            }

            System.out.println("Enter client first name:");
            String firstName = scanner.nextLine().trim();

            System.out.println("Enter client last name:");
            String lastName = scanner.nextLine().trim();

            System.out.println("Enter client phone number:");
            String phoneNumber = scanner.nextLine().trim();

            Client client = new Client(clientId, firstName, lastName, phoneNumber);
            clientRepo.addClient(client);
            System.out.println("Client added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }

    public static void listAllClients() {
        for(Client client : clientRepo.getAllClients()) {
            System.out.println(client);
        }
    }

    public static void addItemToWishlist(Scanner scanner) {
        System.out.print("Enter client ID: ");
        long clientId = Long.parseLong(scanner.nextLine());
        Client client = clientRepo.getClient(clientId);
        if (client == null) {
            System.out.println("No client found with that ID.");
            return;
        }

        System.out.print("Enter Lego set ID to add: ");
        long setId = Long.parseLong(scanner.nextLine());
        LegoSet legoSet = repo.getSet(setId);
        if (legoSet == null) {
            System.out.println("No Lego set found with that ID.");
            return;
        }

        if (client.getWishlist().addLegoSet(legoSet)) {
            System.out.println("Lego set added to wishlist.");
        } else {
            System.out.println("Set is already in the wishlist.");
        }
    }

    public static void removeItemFromWishlist(Scanner scanner) {
        System.out.print("Enter client ID: ");
        long clientId = Long.parseLong(scanner.nextLine());
        Client client = clientRepo.getClient(clientId);
        if (client == null) {
            System.out.println("No client found with that ID.");
            return;
        }

        System.out.print("Enter Lego set ID to remove: ");
        long setId = Long.parseLong(scanner.nextLine());
        LegoSet legoSet = repo.getSet(setId);
        if (legoSet == null) {
            System.out.println("No Lego set found with that ID.");
            return;
        }

        if (client.getWishlist().removeLegoSet(legoSet)) {
            System.out.println("Lego set removed from wishlist.");
        } else {
            System.out.println("Set is not in the wishlist.");
        }
    }

    public static void listWishlist(Scanner scanner) {
        System.out.print("Enter client ID: ");
        long clientId = Long.parseLong(scanner.nextLine());
        Client client = clientRepo.getClient(clientId);
        if (client == null) {
            System.out.println("No client found with that ID.");
            return;
        }
        System.out.println(client.getWishlist());
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

    public static void addSaleToLegoSet(Scanner scanner) {
        System.out.print("Enter the set ID to put on sale: ");
        long setId = Long.parseLong(scanner.nextLine());
        LegoSet set = repo.getSet(setId);
        if (set == null) {
            System.out.println("No set found with that ID.");
            return;
        }
        System.out.println("Current price: " + set.getPrice());
        System.out.print("Enter new sale price: ");
        double salePrice = Double.parseDouble(scanner.nextLine());
        if (salePrice >= set.getPrice()) {
            System.out.println("Sale price must be less than current price.");
            return;
        }
        set.setSalePrice(salePrice);
        System.out.println(set.getName() + " is now on sale for " + salePrice + "!");
    }
}