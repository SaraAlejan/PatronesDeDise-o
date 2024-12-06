import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public abstract class UserFactory {
    protected Connection connection;

    // Constructor que inicializa la conexión a la base de datos
    public UserFactory() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            this.connection = null;
        }
    }

    // Crear un nuevo usuario
    public User createUser(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        return new Cliente(name, surname, email, password, phone, address, cliente_id);
    }

    // Registrar un nuevo usuario en la base de datos
    public User registerUser(String name, String surname, String email, String phone, String address, String password) {

        String sql = "INSERT INTO clientes (nombre, apellido, email, telefono, direccion, password, fecha_registro) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Verificar si el email ya está registrado
            if (isEmailRegistered(conn, email)) {
                System.out.println("Error: El email ya está registrado.");
                return null;
            }

            // Configurar los parámetros de la consulta
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, password);

            // Ejecutar la consulta
            stmt.executeUpdate();

            // Obtener la clave generada automáticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int clienteId = rs.getInt(1);
                    System.out.println("Usuario registrado exitosamente con ID: " + clienteId);
                    return createUser(name, surname, email, password, phone, address, clienteId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }

        return null;
    }





    // Iniciar sesión verificando email y contraseña
    public User loginUser(String email, String password) {

        String sql = "SELECT cliente_id, nombre, apellido, email, telefono, direccion, password " +
                "FROM clientes WHERE email = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                String name = rs.getString("nombre");
                String surname = rs.getString("apellido");
                String phone = rs.getString("telefono");
                String address = rs.getString("direccion");

                System.out.println("Inicio de sesión exitoso para: " + name + " " + surname);
                return createUser(name, surname, email, password, phone, address, clienteId);
            } else {
                System.out.println("Error: Email o contraseña incorrectos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }

        return null;
    }




    // Leer todos los usuarios desde la base de datos
    public List<User> readUsersFromDatabase() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT cliente_id, nombre, apellido, email, telefono, direccion FROM clientes";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                String name = rs.getString("nombre");
                String surname = rs.getString("apellido");
                String email = rs.getString("email");
                String phone = rs.getString("telefono");
                String address = rs.getString("direccion");

                userList.add(createUser(name, surname, email, null, phone, address, clienteId));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }
        return userList;
    }

    // Actualizar información del usuario en la base de datos
    public boolean updateUserInDatabase(String email, String newName, String newPassword) {
        String sql = "UPDATE clientes SET nombre = ?, password = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setString(2, newPassword);
            stmt.setString(3, email);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un usuario de la base de datos
    public boolean deleteUserInDatabase(String email) {
        String sql = "DELETE FROM clientes WHERE email = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario eliminado con éxito.");
                return true;
            } else {
                System.out.println("No se encontró un usuario con ese correo.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
            return false;
        }
    }



    // Verificar si un email ya existe en la base de datos
    private boolean isEmailRegistered(Connection conn, String email) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar email: " + e.getMessage());
        }

        return false;
    }




}





