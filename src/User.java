import java.io.Serializable;

public abstract class User implements Serializable {
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected String phone;
    protected String address;
    protected int cliente_id;

    // Constructor del usuario
    public User(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.cliente_id = cliente_id;
    }

    public abstract String getRole();

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public int getClienteId() { return cliente_id; }

    public boolean authenticate(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
}


