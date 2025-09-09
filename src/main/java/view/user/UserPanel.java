package view.user;

import controller.Controller;
import com.toedter.calendar.JCalendar;
import model.Reservation;
import model.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class UserPanel extends JPanel {

    private JComboBox<Restaurant> restaurantComboBox;
    private JComboBox<String> timeComboBox;
    private JComboBox<Integer> guestsComboBox;
    private JTextArea notesArea;
    private JButton okButton;
    private JCalendar calendar;

    private final Controller controller = Controller.getInstance();

    public UserPanel() {
        setLayout(new BorderLayout());

        initializeComponents();
        layoutComponents();
        addEventListeners();
    }

    private void initializeComponents() {
        // Restaurant selection
        Restaurant[] restaurants = controller.getAllRestaurants().toArray(new Restaurant[0]);
        restaurantComboBox = new JComboBox<>(restaurants);

        // Time selection
        String[] times = {"12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
                "18:00", "18:30", "19:00", "19:30", "20:00", "20:30",
                "21:00", "21:30", "22:00"};
        timeComboBox = new JComboBox<>(times);

        // Guests selection
        Integer[] guestCounts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        guestsComboBox = new JComboBox<>(guestCounts);

        // Notes area
        notesArea = new JTextArea(8, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBorder(BorderFactory.createTitledBorder("Leave the note"));

        // OK button
        okButton = new JButton("OK");

        // Calendar component
        calendar = new JCalendar();
        calendar.setPreferredSize(new Dimension(300, 200));
        calendar.setLocale(Locale.ENGLISH);
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Left side - Restaurant and calendar
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.insets = new Insets(5, 5, 5, 5);

        // Restaurant selection
        leftGbc.gridx = 0; leftGbc.gridy = 0;
        leftGbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(new JLabel("Choose the restaurant:"), leftGbc);

        leftGbc.gridy = 1;
        leftGbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(restaurantComboBox, leftGbc);

        // Date selection label
        leftGbc.gridy = 2;
        leftGbc.fill = GridBagConstraints.NONE;
        leftGbc.insets = new Insets(15, 5, 5, 5);
        leftPanel.add(new JLabel("Choose the date:"), leftGbc);

        // Calendar
        leftGbc.gridy = 3;
        leftGbc.fill = GridBagConstraints.BOTH;
        leftGbc.weightx = 1.0;
        leftGbc.weighty = 1.0;
        leftGbc.insets = new Insets(5, 5, 5, 5);
        leftPanel.add(calendar, leftGbc);

        // Middle panel - Time and guests
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints middleGbc = new GridBagConstraints();
        middleGbc.insets = new Insets(5, 5, 5, 5);

        // Time selection
        middleGbc.gridx = 0; middleGbc.gridy = 0;
        middleGbc.anchor = GridBagConstraints.WEST;
        middlePanel.add(new JLabel("Choose the time:"), middleGbc);

        middleGbc.gridy = 1;
        middleGbc.fill = GridBagConstraints.HORIZONTAL;
        middlePanel.add(timeComboBox, middleGbc);

        // Guests selection
        middleGbc.gridy = 2;
        middleGbc.fill = GridBagConstraints.NONE;
        middleGbc.insets = new Insets(15, 5, 5, 5);
        middlePanel.add(new JLabel("Choose the number of guests:"), middleGbc);

        middleGbc.gridy = 3;
        middleGbc.fill = GridBagConstraints.HORIZONTAL;
        middleGbc.insets = new Insets(5, 5, 5, 5);
        middlePanel.add(guestsComboBox, middleGbc);

        // OK Button
        middleGbc.gridy = 4;
        middleGbc.anchor = GridBagConstraints.CENTER;
        middleGbc.insets = new Insets(30, 5, 5, 5);
        middlePanel.add(okButton, middleGbc);

        // Right panel - Notes
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(notesArea), BorderLayout.CENTER);

        // Add all panels to main panel
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.4; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(leftPanel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 0.3; gbc.weighty = 1.0;
        mainPanel.add(middlePanel, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        gbc.weightx = 0.3; gbc.weighty = 1.0;
        mainPanel.add(rightPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void addEventListeners() {
        // OK button action
        okButton.addActionListener(e -> {
            // TODO: Implement reservation logic
            Restaurant restaurant = (Restaurant) restaurantComboBox.getSelectedItem();
            String time = (String) timeComboBox.getSelectedItem();
            int guests = (int) guestsComboBox.getSelectedItem();
            String notes = notesArea.getText();

            Date selectedDate = calendar.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a date!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate localDate = selectedDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            System.out.println(localDate);
            System.out.println(restaurant);
            System.out.println(time);
            System.out.println(guests);
            System.out.println(notes);

            //dodati validaciju podataka iz panela i dodati session tracker ulogiranog usera

            Reservation reservation = new Reservation();
            reservation.setLocalDate(localDate);
            reservation.setNumberOfGuests(guests);
            reservation.setTime(time);
            reservation.setRestaurantId(restaurant.getId());
            reservation.setUsername(restaurant.getUsername());


            // Format date for display
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(selectedDate);
            String dateString = cal.get(java.util.Calendar.DAY_OF_MONTH) + "/" +
                    (cal.get(java.util.Calendar.MONTH) + 1) + "/" +
                    cal.get(java.util.Calendar.YEAR);

            JOptionPane.showMessageDialog(this,
                    "Reservation request submitted!\nRestaurant: " + restaurant +
                            "\nDate: " + dateString +
                            "\nTime: " + time + "\nGuests: " + guests,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}