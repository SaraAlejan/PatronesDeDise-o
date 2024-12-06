public class Cliente extends User {

    public Cliente(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        super(name, surname, email, password, phone, address, cliente_id);
    }

    @Override
    public String getRole() {
        return "Cliente";
    }
}



