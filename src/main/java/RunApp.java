import com.formdev.flatlaf.FlatIntelliJLaf;
import view.login.LoginFrame;
import view.restaurant.ReservationFrame;

import javax.swing.*;

public class RunApp {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
        });
    }
}
