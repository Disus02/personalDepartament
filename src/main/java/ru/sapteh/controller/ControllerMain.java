package ru.sapteh.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.*;
import ru.sapteh.service.TimetableService;
import ru.sapteh.service.WorkerService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerMain {
    ObservableList<Worker> workers= FXCollections.observableArrayList();
    @FXML
    private TableView<Worker> tableWorker;

    @FXML
    private TableColumn<Worker,String> columnLastName;

    @FXML
    private TableColumn<Worker, String> columnFirstName;

    @FXML
    private TableColumn<Worker, String> columnPatronymic;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPatronymic;

    @FXML
    private TextField txtBirthday;

    @FXML
    private TextField txtDateEmployment;

    @FXML
    private TextArea txtAddress;

    @FXML
    private ImageView imagePhoto;

    @FXML
    private Button buttonPath;

    @FXML
    private TextField txtTitlePost;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtSeries;

    @FXML
    private TextField txtNumbers;

    @FXML
    private TextArea txtWhomIssued;

    @FXML
    private TextArea txtRegPlace;

    @FXML
    private TextField txtDateIssue;

    @FXML
    private TextField txtDivisionCode;
    @FXML
    private Button buttonOpenCreateWorker;
    @FXML
    private Button updateWorker;
    @FXML
    private Button deleteWorker;
    @FXML
    private TableView<TimesheetWorker> tableTimesheetWorker;

    @FXML
    private TableColumn<TimesheetWorker, Date> columnDateStart;

    @FXML
    private TableColumn<TimesheetWorker, Date> columnDateEnd;

    @FXML
    private TableColumn<TimesheetWorker, Integer> columnPaymentCode;

    @FXML
    private TableColumn<TimesheetWorker, Integer> columnCorAccount;

    @FXML
    private TextField txtNumberDay;

    @FXML
    private TextField txtQuantityHours;

    @FXML
    private ComboBox<Status> comboStatus;

    @FXML
    private ComboBox<TimesheetWorker> comboTimesheetWorker;

    @FXML
    private TableView<Marks> tableMarks;

    @FXML
    private TableColumn<Marks, Integer> columnNumberDay;

    @FXML
    private TableColumn<Marks,Integer> columnQuantityHours;

    @FXML
    private TableColumn<Marks, String> columnStatus;

    @FXML
    private ComboBox<Worker> comboWorkers;

    @FXML
    private Button buttonSaveMarks;
    @FXML
    private ComboBox<Timetable> comboTimetable;

    @FXML
    private TableView<Worker> tableListWorker;

    @FXML
    private TableColumn<Worker, String> columnFirstNameW;

    @FXML
    private TableColumn<Worker, String> columnLastNameW;

    @FXML
    private TableColumn<Worker, String> columnPatronymicW;

    @FXML
    private TextField numberOrder;

    @FXML
    private DatePicker dateStartHoliday;

    @FXML
    private DatePicker dateEndHoliday;

    @FXML
    private ComboBox<Worker> comboWorkerHoliday;
    @FXML
    private ComboBox<Timetable> comboTimetableReg;
    @FXML
    private Button saveTimetable;
    @FXML
    private Button saveHoliday;

    @FXML
    void initialize(){
        getWorker();
        columnFirstName.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getFirstName()));
        columnLastName.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getLastName()));
        columnPatronymic.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getPatronymic()));
        tableWorker.setItems(workers);
        tableWorker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            try {
                showDetailsWorker(newValue);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
        searchWorker();
        buttonOpenCreateWorker.setOnAction(event -> {
            try {
                Parent parent= FXMLLoader.load(getClass().getResource("/view/createWorker.fxml"));
                Stage stage=new Stage();
                stage.setTitle("Добавление сотрудника");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        saveTimetable.setOnAction(event -> {
            try {
                createTimetable();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        initializeTableTimeSheetWorker();
        initializeTimetable();
    }

    @FXML
    void photoPath(ActionEvent event) {

    }

    @FXML
    void updateWorker(ActionEvent event) {

    }

    private void getWorker(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        WorkerService workerService=new WorkerService(factory);
        workers.addAll(workerService.readByAll());
    }
    private void searchWorker(){
        FilteredList<Worker> filterList = new FilteredList<>(workers, p -> true);
        txtSearch.textProperty().addListener((obj, oldValue, newValue) -> {
            filterList.setPredicate(worker -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                    if (worker.getFirstName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    else if (worker.getLastName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    } else if (worker.getPatronymic().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                return false;
            });
        });
        SortedList<Worker> sortedList = new SortedList<>(filterList);
        sortedList.comparatorProperty().bind(tableWorker.comparatorProperty());
        tableWorker.setItems(sortedList);
    }
    private void showDetailsWorker(Worker worker) throws FileNotFoundException {
        if (worker!=null){
            txtFirstName.setText(worker.getFirstName());
            txtLastName.setText(worker.getLastName());
            txtPatronymic.setText(worker.getPatronymic());
            txtAddress.setText(worker.getAddress());
            txtBirthday.setText(String.valueOf(worker.getBirthday()));
            txtDateEmployment.setText(String.valueOf(worker.getDateEmployment()));
            buttonPath.setOnAction(event -> {FileChooser fileChooser=new FileChooser();
                fileChooser.setTitle("Выбрать файл");
                File file=fileChooser.showOpenDialog(new Stage());
                if (!(file==null)){
                    worker.setPhotoPath(file.getAbsolutePath());
                }});
            imagePhoto.setImage(new Image(worker.getPhotoPath()));
            txtNumbers.setText(String.valueOf(worker.getPassport().getNumber()));
            txtSeries.setText(String.valueOf(worker.getPassport().getSeries()));
            txtDivisionCode.setText(String.valueOf(worker.getPassport().getDevisionCode()));
            txtWhomIssued.setText(worker.getPassport().getWhomIssued());
            txtRegPlace.setText(worker.getPassport().getRegistrationPlace());
            txtDateIssue.setText(String.valueOf(worker.getPassport().getDateIssue()));
            txtTitlePost.setText(worker.getPositionTypes().iterator().next().getTitle());
            txtSalary.setText(worker.getPositionTypes().iterator().next().getSalary());
        }else{
            txtFirstName.setText("");
            txtLastName.setText("");
            txtPatronymic.setText("");
            txtAddress.setText("");
            txtBirthday.setText("");
            txtDateEmployment.setText("");
            txtNumbers.setText("");
            txtSeries.setText("");
            txtDivisionCode.setText("");
            txtWhomIssued.setText("");
            txtRegPlace.setText("");
            txtDateIssue.setText("");
            txtTitlePost.setText("");
            txtSalary.setText("");
        }
    }
    private void initializeTableTimeSheetWorker(){
        ObservableList<Marks> marks=FXCollections.observableArrayList();
        ObservableList<TimesheetWorker> timesheetWorkers=FXCollections.observableArrayList();
        ObservableList<Worker> workers=FXCollections.observableArrayList();
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Worker,Integer> workerService=new WorkerService(factory);
        workers.addAll(workerService.readByAll());
        comboWorkers.setItems(workers);
        comboWorkers.valueProperty().addListener((obj,oldValue,newValue)->{
            timesheetWorkers.clear();
            timesheetWorkers.addAll(newValue.getTimesheetWorkers());
            columnDateStart.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getTimesheet().getDateStart()));
            columnDateEnd.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getTimesheet().getDateEnd()));
            columnPaymentCode.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getPaymentCode().getCode()));
            columnCorAccount.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getCorrespondentAccount().getCode()));
            tableTimesheetWorker.setItems(timesheetWorkers);
        });
        tableTimesheetWorker.getSelectionModel().selectedItemProperty().addListener((obj,oldValue,newValue)->{
            marks.clear();
            marks.addAll(newValue.getMarks());
            columnNumberDay.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getNumberDay()));
            columnQuantityHours.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getQuantityHours()));
            columnStatus.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getStatus().getName()));
            tableMarks.setItems(marks);
        });

    }
    private void initializeTimetable(){
        ObservableList<Timetable> timetables=FXCollections.observableArrayList();
        ObservableList<Worker> workers=FXCollections.observableArrayList();
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Timetable,Integer> timetableService=new TimetableService(factory);
        timetables.addAll(timetableService.readByAll());
        comboTimetable.setItems(timetables);
        comboTimetableReg.setItems(timetables);
        comboTimetable.getSelectionModel().selectedItemProperty().addListener((obj,oldValue,newValue)->{
            workers.clear();
            workers.addAll(newValue.getWorkers());
            columnFirstNameW.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getFirstName()));
            columnLastNameW.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getLastName()));
            columnPatronymicW.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getPatronymic()));
            tableListWorker.setItems(workers);
        });
    }
    private void createTimetable() throws ParseException {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Timetable,Integer> timetableService=new TimetableService(factory);
        Timetable timetable=new Timetable();
        timetable.setNumberOrder(Integer.parseInt(numberOrder.getText()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateStartHoliday.getValue().toString().substring(0,10));
        timetable.setDateStart(date);
        Date date1 = format.parse(dateEndHoliday.getValue().toString().substring(0,10));
        timetable.setDateEnd(date1);
        timetableService.create(timetable);
    }
}
