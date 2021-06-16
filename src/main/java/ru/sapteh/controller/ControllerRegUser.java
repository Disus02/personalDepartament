package ru.sapteh.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Role;
import ru.sapteh.model.Users;
import ru.sapteh.service.RoleService;
import ru.sapteh.service.UsersService;

public class ControllerRegUser {
    ObservableList<Role> roles= FXCollections.observableArrayList();
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtLogin;
    @FXML
    private ComboBox<Role> comboRole;
    @FXML
    private Button buttonCreate;
    @FXML
    private Button buttonClose;
    @FXML
    private Label status;
    String login;


    @FXML
    public void initialize(){
        getList();
        buttonClose.setOnAction(event -> {
            System.exit(0);
        });
        buttonCreate.setOnAction(event -> {
            createUser();
        });

    }
    private void getList(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Role,Integer> roleIntegerDao=new RoleService(factory);
        roles.addAll(roleIntegerDao.readByAll());
        comboRole.setItems(roles);
    }
    private void createUser(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Users,Integer> usersIntegerDao=new UsersService(factory);
        Users users=new Users();
        for (Users users1:usersIntegerDao.readByAll()) {
            if (users1.getLogin().equals(txtLogin.getText())){
                login=users1.getLogin();
            }
        }
        if (!login.equals(txtLogin.getText())){
            users.setFirstName(txtFirstName.getText());
            users.setLastName(txtLastName.getText());
            users.setLogin(txtLogin.getText());
            users.setPassword(txtPassword.getText());
            users.setRole(comboRole.getValue());
            usersIntegerDao.create(users);
            status.setText(String.format("Пользователь %s %s успешно добавлен",users.getFirstName(),users.getLastName()));
        }else status.setText("Логин занят");
    }
}
