package org.example.controller;

import org.example.view.Login;
import org.example.view.Online;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ButtonController implements ActionListener {
    private Online online;
    private Login login;

    public ButtonController(Online online, Login login) {
        this.online = online;
        this.login = login;
    }


    @Override
    public void actionPerformed(ActionEvent theEvent) {
        String chosen = theEvent.getActionCommand();
        if (chosen.equals("Register")) {
            login.registerPage();
        }
    }
}
