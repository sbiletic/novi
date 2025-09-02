import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import view.login.LoginFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class RunApp {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
        });
    }
}
