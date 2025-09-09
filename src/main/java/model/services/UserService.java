package model.services;

import model.Role;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private final Map<String, User> userMap = new HashMap<>();

    public void addUser(User user) {
        userMap.put(user.getUsername(), user);
        System.out.println("User " + user.getUsername() + " added");
    }

    public void removeUser(String username) {
        userMap.remove(username);
        System.out.println("User " + username + " removed");
    }

    public User getUserByUsername(String username) {
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

    public List<User> getAllUsersWithRestaurantRole() {
        List<User> users = new ArrayList<>();

        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            if (entry.getValue().getRole() == Role.RESTAURANT) {
                users.add(entry.getValue());
            }
        }
        System.out.println("Users found: " + users.size());
        return users;
    }
}
