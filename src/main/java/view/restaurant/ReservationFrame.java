package view.restaurant;

import javax.swing.*;

public class ReservationFrame extends JFrame {

    public ReservationFrame() {
        JFrame frame = new JFrame("Reservations");
        ReservationPanel reservationPanel = new ReservationPanel(frame);
        ReservationMenuBar menuBar = new ReservationMenuBar(frame, reservationPanel);

        frame.setJMenuBar(menuBar);
        frame.add(reservationPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
