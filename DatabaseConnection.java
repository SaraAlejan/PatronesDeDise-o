import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/patrones"; // Cambia "tu_base_de_datos" por el nombre real de tu base de datos en WAMP.
    private String username = "root"; // En WAMP, el usuario por defecto es "root".
    private String password = ""; // En WAMP, la contraseña por defecto de "root" suele estar en blanco.

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}



