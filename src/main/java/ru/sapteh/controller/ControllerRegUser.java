package ru.sapteh.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerRegUser {
    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtLogin;

    @FXML
    private ComboBox<?> comboRole;

    @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonClose;

    @FXML
    private Label status;
}
