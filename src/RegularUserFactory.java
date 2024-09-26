public class RegularUserFactory extends UserFactory {

    @Override
    public User createUser(String name, String email, String password) {
        return new RegularUser(name, email, password);
    }
