import java.util.Scanner;

public class AuthSystem {

    private UserFactory userFactory;

    public AuthSystem(UserFactory factory) {
        this.userFactory = factory;
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su nombre:");
        String name = scanner.nextLine();
        System.out.println("Ingrese su correo:");
        String email = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String password = scanner.nextLine();

        userFactory.registerUser(name, email, password);
    }

    public void readUsers() {
        userFactory.readUsers();
    }

    public void updateUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el correo del usuario a actualizar:");
        String email = scanner.nextLine();
        System.out.println("Ingrese el nuevo nombre:");
        String newName = scanner.nextLine();
        System.out.println("Ingrese la nueva contraseña:");
        String newPassword = scanner.nextLine();

        userFactory.updateUser(email, newName, newPassword);
    }

    public void deleteUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el correo del usuario a eliminar:");
        String email = scanner.nextLine();

        userFactory.deleteUser(email);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione el tipo de usuario para registrar (1. Admin, 2. Regular):");
        int userType = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        UserFactory factory = null;

        if (userType == 1) {
            factory = new AdminUserFactory();
        } else {
            factory = new RegularUserFactory();
        }

        AuthSystem authSystem = new AuthSystem(factory);

        boolean running = true;

        while (running) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Leer usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");

            int option = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (option) {
                case 1:
                    authSystem.register();
                    break;
                case 2:
                    authSystem.readUsers();
                    break;
                case 3:
                    authSystem.updateUser();
                    break;
                case 4:
                    authSystem.deleteUser();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();
    }
}
