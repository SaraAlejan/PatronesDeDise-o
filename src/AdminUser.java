public class AdminUser extends User {

    public AdminUser(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String getRole() {
        return "Administrator";
    }
}
