package model.services;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> userMap = new HashMap<>();

    public void addUser(User user) {
        userMap.put(user.getUsername(), user);
        System.out.println("User " + user.getUsername() + " added");
    }

    public void removeUser(int username) {
        userMap.remove(username);
        System.out.println("User " + username + " removed");
    }

    public User getUserByUsername(int username) {
        System.out.println("User " + username + " found");
        return userMap.get(username);
    }

    public boolean attemptLogin(String username, String password) {
        if (userMap.containsKey(username)) {
            User user = userMap.get(username);
            return user.getPassword().equals(password);
        } else {
            return false;
        }
    }
}
