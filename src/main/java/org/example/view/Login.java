package org.example.view;

import org.example.controller.ButtonController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    JFrame window;
    JPanel loginPanel;
    JPanel registerPanel;
    JLabel loginNameLabel;
    JLabel signUpLabel;
    JLabel emailLabel;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JTextField emailField;
    JButton loginButton;
    JButton registerButton;

    public void createLogin(ButtonController theChoice) {

        // WINDOW
        window = new JFrame();
        window.setSize(640, 640);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);

        window.setLocationRelativeTo(null);

        // LOGO
        ImageIcon logo = new ImageIcon("src/main/resources/nozama.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(0, 0, 640, 200);
        window.add(logoLabel);

        // LOGIN PANEL
        loginPanel = new JPanel();
        int panelWidth = 280;
        int panelHeight = 500;
        int xPosition = (window.getWidth() - panelWidth) / 2;
        int yPosition = (window.getHeight() - panelHeight) / 2;
        loginPanel.setBounds(xPosition, yPosition, panelWidth, panelHeight);
        loginPanel.setBackground(Color.gray);
        loginPanel.setLayout(null); // Use null layout for absolute positioning

        // LOGIN LABEL
        loginNameLabel = new JLabel("LOGIN");
        loginNameLabel.setForeground(Color.white);
        loginNameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        loginNameLabel.setBounds(40, 90, 200, 50);
        loginPanel.add(loginNameLabel);

        // EMAIL LABEL
        emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.white);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setBounds(40, 140, 200, 30);
        loginPanel.add(emailLabel);

        // EMAIL FIELD
        emailField = new JTextField();
        emailField.setBounds(40, 170, 200, 30);
        loginPanel.add(emailField);

        // PASSWORD LABEL
        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setBounds(40, 200, 200, 30);
        loginPanel.add(passwordLabel);

        // PASSWORD FIELD
        passwordField = new JPasswordField();
        passwordField.setBounds(40, 230, 200, 30);
        loginPanel.add(passwordField);


        // LOGIN BUTTON
        loginButton = new JButton("Login");
        loginButton.setBounds(90, 280, 100, 30);
        loginPanel.add(loginButton);
        loginButton.addActionListener(theChoice);

        // REGISTER BUTTON
        registerButton = new JButton("Register");
        registerButton.setBounds(90, 320, 100, 30);
        registerButton.setActionCommand("Register");
        loginPanel.add(registerButton);
        registerButton.addActionListener(theChoice);



        window.add(loginPanel);

        window.setVisible(true);
    }

    public void registerPage() {
        window.remove(loginPanel);

        registerPanel = new JPanel();
        int panelWidth = 280;
        int panelHeight = 400;
        int xPosition = (window.getWidth() - panelWidth) / 2;
        int yPosition = (window.getHeight() - panelHeight) / 2;
        registerPanel.setBounds(xPosition, yPosition, panelWidth, panelHeight);
        registerPanel.setBackground(Color.gray);
        registerPanel.setLayout(null);

        // SIGN UP LABEL
        signUpLabel = new JLabel("SIGN UP");
        signUpLabel.setForeground(Color.white);
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 30));
        signUpLabel.setBounds(40, 90, 200, 50);
        registerPanel.add(signUpLabel);



        window.add(registerPanel);
        window.revalidate();
        window.repaint();

    }


}
