package dam.code.models;

import dam.code.models.utils.Rol;

public class User {
    private int id;
    private String userNickName;
    private String name;
    private String surname;
    private String email;
    private Rol rol;

    public User(String userNickName, String nombre, String surname, String email) {
        this.userNickName = userNickName;
        this.name = nombre;
        this.surname = surname;
        this.email = email;
        this.rol = Rol.COLABORATOR;
    }

    public User(String userNickName, String nombre, String email, Rol rol) {
        this.userNickName = userNickName;
        this.name = nombre;
        this.email = email;
        this.rol = rol;
    }

    public User(int id, String userNickName, String nombre, String email, Rol rol) {
        this.id = id;
        this.userNickName = userNickName;
        this.name = nombre;
        this.email = email;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
