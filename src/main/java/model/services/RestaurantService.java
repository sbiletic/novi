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
}
