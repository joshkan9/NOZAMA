package org.example.model;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    private JFrame mainFrame;
    private JPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }

    public MainApp() {
        createMainFrame();
    }

    private void createMainFrame() {
        mainFrame = new JFrame("Main Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Top Panel for Logo and Title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon("src/main/resources/nozama.png")); // Add the path to your logo file
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(logoLabel);

        // Title
        JLabel titleLabel = new JLabel("E-Commerce Project", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);

        mainPanel.add(topPanel);

        // Members' Contact Information
        JPanel membersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        membersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] members = {
                "Joshua Kang", "Tyler Michael Cairney", "Brandon Raggihanti", "Christina Situ"
        };

        for (String member : members) {
            membersPanel.add(new JLabel(member));
        }

        mainPanel.add(membersPanel);

        // Project Description using JLabel with HTML for wrapping and centering
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center; width: 400px;'>"
                + "This project is an E-commerce application that allows users to manage products, orders, and customers. "
                + "It provides detailed analytics and reports based on sales data."
                + "</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        descriptionPanel.add(descriptionLabel);
        mainPanel.add(descriptionPanel);

        // Button to go to QueryApp
        JPanel buttonPanel = new JPanel();
        JButton queryAppButton = new JButton("Go to Query Application");
        queryAppButton.addActionListener(e -> openQueryApp());
        buttonPanel.add(queryAppButton);

        mainPanel.add(buttonPanel);

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void openQueryApp() {
        mainFrame.dispose(); // Close the main application frame
        SwingUtilities.invokeLater(QueryApp::new); // Open the QueryApp
    }
}
