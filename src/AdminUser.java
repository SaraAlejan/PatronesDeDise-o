public class AdminUser extends User {
    private String surname;
    private String phone;
    private String address;

    public AdminUser(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        super(name, surname, email, password, phone, address, cliente_id);
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}





