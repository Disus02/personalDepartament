package ru.sapteh.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Role;
import ru.sapteh.model.Users;
import ru.sapteh.service.UsersService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerLogin {
    private final List<Users> users= new ArrayList<>();
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button buttonOpen;
    @FXML
    private Button buttonExit;
    @FXML
    private Label status;
    public static String role="";

    @FXML
    public void initialize(){
        buttonExit.setOnAction(event -> {
            System.exit(0);
        });

    }
    @FXML
    public void openMain(ActionEvent event) throws IOException {
        getUsers();
        if (txtLogin.getText().isEmpty()&&txtPassword.getText().isEmpty()) {
            status.setText("Логин и пароль не заполнены");
        }else if (txtLogin.getText().isEmpty()){
            status.setText("Логин пустой");
        }else if (txtPassword.getText().isEmpty()){
            status.setText("Пароль пустой");
        } else
            for (Users users:users) {
                if (users.getLogin().equals(txtLogin.getText())&&users.getPassword().equals(txtPassword.getText())){
                    status.setText(String.format("Добро пожаловать %s %s",users.getFirstName(),users.getLastName()));
                    role=users.getRole().getTitle();
                    buttonOpen.getScene().getWindow().hide();
                    Parent parent= FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                    Stage stage=new Stage();
                    stage.setTitle("Главная страница");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
                    Scene scene=new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                    break;
                }else status.setText("Пароля и логина не существует");
            }
    }
//    private void login() throws IOException {
//        getUsers();
//        if (txtLogin.getText().isEmpty()&&txtPassword.getText().isEmpty()) {
//            status.setText("Логин и пароль не заполнены");
//        }else if (txtLogin.getText().isEmpty()){
//            status.setText("Логин пустой");
//        }else if (txtPassword.getText().isEmpty()){
//            status.setText("Пароль пустой");
//        } else
//            for (Users users:users) {
//                if (users.getLogin().equals(txtLogin.getText())&&users.getPassword().equals(txtPassword.getText())){
//                    status.setText(String.format("Добро пожаловать %s %s",users.getFirstName(),users.getLastName()));
//                    role=users.getRole().getTitle();
//                    buttonOpen.getScene().getWindow().hide();
//                    Parent parent= FXMLLoader.load(getClass().getResource("/view/main.fxml"));
//                    Stage stage=new Stage();
//                    stage.setTitle("Главная страница");
//                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
//                    Scene scene=new Scene(parent);
//                    stage.setScene(scene);
//                    stage.show();
//                }else status.setText("Пароля и логина не существует");
//            }
//    }
    private void getUsers(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Users,Integer> usersIntegerDao=new UsersService(factory);
        users.addAll(usersIntegerDao.readByAll());
    }
}
