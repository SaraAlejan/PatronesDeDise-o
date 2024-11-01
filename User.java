public abstract class User {
    protected String name;
    protected String surname; // Añadido
    protected String email;
    protected String password;
    protected String phone;    // Añadido
    protected String address;  // Añadido
    protected int cliente_id;

    // Modificar constructor
    public User(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;  // Inicializa password
        this.phone = phone;        // Inicializa phone
        this.address = address;    // Inicializa address
        this.cliente_id = cliente_id;
    }

    public abstract String getRole();

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname; // Método para obtener el apellido
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone; // Método para obtener el teléfono
    }

    public String getAddress() {
        return address; // Método para obtener la dirección
    }

    public int getClienteId() {
        return cliente_id;
    }

    public boolean authenticate(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Métodos set para poder actualizar los datos
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

