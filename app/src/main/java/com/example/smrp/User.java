package com.example.smrp;

public class User {
    String userId,email,password,name,gender,birth;

    public User(String id, String email, String password, String name, String gender, String birth) {
        this.userId = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
