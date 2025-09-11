package model.services;

import model.Reservation;

import java.util.ArrayList;
import java.util.List;


public class ReservationService {
    public final List<Reservation> reservationList = new ArrayList<>();

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
        for (Reservation reservation : reservationList) {
            if (reservation.getId() == reservationId) {
                reservationList.remove(reservation);
                System.out.println("Reservation with id " + reservationId + " removed");
                return true;
            } else {
                System.out.println("Reservation with id " + reservationId + " not found");
                return false;
            }
        }
        return false;
    }
}
