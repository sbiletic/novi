package model;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class User implements Serializable{
    private int id = ThreadLocalRandom.current().nextInt(1, 999999);
    private String username;
    private String password;
    private int phoneNumber;
    private Role role;

    @Override
    public String toString() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
