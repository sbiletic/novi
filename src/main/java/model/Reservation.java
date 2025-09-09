package model;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class Reservation {
    private int id = ThreadLocalRandom.current().nextInt(1, 999999);
    private int restaurantId;
    private int username;
    private int numberOfGuests;
    private LocalDate date;
    private String time;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalDate getLocalDate() {
        return date;
    }

    public void setLocalDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", userId=" + username +
                ", numberSeats=" + numberOfGuests +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

