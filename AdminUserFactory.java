public class AdminUserFactory extends UserFactory {

    @Override
    public User createUser(String name, String email, String password) {
        return new AdminUser(name, email, password);
    }
}