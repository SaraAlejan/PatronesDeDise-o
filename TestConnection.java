import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/patrones"; // Cambia 'patrones' por el nombre de tu base de datos
        String user = "root"; // Cambia 'tu_usuario' por tu usuario de MySQL
        String password = ""; // Cambia 'tu_contraseña' por tu contraseña de MySQL

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
