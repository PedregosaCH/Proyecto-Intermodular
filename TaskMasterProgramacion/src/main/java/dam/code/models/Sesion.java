package dam.code.models;

import dam.code.models.utils.Rol;

public class Sesion {
    private static User userActual;

    public static void setUser(User usuario) {
        userActual = usuario;
    }

    public static User getUsuario() {
        return userActual;
    }

    public static Rol getRol() {
        return userActual.getRol();
    }
}
