package com.example.toprakkokusu;

public class UserModel {
    private String nameSurname,email,password;

    public UserModel(){

    }

    public UserModel(String nameSurname, String email) {
        this.nameSurname = nameSurname;
        this.email = email;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
