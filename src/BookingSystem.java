import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingSystem {
    private static BookingSystem instance;
    private Connection connection;

    private BookingSystem() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        if (this.connection == null) {
            throw new SQLException("No se pudo establecer la conexión con la base de datos.");
        }
    }

    public static BookingSystem getInstance() {
        try {
            if (instance == null) {
                instance = new BookingSystem();
            }
        } catch (SQLException e) {
            System.out.println("Error al crear BookingSystem: " + e.getMessage());
            return null;
        }
        return instance;
    }

    public void makeReservation(int clienteId, int habitacion, String fechaCheckIn, String fechaCheckOut) {
        String serviceName = "CrearReserva";
        try {
            if (isAvailable(habitacion, fechaCheckIn, fechaCheckOut)) {
                String sql = "INSERT INTO reservas (cliente_id, habitacion_id, fecha_checkin, fecha_checkout) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, clienteId);
                    stmt.setInt(2, habitacion);
                    stmt.setString(3, fechaCheckIn);
                    stmt.setString(4, fechaCheckOut);
                    stmt.executeUpdate();

                    String successMessage = "Reserva creada para el cliente con ID " + clienteId;
                    System.out.println(successMessage);
                    LogUtils.log(serviceName, true, successMessage);
                }
            } else {
                String errorMessage = "La habitación no está disponible en las fechas seleccionadas.";
                System.out.println(errorMessage);
                LogUtils.log(serviceName, false, errorMessage);
            }
        } catch (SQLException e) {
            String errorMessage = "Error al guardar la reserva: " + e.getMessage();
            System.out.println(errorMessage);
            LogUtils.log(serviceName, false, errorMessage);
        }
    }

    private boolean isAvailable(int habitacion, String fechaCheckIn, String fechaCheckOut) {
        String serviceName = "VerificarDisponibilidad";
        String sql = "SELECT COUNT(*) FROM reservas WHERE habitacion_id = ? AND (fecha_checkin < ? AND fecha_checkout > ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, habitacion);
            stmt.setString(2, fechaCheckOut);
            stmt.setString(3, fechaCheckIn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boolean available = rs.getInt(1) == 0;
                LogUtils.log(serviceName, true, "Habitación disponible: " + available);
                return available;
            }
        } catch (SQLException e) {
            String errorMessage = "Error al verificar disponibilidad: " + e.getMessage();
            System.out.println(errorMessage);
            LogUtils.log(serviceName, false, errorMessage);
        }
        return false;
    }

    public void viewReservations(int clienteId) {
        String serviceName = "VerReservas";
        String sql = "SELECT * FROM reservas WHERE cliente_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Tus reservas:");
            while (rs.next()) {
                int reservaId = rs.getInt("reserva_id");
                int habitacionId = rs.getInt("habitacion_id");
                String fechaCheckIn = rs.getString("fecha_checkin");
                String fechaCheckOut = rs.getString("fecha_checkout");

                System.out.println("Reserva ID: " + reservaId + ", Habitación: " + habitacionId +
                        ", Check-in: " + fechaCheckIn + ", Check-out: " + fechaCheckOut);
            }

            LogUtils.log(serviceName, true, "Reservas mostradas para cliente ID " + clienteId);
        } catch (SQLException e) {
            String errorMessage = "Error al obtener reservas: " + e.getMessage();
            System.out.println(errorMessage);
            LogUtils.log(serviceName, false, errorMessage);
        }
    }


    public void updateReservation(int reservaId, int habitacion, String fechaCheckIn, String fechaCheckOut, int clienteId) {
        String serviceName = "ActualizarReserva";

        try {
            // Verificar si la reserva pertenece al cliente actual
            String checkSql = "SELECT cliente_id FROM reservas WHERE reserva_id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setInt(1, reservaId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int clienteIdEnReserva = rs.getInt("cliente_id");

                    if (clienteIdEnReserva != clienteId) {
                        System.out.println("Error: No puedes actualizar reservas que no te pertenecen.");
                        LogUtils.log(serviceName, false, "Intento de actualizar reserva no propia.");
                        return;
                    }
                } else {
                    System.out.println("No se encontró la reserva.");
                    return;
                }
            }

            // Verificar si la habitación está disponible en las fechas especificadas
            if (isAvailable(habitacion, fechaCheckIn, fechaCheckOut)) {
                String sql = "UPDATE reservas SET habitacion_id = ?, fecha_checkin = ?, fecha_checkout = ? WHERE reserva_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, habitacion);
                    stmt.setString(2, fechaCheckIn);
                    stmt.setString(3, fechaCheckOut);
                    stmt.setInt(4, reservaId);

                    stmt.executeUpdate();
                    System.out.println("Reserva actualizada con éxito.");
                    LogUtils.log(serviceName, true, "Reserva actualizada.");
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error al actualizar la reserva: " + e.getMessage();
            System.out.println(errorMessage);
            LogUtils.log(serviceName, false, errorMessage);
        }
    }


    public void deleteReservation(int reservaId, int clienteId) {
        String serviceName = "EliminarReserva";
        String sql = "DELETE FROM reservas WHERE reserva_id = ? AND cliente_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reservaId);
            stmt.setInt(2, clienteId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reserva eliminada con éxito.");
                LogUtils.log(serviceName, true, "Reserva eliminada para cliente ID " + clienteId);
            } else {
                System.out.println("No se encontró la reserva o no pertenece al usuario.");
            }
        } catch (SQLException e) {
            String errorMessage = "Error al eliminar la reserva: " + e.getMessage();
            System.out.println(errorMessage);
            LogUtils.log(serviceName, false, errorMessage);
        }
    }

}





