package legostore.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String FILE_NAME = "src/main/resources/audit.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static AuditService instance = null;

    private AuditService() {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            if (new java.io.File(FILE_NAME).length() == 0) {
                writer.append("action_name,timestamp\n");
            }
        } catch (IOException e) {
            System.out.println("Error initializing audit file: " + e.getMessage());
        }
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String actionName) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.append(actionName).append(",").append(timestamp).append("\n");
        } catch (IOException e) {
            System.out.println("Error writing to audit file: " + e.getMessage());
        }
    }
}