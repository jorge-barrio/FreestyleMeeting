package Modelo;

public class UserEstacion extends User {

    private String cifEmpresa;

    public String getCifEmpresa() {
        return cifEmpresa;
    }

    public UserEstacion(){}
    public UserEstacion(String name, String email, String password, String cif){
        super(name,email,password);
        this.cifEmpresa=cif;
    }
}
