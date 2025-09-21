package model.services;

import model.Reservation;
import model.ReservationStatus;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final List<Reservation> reservationList = new ArrayList<>();

    public void add(Reservation reservation) {
        reservationList.add(reservation);
        System.out.println("Reservation added " + reservation);
    }

    public List<Reservation> getUserReservations(String username) {
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (reservation.getUsername().equals(username)) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }

    public boolean removeUserReservation(int reservationId) {
        boolean removed = reservationList.removeIf(r -> r.getId() == reservationId);
        if (removed) {
            System.out.println("Reservation with id " + reservationId + " removed");
        } else {
            System.out.println("Reservation with id " + reservationId + " not found");
        }
        return removed;
    }

    public List<Reservation> getReservationsByRestaurantId(int restaurantId) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (reservation.getRestaurantId() == restaurantId) {
                reservations.add(reservation);
                System.out.println("Reservation with id " + reservation.getId() + " added");
            }
        }
        return reservations;
    }

    public void updateReservation(int reservationId, ReservationStatus reservationStatus) {
        for (Reservation reservation : reservationList) {
            if (reservation.getId() == reservationId) {
                reservation.setStatus(reservationStatus);
                System.out.println("Reservation with id " + reservationId + " updated");
                break; // stop after first match
            }
        }
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservationList); // defensive copy
    }

    public void loadReservations(List<Reservation> reservationsFromFile) {
        reservationsFromFile.stream()
                .filter(rFromFile -> reservationList.stream()
                        .noneMatch(r -> r.getId() == rFromFile.getId()))
                .forEach(reservationList::add);

        System.out.println("Reservation list loaded: " + reservationList.size() + " reservations");
    }
}
