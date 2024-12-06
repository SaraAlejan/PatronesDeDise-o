import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {
    private static final String LOG_FILE = "application.log"; // Nombre del archivo de log

    public static void log(String serviceName, boolean success, String description) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
        String status = success ? "Exitoso" : "Fallido";
        String logEntry = String.format("%s  %s - %s: %s", timestamp, serviceName, status, description);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }
}

