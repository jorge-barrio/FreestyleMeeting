package Modelo;

public class User {
    private String nombre;
    private String nombreCompleto;
    private String email;
    private String password;
    private Estacion[] estaciones;
    private Pista[] pistas;

    public String getName() {
        return nombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String surname, String email, String password) {
        this.nombre = name;
        this.nombreCompleto = surname;
        this.email = email;
        this.password = password;
    }
}
