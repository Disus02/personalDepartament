package ru.sapteh.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mysql.cj.xdevapi.Client;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.*;
import ru.sapteh.service.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ControllerMain {
    ObservableList<Worker> workers= FXCollections.observableArrayList();
    ObservableList<Marks> marks=FXCollections.observableArrayList();
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
    private ComboBox<Division> comboDivisionTimesheet;
    @FXML
    private TableView<Timesheet> tableTimesheet;
    @FXML
    private TableColumn<Timesheet,Integer> columnNumberDoc;
    @FXML
    private TableColumn<Timesheet, Date> columnDateCompilation;
    @FXML
    private TableColumn<Timesheet, Date> columnPeriodFrom;
    @FXML
    private TableColumn<Timesheet, Date> columnPeriodTo;
    @FXML
    private ComboBox<Timesheet> comboPeriod;
    @FXML
    private ComboBox<Worker> comboWorkTimesheet;
    @FXML
    private ComboBox<PaymentCode> comboPaymentCode;
    @FXML
    private ComboBox<CorrespondentAccount> comboCorrespondentAccount;
    @FXML
    private Button saveTimesheetWorker;
    @FXML
    private Button openRegUser;
    @FXML
    private Button savePdfTimesheet;
    @FXML
    private TextField txtNumberDoc;
    @FXML
    private DatePicker dateSostav;
    @FXML
    private DatePicker datePeriodStart;
    @FXML
    private DatePicker datePeriodEnd;
    @FXML
    private Button saveTimesheet;
    Timetable timetable=new Timetable();
    private Worker worker;
    private PositionType positionType;
    int count=0;

    @FXML
    void initialize() throws IOException, DocumentException {
        if (ControllerLogin.role.equals("Директор")){
            openRegUser.setDisable(true);
            buttonOpenCreateWorker.setDisable(true);
            saveHoliday.setDisable(true);
            saveTimetable.setDisable(true);
            buttonSaveMarks.setDisable(true);
            buttonPath.setDisable(true);
            deleteWorker.setDisable(true);
            updateWorker.setDisable(true);
        }else if (ControllerLogin.role.equals("Табельщик")){
            deleteWorker.setDisable(true);
        }
        getLists();
        getWorker();
        columnFirstName.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getFirstName()));
        columnLastName.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getLastName()));
        columnPatronymic.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getPatronymic()));
        tableWorker.setItems(workers);
        tableWorker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            try {
                if (newValue!=null){
                    worker=newValue;
                }
                showDetailsWorker(newValue);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
        searchWorker();
        openRegUser.setOnAction(event -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/view/regUser.fxml"));
                Stage stage=new Stage();
                stage.setTitle("Добавление пользователя");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttonOpenCreateWorker.setOnAction(event -> {
            try {
                Parent parent= FXMLLoader.load(getClass().getResource("/view/createWorker.fxml"));
                Stage stage=new Stage();
                stage.setTitle("Добавление сотрудника");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
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
        initializeTimesheet();
        buttonSaveMarks.setOnAction(event -> {
            createMarks();
            initializeTableTimeSheetWorker();
        });
        saveHoliday.setOnAction(event -> {
            createHoliday();
        });
        createTimesheetPdf();
        saveTimesheetWorker.setOnAction(event -> {
            createTimesheetWorker();
        });
        updateWorker.setOnAction(event -> {
            try {
                updateWorkers();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        saveTimesheet.setOnAction(event -> {
            try {
                createTimesheet();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void getWorker(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        WorkerService workerService=new WorkerService(factory);
        workers.addAll(workerService.readByAll());
        comboWorkerHoliday.setItems(workers);
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
        ObservableList<Status> statuses=FXCollections.observableArrayList();
        ObservableList<TimesheetWorker> timesheetWorkers=FXCollections.observableArrayList();
        ObservableList<Worker> workers=FXCollections.observableArrayList();
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Worker,Integer> workerService=new WorkerService(factory);
        Dao<Status,Integer> statusService=new StatusService(factory);
        statuses.addAll(statusService.readByAll());
        comboStatus.setItems(statuses);
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
        initializeMarks();
    }
    private void initializeMarks(){
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
    private void createMarks(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Marks,Integer> marksService=new MarksService(factory);
        Marks marks=new Marks();
        marks.setNumberDay(Integer.parseInt(txtNumberDay.getText()));
        marks.setQuantityHours(Integer.parseInt(txtQuantityHours.getText()));
        marks.setStatus(comboStatus.getValue());
        marks.setTimesheetWorker(tableTimesheetWorker.getSelectionModel().getSelectedItem());
        marksService.create(marks);
        System.out.println(tableTimesheetWorker.getSelectionModel().getSelectedItem());
    }
    private void initializeTimesheet(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        ObservableList<Division> divisions=FXCollections.observableArrayList();
        ObservableList<Timesheet> timeSheets=FXCollections.observableArrayList();
        Dao<Division,Integer> divisionService=new DivisionService(factory);
        divisions.addAll(divisionService.readByAll());
        comboDivisionTimesheet.setItems(divisions);
        comboDivisionTimesheet.valueProperty().addListener((obj,oldValue,newValue)->{
            timeSheets.clear();
            timeSheets.addAll(newValue.getTimesheet());
            columnNumberDoc.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getNumberDoc()));
            columnDateCompilation.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getDateCompilation()));
            columnPeriodFrom.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getDateStart()));
            columnPeriodTo.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getDateEnd()));
            tableTimesheet.setItems(timeSheets);
        });
    }
    private void createTimesheetPdf() throws IOException, DocumentException {
        tableTimesheet.getSelectionModel().selectedItemProperty().addListener((obj,oldValue,newValue)->{
            savePdfTimesheet.setOnAction(event -> {
                FileChooser fileChooser=new FileChooser();
                fileChooser.setTitle("Выберите путь");
                File file=fileChooser.showSaveDialog(new Stage());
                String fileName= file.getAbsolutePath();
                try {
                    Document document = new Document(PageSize.A3.rotate());
                    PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
                    document.open();
                    String font = "./src/main/resources/ArialRegular.ttf";
                    BaseFont bf = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    Font fontParagraph = new Font(bf, 18, Font.NORMAL);
                    Font fontTable = new Font(bf, 8, Font.NORMAL);
                    Font fontNumberDay=new Font(bf,8,Font.NORMAL);
                    Paragraph paragraph = new Paragraph(newValue.getDivision().getOrganization().getName(), fontParagraph);
                    paragraph.setSpacingAfter(20);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);
                    Paragraph paragraph1 = new Paragraph(newValue.getDivision().getName(), fontParagraph);
                    paragraph1.setSpacingAfter(20);
                    paragraph1.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph1);
                    PdfPTable tableTimeSheetPdf = new PdfPTable(4);
                    ObservableList<TableColumn<Timesheet, ?>> columns = tableTimesheet.getColumns();
                    columns.forEach(c -> tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(c.getText(), fontTable))));
                    tableTimeSheetPdf.setHeaderRows(1);
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(String.valueOf(newValue.getNumberDoc()),fontTable)));
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(newValue.getDateCompilation().toString().substring(0,10),fontTable)));
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(newValue.getDateStart().toString().substring(0,10),fontTable)));
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(newValue.getDateEnd().toString().substring(0,10),fontTable)));
                    document.add(tableTimeSheetPdf);
                    PdfPTable tableMarks=new PdfPTable(15);
                    tableMarks.addCell(new PdfPCell(new Phrase("1",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("2",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("3",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("4",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("5",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("6",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("7",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("8",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("9",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("10",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("11",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("12",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("13",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("14",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("15",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("16",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("17",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("18",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("19",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("20",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("21",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("22",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("23",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("24",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("25",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("26",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("27",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("28",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("29",fontNumberDay)));
                    tableMarks.addCell(new PdfPCell(new Phrase("30",fontNumberDay)));
                    PdfPTable tableStatus=new PdfPTable(15);
                    PdfPTable tableTimeSheetWorker=new PdfPTable(7);
                    PdfPCell number=new PdfPCell(new Phrase("Номер\nпо порядку",fontTable));
                    tableTimeSheetWorker.addCell(number);
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("ФИО\nдолжность",fontTable)));
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("Табельный\nномер",fontTable)));
                    PdfPCell marks=new PdfPCell(tableMarks);
                    tableTimeSheetWorker.addCell(marks);
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("дней\nчасов",fontTable)));
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("код вида оплаты",fontTable)));
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("корреспондентский\nсчет",fontTable)));
                    tableTimeSheetWorker.setHeaderRows(1);
                    for (TimesheetWorker timesheetWorker:newValue.getTimesheetWorkers()) {
                        PdfPCell id=new PdfPCell(new Phrase(String.valueOf(timesheetWorker.getWorker().getId()),fontTable));
                        tableTimeSheetWorker.addCell(id);
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.format("%s %s\n%s\n%s",
                                timesheetWorker.getWorker().getLastName(),
                                timesheetWorker.getWorker().getFirstName(),
                                timesheetWorker.getWorker().getPatronymic(),timesheetWorker.getWorker().getPositionTypes().iterator().next().getTitle()),fontTable)));
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.valueOf(timesheetWorker.getWorker().getNumberService()),fontTable)));
                        Set<Marks> marksSet=timesheetWorker.getMarks();
                        int quantityHours=0;
                        for (Marks marks1:marksSet){
                            tableStatus.addCell(new PdfPCell(new Phrase(String.format("%d\n%s",marks1.getQuantityHours(),
                                    marks1.getStatus()),fontTable)));
                            quantityHours+=marks1.getQuantityHours();
                        }
                        int quantityDay=quantityHours/8;
                        tableTimeSheetWorker.addCell(new PdfPCell(tableStatus));
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.format("%d\n%d",quantityDay,quantityHours),fontTable)));
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.valueOf(timesheetWorker.getPaymentCode().getCode()),fontTable)));
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.valueOf(timesheetWorker.getCorrespondentAccount().getCode()),fontTable)));
                        tableStatus.resetColumnCount(14);
                    }
                    document.add(tableTimeSheetWorker);
                    document.close();
                }catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }
            });
        });
    }
    private void createHoliday(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Worker,Integer> workerService=new WorkerService(factory);
        Worker worker=comboWorkerHoliday.getValue();
        worker.addHoliday(comboTimetableReg.getValue());
        workerService.update(worker);
    }
    private void createTimesheetWorker(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<TimesheetWorker,Integer> timesheetWorkerService=new TimesheetWorkerService(factory);
        TimesheetWorker timesheetWorker=new TimesheetWorker();
        timesheetWorker.setTimesheet(comboPeriod.getValue());
        timesheetWorker.setWorker(comboWorkTimesheet.getValue());
        timesheetWorker.setCorrespondentAccount(comboCorrespondentAccount.getValue());
        timesheetWorker.setPaymentCode(comboPaymentCode.getValue());
        timesheetWorkerService.create(timesheetWorker);

    }
    private void getLists(){
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Worker,Integer> workerIntegerDao=new WorkerService(factory);
        Dao<PaymentCode,Integer> paymentCodeService=new PaymentCodeService(factory);
        Dao<CorrespondentAccount,Integer> correspondentAccountService=new CorrespondentAccountService(factory);
        Dao<Timesheet,Integer> timesheetIntegerDao=new TimesheetService(factory);

        ObservableList<Worker> workerLists=FXCollections.observableArrayList();
        ObservableList<PaymentCode> paymentCodes=FXCollections.observableArrayList();
        ObservableList<CorrespondentAccount> correspondentAccounts=FXCollections.observableArrayList();
        ObservableList<Timesheet> timesheetlist=FXCollections.observableArrayList();
        workerLists.addAll(workerIntegerDao.readByAll());
        paymentCodes.addAll(paymentCodeService.readByAll());
        correspondentAccounts.addAll(correspondentAccountService.readByAll());
        timesheetlist.addAll(timesheetIntegerDao.readByAll());

        comboPeriod.setItems(timesheetlist);
        comboCorrespondentAccount.setItems(correspondentAccounts);
        comboPaymentCode.setItems(paymentCodes);
        comboWorkTimesheet.setItems(workerLists);
    }
    private void updateWorkers() throws ParseException {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Worker,Integer> workerIntegerDao=new WorkerService(factory);
        Dao<Passport,Integer> passportIntegerDao=new PassportService(factory);
        Dao<PositionType,Integer> positionTypeIntegerDao=new PositionTypeService(factory);
        worker.setFirstName(txtFirstName.getText());
        worker.setLastName(txtLastName.getText());
        worker.setPatronymic(txtPatronymic.getText());
        worker.setAddress(txtAddress.getText());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date birthday=simpleDateFormat.parse(txtBirthday.getText());
        worker.setBirthday(birthday);
        Date dateEmployee=simpleDateFormat.parse(txtDateEmployment.getText());
        worker.setDateEmployment(dateEmployee);
        worker.getPassport().setDevisionCode(Integer.parseInt(txtDivisionCode.getText()));
        worker.getPassport().setRegistrationPlace(txtRegPlace.getText());
        worker.getPassport().setNumber(Integer.parseInt(txtNumbers.getText()));
        worker.getPassport().setSeries(Integer.parseInt(txtSeries.getText()));
        worker.getPassport().setWhomIssued(txtWhomIssued.getText());
        passportIntegerDao.update(worker.getPassport());
        Date dateIssue=simpleDateFormat.parse(txtDateIssue.getText());
        worker.getPassport().setDateIssue(dateIssue);
        for (PositionType positionType1:worker.getPositionTypes()) {
            positionType=positionType1;
        }
        positionType.setTitle(txtTitlePost.getText());
        positionType.setSalary(txtSalary.getText());
        positionTypeIntegerDao.update(positionType);
        workerIntegerDao.update(worker);
    }
    private void createTimesheet() throws ParseException {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Timesheet,Integer> timesheetIntegerDao=new TimesheetService(factory);
        Timesheet timesheet=new Timesheet();
        timesheet.setNumberDoc(Integer.parseInt(txtNumberDoc.getText()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCompil = format.parse(dateSostav.getValue().toString().substring(0,10));
        timesheet.setDateCompilation(dateCompil);
        Date dateStart=format.parse(datePeriodStart.getValue().toString().substring(0,10));
        timesheet.setDateStart(dateStart);
        Date dateEnd=format.parse(datePeriodEnd.getValue().toString().substring(0,10));
        timesheet.setDateEnd(dateEnd);
        timesheet.setDivision(comboDivisionTimesheet.getValue());
        timesheetIntegerDao.create(timesheet);
    }
}
