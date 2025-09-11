package view.restaurant;

import javax.swing.*;

public class ReservationFrame extends JFrame {

    public ReservationFrame() {
        setTitle("Reservations - Restaurant");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(new ReservationMenuBar(this));

        add(new ReservationPanel(this));

        setVisible(true);
    }
}
