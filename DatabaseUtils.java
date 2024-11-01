import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {
    private String url = "jdbc:mysql://localhost:3306/patrones"; // Cambia "patrones" por el nombre de tu base de datos
    private String user = "root";
    private String password = "";

    public void insertClient(String nombre, String apellido, String email, String telefono, String direccion) {
        String query = "INSERT INTO clientes (nombre, apellido, email, telefono, direccion) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, telefono);
            preparedStatement.setString(5, direccion);
            preparedStatement.executeUpdate();

            System.out.println("Cliente registrado con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al guardar el cliente: " + e.getMessage());
        }
    }

    public AdminUser fetchAdminByEmail(String email, String password) {
        String query = "SELECT * FROM empleado WHERE email = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new AdminUser(
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("email"),
                        password,
                        resultSet.getString("telefono"),
                        "",  // Dirección vacía para empleados
                        resultSet.getInt("empleado_id")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener administrador: " + e.getMessage());
        }
        return null;
    }
}

