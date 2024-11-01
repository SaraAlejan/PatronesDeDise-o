import java.util.Scanner;

public class AuthSystem {
    private UserFactory userFactory;
    private Scanner scanner = new Scanner(System.in);
    private User loggedInUser = null; // Usuario logueado

    public AuthSystem(UserFactory factory) {
        this.userFactory = factory;
    }

    public void register() {
        System.out.println("Ingrese su nombre:");
        String name = scanner.nextLine();

        System.out.println("Ingrese su apellido:");
        String surname = scanner.nextLine();

        System.out.println("Ingrese su correo:");
        String email = scanner.nextLine();

        System.out.println("Ingrese su teléfono:");
        String phone = scanner.nextLine();

        System.out.println("Ingrese su dirección:");
        String address = scanner.nextLine();

        System.out.println("Ingrese su contraseña:");
        String password = scanner.nextLine();

        if (userFactory != null) {
            userFactory.registerUser(name, surname, email, phone, address, password);
        } else {
            System.out.println("Error: No se ha seleccionado un tipo de usuario.");
        }
    }

    public void login() {
        System.out.println("Ingrese su correo:");
        String email = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String password = scanner.nextLine();

        User user = userFactory.loginUser(email, password);
        if (user != null) {
            loggedInUser = user;
            System.out.println("Inicio de sesión exitoso. Bienvenido, " + user.getName() + ".");
        }
    }

    public void handleReservations() {
        if (loggedInUser == null) {
            System.out.println("Debe iniciar sesión primero.");
            return;
        }

        System.out.println("Ingrese el número de habitación:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.println("Ingrese la fecha de check-in (YYYY-MM-DD):");
        String checkInDate = scanner.nextLine();
        System.out.println("Ingrese la fecha de check-out (YYYY-MM-DD):");
        String checkOutDate = scanner.nextLine();

        BookingSystem bookingSystem = BookingSystem.getInstance();
        bookingSystem.makeReservation(loggedInUser.getClienteId(), roomNumber, checkInDate, checkOutDate);
    }

    public void readUsers() {
        userFactory.readUsers();
    }

    public void updateUser() {
        System.out.println("Ingrese el correo del usuario a actualizar:");
        String email = scanner.nextLine();
        System.out.println("Ingrese el nuevo nombre:");
        String newName = scanner.nextLine();
        System.out.println("Ingrese la nueva contraseña:");
        String newPassword = scanner.nextLine();
        userFactory.updateUser(email, newName, newPassword);
    }

    public void deleteUser() {
        System.out.println("Ingrese el correo del usuario a eliminar:");
        String email = scanner.nextLine();
        userFactory.deleteUser(email);
    }

    public void adminMenu() {
        boolean adminMenuRunning = true;

        while (adminMenuRunning) {
            System.out.println("\nOpciones de Admin:");
            System.out.println("1. Eliminar usuario");
            System.out.println("2. Actualizar usuario");
            System.out.println("3. Registrar usuario");
            System.out.println("4. Leer usuarios");
            System.out.println("5. Salir");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> deleteUser();
                case 2 -> updateUser();
                case 3 -> register();
                case 4 -> readUsers();
                case 5 -> adminMenuRunning = false;
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public void adminAuthentication() {
        System.out.println("Opciones de administrador:");
        System.out.println("1. Registrarse como administrador");
        System.out.println("2. Iniciar sesión como administrador");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        if (choice == 1) {
            register();
            System.out.println("Registro completado. Ahora inicie sesión.");
            login();
        } else if (choice == 2) {
            login();
        } else {
            System.out.println("Opción no válida. Intente nuevamente.");
        }

        // Si el inicio de sesión fue exitoso, mostrar el menú de administración
        if (loggedInUser != null) {
            adminMenu();
        }
    }

    public void userMenu() {
        boolean userMenuRunning = true;

        while (userMenuRunning) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> register();
                case 2 -> {
                    login();
                    if (loggedInUser != null) {
                        reservationMenu();
                    }
                }
                case 3 -> userMenuRunning = false;
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public void reservationMenu() {
        boolean reservationMenuRunning = true;

        while (reservationMenuRunning) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Hacer reserva");
            System.out.println("2. Cerrar sesión");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleReservations();
                case 2 -> {
                    loggedInUser = null;
                    reservationMenuRunning = false;
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de usuario (1. Usuario, 2. Administrador):");
        int userType = scanner.nextInt();
        scanner.nextLine();

        UserFactory factory;
        AuthSystem authSystem;

        if (userType == 1) {
            factory = new RegularUserFactory();
            authSystem = new AuthSystem(factory);
            authSystem.userMenu();

        } else if (userType == 2) {
            factory = new AdminUserFactory();
            authSystem = new AuthSystem(factory);
            authSystem.adminAuthentication();

        } else {
            System.out.println("Opción no válida.");
        }

        scanner.close();
    }
}



