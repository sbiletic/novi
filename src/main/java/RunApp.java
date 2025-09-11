import com.formdev.flatlaf.FlatIntelliJLaf;
import model.User;
import view.login.LoginFrame;
import view.restaurant.ReservationFrame;
import view.user.UserFrame;

import javax.swing.*;

public class RunApp {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        SwingUtilities.invokeLater(() -> {
            ReservationFrame loginFrame = new ReservationFrame();
        });
    }
}
