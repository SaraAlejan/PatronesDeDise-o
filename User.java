public abstract class User {
    protected String name;
    protected String email;
    protected String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public abstract String getRole();

    // Métodos comunes para todos los usuarios
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean authenticate(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
