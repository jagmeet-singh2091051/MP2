package com.example.mpa;

public class Person {
    private String email, password, name, gen, dob, location;


    public Person(String email, String password, String name, String gen, String dob, String location) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gen = gen;
        this.dob = dob;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGen() { return gen; }

    public void setGen(String gen) { this.gen = gen; }

}


