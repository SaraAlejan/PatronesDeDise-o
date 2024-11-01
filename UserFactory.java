import java.util.ArrayList;
import java.util.List;

public abstract class UserFactory {
    protected List<User> users = new ArrayList<>();

    public abstract User createUser(String name, String surname, String email, String password, String phone, String address, int cliente_id);

    public User registerUser(String name, String surname, String email, String password, String phone, String address) {
        int cliente_id = getClientIdByEmail(email); // Método que asumo ya tienes implementado
        User user = createUser(name, surname, email, password, phone, address, cliente_id); // Llama a createUser con todos los argumentos

        // Guardar cliente en la base de datos
        DatabaseUtils dbUtils = new DatabaseUtils();
        dbUtils.insertClient( name, surname, email, phone, address);

        users.add(user);
        System.out.println("Usuario " + user.getName() + " registrado con éxito como " + user.getRole());
        return user;
    }

    // Método auxiliar para obtener cliente_id
    public int getClientIdByEmail(String email) {
        // Código para obtener el cliente_id a partir del email
        return 1; // Valor de ejemplo
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
            user.setName(newName); // Asegúrate de tener un método setName en la clase User
            user.setPassword(newPassword); // Asegúrate de tener un método setPassword en la clase User
            System.out.println("Usuario " + user.getName() + " actualizado con éxito.");
            return true;
        } else {
            System.out.println("Error: Usuario no encontrado.");
            return false;
        }
    }

    // Método para eliminar un usuario
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


