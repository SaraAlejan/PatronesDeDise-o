import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/patrones";
    private String user = "root";
    private String password = "";

    // Constructor privado para el patrón Singleton
    private DatabaseConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
            throw ex;  // Manejo interno y propagación opcional.
        }
    }


    // Devuelve la instancia única
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            try {
                instance = new DatabaseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                instance = null;  // Configura la instancia como null si falla la conexión.
            }
        }
        return instance;
    }


    // Devuelve la conexión activa
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida a la base de datos.");
        }
        return connection;
    }

}
