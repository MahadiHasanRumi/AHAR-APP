package com.example.aahar;

public class ModelUser {
    private String phone,password,name;

    public ModelUser(String mail, String password, String name) {
        this.phone = mail;
        this.password = password;
        this.name = name;
    }

    public ModelUser() {
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
