package Modelo;

public class Client extends User{
    private String nombreCompleto;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Client(){}
    public Client(String name, String email, String password, String nombreCompleto){
        super(name,email,password);
        this.nombreCompleto=nombreCompleto;
    }
}
