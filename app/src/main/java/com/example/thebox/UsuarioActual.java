package com.example.thebox;

public class UsuarioActual {
    private String nombres,apellidos,correo,nomUsuario;
    private static UsuarioActual user;

    public UsuarioActual(String nomUsuario,String nombres, String apellidos, String correo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.nomUsuario = nomUsuario;
    }

    public static void setUser(UsuarioActual user) {
        UsuarioActual.user = user;
    }

    public static UsuarioActual getUser() {
        return user;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }
}
