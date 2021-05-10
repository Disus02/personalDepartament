package ru.sapteh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogin {
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button buttonOpen;
    @FXML
    private Button buttonExit;

    @FXML
    public void initialize(){
        buttonExit.setOnAction(event -> {
            System.exit(0);
        });
        buttonOpen.setOnAction(event -> {buttonOpen.getScene().getWindow().hide();
            Parent parent= null;
            try {
                parent = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                Stage stage=new Stage();
                stage.setScene(new Scene(parent));
                stage.setTitle("Главная страница");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
