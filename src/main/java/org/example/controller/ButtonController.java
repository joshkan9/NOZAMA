package org.example.controller;

import org.example.view.Login;
import org.example.view.Online;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ButtonController {
    private Online online;
    private Login login;

    public ButtonController(Online online, Login login) {
        this.online = online;
        this.login = login;
    }
}
