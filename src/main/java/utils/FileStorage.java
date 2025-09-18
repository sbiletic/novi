package utils;

import model.Reservation;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    // ✅ Spremanje u BIN ili TXT ovisno o ekstenziji datoteke
    public static void saveReservations(List<Reservation> reservations, File file) {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".bin")) {
            saveReservationsToBin(reservations, file);
        } else if (name.endsWith(".txt")) {
            saveReservationsToTxt(reservations, file);
        } else {
            throw new IllegalArgumentException("Unsupported file format! Use .bin or .txt");
        }
    }

    // ✅ Učitavanje iz BIN ili TXT ovisno o ekstenziji
    public static List<Reservation> loadReservations(File file) {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".bin")) {
            return loadReservationsFromBin(file);
        } else if (name.endsWith(".txt")) {
            return loadReservationsFromTxt(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format! Use .bin or .txt");
        }
    }

    // ---------------- BIN ----------------
    private static void saveReservationsToBin(List<Reservation> reservations, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Reservation> loadReservationsFromBin(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Reservation>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // ---------------- TXT ----------------
    private static void saveReservationsToTxt(List<Reservation> reservations, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Reservation r : reservations) {
                writer.write(r.getId() + "," + r.getUsername() + "," + r.getRestaurantId() + ","
                        + r.getLocalDate() + " " + r.getTime() + "," + r.getNumberOfGuests() + "," + r.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Reservation> loadReservationsFromTxt(File file) {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Reservation r = new Reservation();
                    r.setId(Integer.parseInt(parts[0]));
                    r.setUsername(parts[1]);
                    r.setRestaurantId(Integer.parseInt(parts[2]));

                    String[] dateTime = parts[3].split(" ");
                    r.setLocalDate(LocalDate.parse(dateTime[0]));
                    r.setTime(dateTime[1]);

                    r.setNumberOfGuests(Integer.parseInt(parts[4]));
                    r.setStatus(Enum.valueOf(model.ReservationStatus.class, parts[5]));
                    reservations.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
