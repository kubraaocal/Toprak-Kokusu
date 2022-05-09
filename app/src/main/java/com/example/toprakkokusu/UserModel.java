package com.example.toprakkokusu;

public class UserModel {
    private String nameSurname,email,password,photo;

    public UserModel(){

    }

    public UserModel(String nameSurname, String email,String photo) {
        this.nameSurname = nameSurname;
        this.email = email;
        this.photo=photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
