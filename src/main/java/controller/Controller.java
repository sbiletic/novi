package controller;

import model.Restaurant;
import model.User;
import model.services.RestaurantService;
import model.services.UserService;

import java.util.List;

public class Controller {
    private final static UserService userService = new UserService();
    private static Controller instance;
    private final static RestaurantService restaurantService = new RestaurantService();
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void addUser(User user) {
        userService.addUser(user);
    }

    public void removeUser(int username) {
         userService.removeUser(username);
    }
    public User getUserByUsername(int username) {
        return userService.getUserByUsername(username);
    }

    public boolean attemptLogin(String username, String password) {
        return userService.attemptLogin(username, password);
    }

    public List<User> getAllUsersWithRestaurantRole(){
        return userService.getAllUsersWithRestaurantRole();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantService.add(restaurant);
    }
}
