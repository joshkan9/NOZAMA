package org.example.view;

import javax.swing.*;
import java.awt.*;

public class SQLQueries extends JPanel {
    public SQLQueries(JFrame window) {
            setSize(700, 700);
            JLabel welcomeLabel = new JLabel("Welcome!");
            welcomeLabel.setForeground(Color.white);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
            welcomeLabel.setBounds(100, 100, 200, 50);
            add(welcomeLabel);
            window.add(this);
            window.setSize(1000,1000);

            // Refresh the window to show the new panel
            window.revalidate();
            window.repaint();
    }
}
