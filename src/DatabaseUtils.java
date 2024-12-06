import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    // Registrar un administrador en la base de datos
    public void insertAdmin(String name, String surname, String email, String password) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String query = "INSERT INTO clientes (nombre, apellido, email, password) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);  // Almacena la contraseña sin cifrar
                preparedStatement.executeUpdate();

                System.out.println("Administrador registrado con éxito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verificar el login del administrador
    public AdminUser fetchAdminByEmail(String email, String password) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM clientes WHERE email = ? AND password = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);  // Compara la contraseña sin cifrar
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new AdminUser(
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("email"),
                            password,
                            resultSet.getString("telefono"),
                            "",  // Dirección vacía
                            resultSet.getInt("cliente_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el administrador: " + e.getMessage());
        }
        return null;
    }
}






