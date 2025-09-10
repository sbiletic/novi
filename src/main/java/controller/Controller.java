package controller;

import model.Reservation;
import model.Restaurant;
import model.User;
import model.services.ReservationService;
import model.services.RestaurantService;
import model.services.UserService;

import java.util.List;

public class Controller {
    private final static UserService userService = new UserService();
    private static Controller instance;
    private static User loggedInUser;
    private final static RestaurantService restaurantService = new RestaurantService();
    private final static ReservationService reservationService = new ReservationService();
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public static User getLoggedInUser() {
        if (loggedInUser == null) {
            loggedInUser = new User();
        }
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public List<Reservation> getUserReservations(String username) {
        return reservationService.getUserReservations(username);
    }

    public void addUser(User user) {
        userService.addUser(user);
    }

    public void removeUser(String username) {
         userService.removeUser(username);
    }
    public User getUserByUsername(String username) {
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

    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    public void addReservation(Reservation reservation) {
        reservationService.add(reservation);
    }

    //TODO dovrsiti ovu metodu, dodati u restaurant service klasi metodu koja vraca objekt restaurant po parametru
    //TODO tipa String restaurantId
    public Restaurant getRestaurantById(int restaurantId) {
    }
}
