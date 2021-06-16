package ru.sapteh.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.*;
import ru.sapteh.service.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerCreateWorker {
    ObservableList<Division> divisions= FXCollections.observableArrayList();
    ObservableList<WorkBook> workBooks= FXCollections.observableArrayList();
    ObservableList<Passport> passports = FXCollections.observableArrayList();
    ObservableList<PositionType> positionTypes=FXCollections.observableArrayList();
    @FXML
    private TextField txtNumberPassport;
    @FXML
    private TextField txtSeriesPassport;
    @FXML
    private DatePicker dateIssuePassport;
    @FXML
    private TextField txtDivisionCodePassport;
    @FXML
    private TextArea whomIssuedPassport;
    @FXML
    private TextArea registrationPlacePassport;
    @FXML
    private TextField txtTitleDivision;
    @FXML
    private TextField txtNumberBook;
    @FXML
    private TextField txtSeriesBook;
    @FXML
    private DatePicker dateIssueBook;
    @FXML
    private TextArea whomIssueBook;
    @FXML
    private Button savePassport;
    @FXML
    private Button saveWorkBook;
    @FXML
    private Button saveDivision;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtPatronymic;
    @FXML
    private TextArea txtAddress;
    @FXML
    private TextField txtInn;
    @FXML
    private TextField txtPathPhoto;
    @FXML
    private Button photoPath;
    @FXML
    private DatePicker dateEmployment;
    @FXML
    private DatePicker birthday;
    @FXML
    private ComboBox<Division> comboDivision;
    @FXML
    private ComboBox<Passport> comboPassport;
    @FXML
    private ComboBox<WorkBook> comboWorkBook;
    @FXML
    private Button saveWorker;
    @FXML
    private TextField txtSnils;
    @FXML
    private TextField txtNumberService;
    @FXML
    private ComboBox<PositionType> comboPost;

    @FXML
    void initialize(){
        txtPathPhoto.setDisable(true);
        getValueComboBox();
        savePassport.setOnAction(event -> {
            try {
                createPassport();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            clearScreen();
        });
        saveDivision.setOnAction(event -> {
            createDivision();
            clearScreen();
        });
        saveWorkBook.setOnAction(event -> {
            try {
                createWorkBook();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            clearScreen();
        });
        saveWorker.setOnAction(event -> {
            try {
                createWorker();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public void photoPath(ActionEvent event) throws IOException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выбрать файл");
        File file=fileChooser.showOpenDialog(new Stage());
        if (file!=null){
            txtPathPhoto.setText("image/"+file.getName());
            File source=new File(file.getAbsolutePath());
            File dest=new File("./src/main/resources/image/"+file.getName());
            Files.copy(source.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    private void getValueComboBox(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Division,Integer> divisionService=new DivisionService(factory);
        Dao<Passport,Integer> passportService=new PassportService(factory);
        Dao<WorkBook,Integer> workBookService=new WorkBookService(factory);
        Dao<PositionType,Integer> positionTypeService=new PositionTypeService(factory);
        workBooks.addAll(workBookService.readByAll());
        divisions.addAll(divisionService.readByAll());
        passports.addAll(passportService.readByAll());
        positionTypes.addAll(positionTypeService.readByAll());
        comboDivision.setItems(divisions);
        comboPassport.setItems(passports);
        comboWorkBook.setItems(workBooks);
        comboPost.setItems(positionTypes);
    }
    private void createPassport() throws ParseException {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Passport,Integer> passportService=new PassportService(factory);
        Passport passport=new Passport();
        passport.setNumber(Integer.parseInt(txtNumberPassport.getText()));
        passport.setSeries(Integer.parseInt(txtSeriesPassport.getText()));
        passport.setWhomIssued(whomIssuedPassport.getText());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateIssuePassport.getValue().toString().substring(0,10));
        passport.setDateIssue(date);
        passport.setRegistrationPlace(registrationPlacePassport.getText());
        passport.setDevisionCode(Integer.parseInt(txtDivisionCodePassport.getText()));
        passportService.create(passport);
    }
    private void createWorkBook() throws ParseException {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<WorkBook,Integer> workBookService=new WorkBookService(factory);
        WorkBook workBook=new WorkBook();
        workBook.setNumber(Integer.parseInt(txtNumberBook.getText()));
        workBook.setSeries(Integer.parseInt(txtSeriesBook.getText()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateIssueBook.getValue().toString().substring(0,10));
        workBook.setDateIssue(date);
        workBook.setWhomIssued(whomIssueBook.getText());
        workBookService.create(workBook);
    }
    private void createDivision(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Division,Integer> divisionService=new DivisionService(factory);
        Dao<Organization,Integer> organizationService=new OrganizationService(factory);
        Division division=new Division();
        division.setName(txtTitleDivision.getText());
        division.setOrganization(organizationService.read(1));
        divisionService.create(division);
    }
    private void createWorker() throws ParseException {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Worker,Integer> workerService=new WorkerService(factory);
        Worker worker=new Worker();
        worker.setLastName(txtLastName.getText());
        worker.setFirstName(txtFirstName.getText());
        worker.setPatronymic(txtPatronymic.getText());
        worker.setAddress(txtAddress.getText());
        worker.setInn(Integer.parseInt(txtInn.getText()));
        worker.setPhotoPath(txtPathPhoto.getText());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birthday.getValue().toString().substring(0,10));
        worker.setBirthday(date);
        Date date1 = format.parse(dateEmployment.getValue().toString().substring(0,10));
        worker.setDateEmployment(date1);
        worker.setSnils(Integer.parseInt(txtSnils.getText()));
        worker.setNumberService(Integer.parseInt(txtNumberService.getText()));
        worker.setPassport(comboPassport.getValue());
        worker.setWorkBook(comboWorkBook.getValue());
        worker.setDivision(comboDivision.getValue());
        worker.addPost(comboPost.getValue());
        workerService.create(worker);
    }
    private void clearScreen(){
        workBooks.clear();
        divisions.clear();
        passports.clear();
        initialize();
    }
}
