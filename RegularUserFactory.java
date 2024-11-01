import java.util.ArrayList;
import java.util.List;

public class RegularUserFactory extends UserFactory {
    private static int currentClientId = 1; // Contador para generar IDs únicos

    @Override
    public User createUser(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        return new RegularUser(name, surname, email, password, phone, address, cliente_id);
    }

    public User registerUser(String name, String surname, String email, String phone, String address, String password) {
        // Genera un nuevo cliente_id para cada registro
        int cliente_id = generateClientId();

        // Crea el usuario utilizando el cliente_id generado
        User user = createUser(name, surname, email, password, phone, address, cliente_id);

        // Guardar cliente en la base de datos
        DatabaseUtils dbUtils = new DatabaseUtils();
        dbUtils.insertClient(name, surname, email, phone, address);

        // Agrega el usuario a la lista de usuarios
        users.add(user);
        System.out.println("Usuario " + user.getName() + " registrado con éxito como " + user.getRole());
        return user;
    }

    // Método para generar un nuevo cliente_id
    private int generateClientId() {
        return currentClientId++;
    }

    public User loginUser(String email, String password) {
        for (User user : users) {
            if (user.authenticate(email, password)) {
                System.out.println("Inicio de sesión exitoso. Bienvenido, " + user.getName() + ".");
                return user;
            }
        }
        System.out.println("Error: Email o contraseña incorrectos.");
        return null;
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean updateUser(String email, String newName, String newPassword) {
        User user = findUserByEmail(email);
        if (user != null) {
            user.setName(newName);
            user.setPassword(newPassword);
            System.out.println("Usuario " + user.getName() + " actualizado con éxito.");
            return true;
        } else {
            System.out.println("Error: Usuario no encontrado.");
            return false;
        }
    }

    public boolean deleteUser(String email) {
        User user = findUserByEmail(email);
        if (user != null) {
            users.remove(user);
            System.out.println("Usuario con correo " + email + " eliminado con éxito.");
            return true;
        } else {
            System.out.println("Error: Usuario no encontrado.");
            return false;
        }
    }

    public void readUsers() {
        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("Usuarios registrados:");
            for (User user : users) {
                System.out.println("Nombre: " + user.getName() + ", Email: " + user.getEmail() + ", Rol: " + user.getRole());
            }
        }
    }
}

