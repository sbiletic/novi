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
}
