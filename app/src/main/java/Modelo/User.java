package Modelo;

public class User {
    private String nickname;
    private String email;
    private String password;

    public String getName() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public User(){}
    public User(String name, String email, String password) {
        this.nickname = name;
        this.email = email;
        this.password = password;
    }
}
