public class RegularUser extends User {

    public RegularUser(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String getRole() {
        return "Regular User";
    }
}