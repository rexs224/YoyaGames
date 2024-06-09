package com.example.app;

public class User {
    private String nick;
    private String mail;
    private String contrasena;

    public User() {
    }

    public User(String name, String email, String password) {
        this.nick = nick;
        this.mail = mail;
        this.contrasena = contrasena;
    }

    public String getNick() {
        return nick;
    }

    public void setName(String nick) {
        this.nick = nick;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

