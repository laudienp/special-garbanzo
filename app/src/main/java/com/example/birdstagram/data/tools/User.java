package com.example.birdstagram.data.tools;

import com.example.birdstagram.tools.DatabaseHelper;

public class User {

    private int id;
    private String pseudo;
    private String name;
    private String surname;
    private int age;
    private String mail;
    private String password;

    public User(int id, String pseudo, String name, String surname, int age, String mail, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }
    public User(String name, String surname, String pseudo, int age, String mail, String password) {
        this.pseudo = pseudo;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
