import java.sql.*;

public class RegularUserFactory extends UserFactory {
    protected Connection connection;

    // Constructor inicializa la conexión a la base de datos
    public RegularUserFactory() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public User createUser(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        return new RegularUser(name, surname, email, password, phone, address, cliente_id);
    }

    public User registerUser(String name, String surname, String email, String phone, String address, String password) {
        try {
            if (isEmailRegistered(email)) {
                System.out.println("Error: El email ya está registrado.");
                return null;
            }

            String sql = "INSERT INTO clientes (nombre, apellido, email, password, telefono, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, NOW())";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setString(2, surname);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, phone);
                stmt.setString(6, address);

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int cliente_id = rs.getInt(1);
                    System.out.println("Usuario registrado exitosamente con ID: " + cliente_id);
                    return createUser(name, surname, email, password, phone, address, cliente_id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
        return null;
    }

    public boolean isEmailRegistered(String email) throws SQLException {
        String query = "SELECT * FROM clientes WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public User loginUser(String email, String password) {
        try {
            String query = "SELECT * FROM clientes WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int cliente_id = rs.getInt("cliente_id");
                    String name = rs.getString("nombre");
                    String surname = rs.getString("apellido");
                    String phone = rs.getString("telefono");
                    String address = rs.getString("direccion");

                    System.out.println("Inicio de sesión exitoso para: " + name);
                    return createUser(name, surname, email, password, phone, address, cliente_id);
                } else {
                    System.out.println("Error: Email o contraseña incorrectos.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }
        return null;
    }
}




