public class AdminUserFactory extends UserFactory {

    @Override
    public User createUser(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        return new AdminUser(name, surname, email, password, phone, address, cliente_id);
    }

    @Override
    public User loginUser(String email, String password) {
        DatabaseUtils dbUtils = new DatabaseUtils();
        AdminUser adminUser = dbUtils.fetchAdminByEmail(email, password);

        if (adminUser != null) {
            System.out.println("Inicio de sesión exitoso. Bienvenido, Admin " + adminUser.getName());
            return adminUser;
        } else {
            System.out.println("Error: Email o contraseña incorrectos.");
            return null;
        }
    }
}
