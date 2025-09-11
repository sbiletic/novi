package model.services;

import model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private final List<Restaurant> restaurants = new ArrayList<>();

    public void add(Restaurant restaurant) {
        restaurants.add(restaurant);
        System.out.println("Available restaurants: " + restaurants);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants;

    }

    public Restaurant getRestaurant(int id) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == id) {
                return restaurant;
            }
        }
        return null;
    }
}
