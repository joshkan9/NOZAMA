package org.example.view;

import org.example.controller.ButtonController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Online {
    Login login = new Login();
    ButtonController theChoice = new ButtonController(this, login);

    public static void main(String[] theArgs) {
        new Online();
    }

    public Online() {
        login.createLogin(theChoice);

    }

}

