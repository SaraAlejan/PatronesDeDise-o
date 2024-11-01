public class AdminUser extends User {
    private String surname;
    private String phone;
    private String address;

    public AdminUser(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        super(name, surname, email, password, phone, address, cliente_id); // Pasa todos los parámetros a la superclase
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String getRole() {
        return "Admin"; // Rol asignado
    }

    // Métodos getters para los nuevos campos
    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}




