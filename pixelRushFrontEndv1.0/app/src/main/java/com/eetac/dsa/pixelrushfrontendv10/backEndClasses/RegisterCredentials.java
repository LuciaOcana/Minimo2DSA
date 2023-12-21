package com.eetac.dsa.pixelrushfrontendv10.backEndClasses;

public class RegisterCredentials {
    String username;
    String password;
    String name;
    String surname;
    String mail;
    String birthDate;

    public RegisterCredentials(String username, String password, String name, String surname, String mail, String birthDate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.birthDate = birthDate;
    }
}
