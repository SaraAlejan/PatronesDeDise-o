import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingSystem {
    private static BookingSystem instance;
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    private BookingSystem() {}

    public static BookingSystem getInstance() {
        if (instance == null) {
            instance = new BookingSystem();
        }
        return instance;
    }

    public void makeReservation(int clienteId, int habitacion, String fechaCheckIn, String fechaCheckOut) {
        if (isAvailable(habitacion, fechaCheckIn, fechaCheckOut)) {
            String sql = "INSERT INTO reservas (cliente_id, habitacion_id, fecha_checkin, fecha_checkout) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, clienteId);
                stmt.setInt(2, habitacion);
                stmt.setString(3, fechaCheckIn);
                stmt.setString(4, fechaCheckOut);
                stmt.executeUpdate();
                System.out.println("Reserva creada para el cliente con ID " + clienteId);
            } catch (SQLException e) {
                System.out.println("Error al guardar la reserva: " + e.getMessage());
            }
        } else {
            System.out.println("La habitación no está disponible en las fechas seleccionadas.");
        }
    }

    private boolean isAvailable(int habitacion, String fechaCheckIn, String fechaCheckOut) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE habitacion_id = ? " +
                "AND (fecha_checkin < ? AND fecha_checkout > ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, habitacion);
            stmt.setString(2, fechaCheckOut);
            stmt.setString(3, fechaCheckIn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Si no hay reservas, la habitación está disponible
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
        }
        return false; // En caso de error, asumimos que no está disponible
    }
}


