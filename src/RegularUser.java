public class RegularUser extends User {

    public RegularUser(String name, String surname, String email, String password, String phone, String address, int cliente_id) {
        // Llama al constructor de User con todos los parámetros requeridos
        super(name, surname, email, password, phone, address, cliente_id);
    }

    @Override
    public String getRole() {
        return "Regular User"; // Rol asignado
    }
}

