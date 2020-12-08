package Modelo;

public class User {
    private String nombre;
    private String email;
    private String password;

    public String getName() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public User(){}
    public User(String name, String email, String password) {
        this.nombre = name;
        this.email = email;
        this.password = password;
    }
}
