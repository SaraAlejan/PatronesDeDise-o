import java.util.ArrayList;
import java.util.List;

public abstract class UserFactory {
    protected List<User> users = new ArrayList<>();

    public abstract User createUser(String name, String email, String password);

    public User registerUser(String name, String email, String password) {
        User user = createUser(name, email, password);
        users.add(user); //Añade el usuario
        System.out.println("Usuario " + user.getName() + " registrado con éxito como " + user.getRole());
        return user;
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

    public void readUsers() {
        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (User user : users) {
                System.out.println("Nombre: " + user.getName() + ", Email: " + user.getEmail() + ", Rol: " + user.getRole());
            }
        }
    }

    public void updateUser(String email, String newName, String newPassword) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.name = newName;
                user.password = newPassword;
                System.out.println("Usuario " + user.getEmail() + " actualizado con éxito.");
                return;
            }
        }
        System.out.println("Usuario con el email " + email + " no encontrado.");
    }

    public void deleteUser(String email) {
        User userToDelete = null;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                userToDelete = user;
                break;
            }
        }
        if (userToDelete != null) {
            users.remove(userToDelete);
            System.out.println("Usuario " + userToDelete.getEmail() + " eliminado con éxito.");
        } else {
            System.out.println("Usuario con el email " + email + " no encontrado.");
        }
    }
}
