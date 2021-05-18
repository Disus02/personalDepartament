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
    private Button savePdfTimesheet;

    @FXML
    void initialize() throws IOException, DocumentException {
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
        initializeTimesheet();
        buttonSaveMarks.setOnAction(event -> {
            createMarks();
        });
        createTimesheetPdf();
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
                    Document document = new Document(PageSize.A4.rotate());
                    PdfWriter.getInstance(document, new FileOutputStream(fileName));
                    document.open();
                    String font = "./src/main/resources/ArialRegular.ttf";
                    BaseFont bf = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    Font fontParagraph = new Font(bf, 18, Font.NORMAL);
                    Font fontTable = new Font(bf, 12, Font.NORMAL);
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
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(String.valueOf(newValue.getNumberDoc()))));
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(String.valueOf(newValue.getDateCompilation()))));
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(String.valueOf(newValue.getDateStart()))));
                    tableTimeSheetPdf.addCell(new PdfPCell(new Phrase(String.valueOf(newValue.getDateEnd()))));
                    document.add(tableTimeSheetPdf);
                    PdfPTable tableMarks=new PdfPTable(4);
                    tableMarks.addCell(new PdfPCell(new Phrase("1",fontTable)));
                    tableMarks.addCell(new PdfPCell(new Phrase("2",fontTable)));
                    tableMarks.addCell(new PdfPCell(new Phrase("3",fontTable)));
                    tableMarks.addCell(new PdfPCell(new Phrase("4",fontTable)));
                    PdfPTable tableTimeSheetWorker=new PdfPTable(4);
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("Номер\nпо порядку",fontTable)));
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("ФИО\nдолжность",fontTable)));
                    tableTimeSheetWorker.addCell(new PdfPCell(new Phrase("Табельный\nномер",fontTable)));
                    PdfPCell marks=new PdfPCell(tableMarks);
                    PdfPHeaderCell title=new PdfPHeaderCell();
                    title.setName("ОТМЕТКА");
                    marks.addHeader(title);
                    tableTimeSheetWorker.addCell(marks);
                    tableTimeSheetWorker.setHeaderRows(1);
                    for (TimesheetWorker timesheetWorker:newValue.getTimesheetWorkers()) {
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.valueOf(timesheetWorker.getWorker().getId()),fontTable)));
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.format("%s %s\n%s\n%s",
                                timesheetWorker.getWorker().getLastName(),
                                timesheetWorker.getWorker().getFirstName(),
                                timesheetWorker.getWorker().getPatronymic(),timesheetWorker.getWorker().getPositionTypes().iterator().next().getTitle()),fontTable)));
                        tableTimeSheetWorker.addCell(new PdfPCell(new Phrase(String.valueOf(timesheetWorker.getWorker().getNumberService()),fontTable)));

                    }
                    document.add(tableTimeSheetWorker);
                    document.close();
                }catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }
            });
        });
    }
}
