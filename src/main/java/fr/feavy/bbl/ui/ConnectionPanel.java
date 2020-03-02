package fr.feavy.bbl.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConnectionPanel extends JPanel {
    public ConnectionPanel(ConnectionPanelEvent callback) {
        super(new GridLayout(0, 1, 5, 5));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JComboBox<String> interfaceCombobox = new JComboBox<>(new String[]{"Ethernet", "Wi-Fi"});
        interfaceCombobox.setEditable(true);

        JTextField loginField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton startButton = new JButton("Ok");
        startButton.addActionListener(e -> callback.onLogged((String)interfaceCombobox.getSelectedItem(),
                loginField.getText(),
                passField.getText())
        );

        add(new JLabel("Interface :"));
        add(interfaceCombobox);
        add(new JLabel("Pseudo :"));
        add(loginField);
        add(new JLabel("Passe : "));
        add(passField);
        add(new JLabel());
        add(startButton);
    }

    public static interface ConnectionPanelEvent {
        void onLogged(String theInterface, String login, String pass);
    }
}
