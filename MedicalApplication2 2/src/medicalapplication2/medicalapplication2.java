/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapplication2;

import Appointment.AppointmentCreator;
import Appointment.AppointmentScheduler;
import Connector.DBConnector;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author aasim
 */
public class medicalapplication2 extends Application {

    ComboBox specialitiesList = new ComboBox();
    TextField tffName = new TextField();
    TextField tflName = new TextField();
    CheckBox cbMonday = new CheckBox();
    CheckBox cbTuesday = new CheckBox();
    CheckBox cbWednesday = new CheckBox();
    CheckBox cbThursday = new CheckBox();
    CheckBox cbFriday = new CheckBox();
    final private ListView<String> selectView = new ListView();

    private final Label lDoctor = new Label("Doctor Name");
    private final Label lMonday = new Label("Monday");
    private final Label lTuesday = new Label("Tuesday");
    private final Label lWednesday = new Label("Wednesday");
    private final Label lThursday = new Label("Thursday");
    private final Label lFriday = new Label("Friday");

    private final Label patientName = new Label();
    private final TextField tfAddress = new TextField();
    private final TextField tfCity = new TextField();
    private final TextField tfState = new TextField();
    private final TextField tfpNumber = new TextField();
    private final TextField tfZipC = new TextField();
    
    private final Label lPatient = new Label("Patient Name");
    private final Label lAddress = new Label("Address");
    private final Label lCity = new Label("City");
    private final Label lState = new Label("State");
    private final Label lpNumber = new Label("Phone");
    private final Label lZipC = new Label("ZipCode");
    
    private final Label lDate = new Label("Choose Date");
    private final DatePicker datePicker = new DatePicker();
    private final ComboBox doctorList = new ComboBox();
    private final Button btNext = new Button("Next");
    Alert docAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert docDoesntWork = new Alert(Alert.AlertType.INFORMATION);
    
    private String speciality;
    private int id;

    HBox top, bottom;
    VBox left = new VBox(), right = new VBox(), center = new VBox();
    ArrayList<String> patients = new ArrayList<String>(0);  //Patients Class            DEMO
    ArrayList<String> doctors = new ArrayList<String>(0);   //Doctors Class             DEMO
    ArrayList<String> events = new ArrayList<String>(0);    //Events Class (for calendar)DEMO

    private final Button btShowContents = new Button("Show Contents");

    // Statement for executing queries
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        fillSpecialities();
        fillDoctors();
        
        try {
            top = createTop();
            root.setTop(top);
        } catch (FileNotFoundException e) {
            System.out.println("Err");
        }

        createLeft();
        createCenter();
        root.setBottom(bottom);
        root.setLeft(left);
        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root, 1024, 728);

        primaryStage.setTitle("Medical Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox createTop() throws FileNotFoundException {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #107076;");
        // Image

        // Text
        Text t = new Text("Medical Organization");
        t.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        t.setFill(Color.WHITE);
        //

        hbox.getChildren().addAll(t);

        return hbox;
    }   //Done. All I have to do is change the image

    public void createLeft() {
        left.setPadding(new Insets(15, 12, 15, 12));
        left.setSpacing(10);
        left.setStyle("-fx-background-color: #107076;");
        left.getChildren().clear();
        //Buttons (Patients, Doctors, Calendar)
        Button pat = new Button("Patients");
        Button doc = new Button("Doctors");
        Button cal = new Button("Calendar");
        left.getChildren().addAll(pat, doc, cal);
        pat.setOnAction((ActionEvent event) -> {
            pat.setText("Patients (Selected)");
            doc.setText("Doctors");
            cal.setText("Calendar");
            patCenter();

        });
        doc.setOnAction((ActionEvent event) -> {
            pat.setText("Patients");
            doc.setText("Doctors (Selected)");
            cal.setText("Calendar");
            docCenter();
        });
        cal.setOnAction((ActionEvent event) -> {
            pat.setText("Patients");
            doc.setText("Doctors");
            cal.setText("Calendar (Selected)");
            calCenter();
        });

    }

    //Default center when nothing is selected
    public void createCenter() {
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

    }

    //This function clears current center and updates it to the center displaying all patients
    public void patCenter() {
        BorderPane pane = new BorderPane();
        String temp = "";
        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();
        TextField name = new TextField();
        Button filter = new Button("Filter");
        Button unFilter = new Button("Un-Filter");
        Button select = new Button("Select");
        Button add = new Button("Add Patient");
        Button help = new Button("Help");

        add.setOnAction(e -> PatientAdd());

        grid2.setPadding(new Insets(0, 15, 15, 15));
        grid2.setHgap(10);
        grid2.setVgap(10);

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        grid.add(new Label("                                                        "), 0, 0);
        grid.add(new Label("ID"), 1, 0);
        grid.add(new Label("    "), 2, 0);
        grid.add(new Label("Name"), 3, 0);
        grid.add(new Label("                    "), 4, 0);
        grid.add(new Label("Phone Number"), 5, 0);

        grid2.add(new Label("Type Last Name: "), 0, 0);
        grid2.add(name, 0, 1);
        grid2.add(filter, 0, 2);
        grid2.add(unFilter, 0, 3);
        grid2.add(select, 0, 4);
        grid2.add(add, 0, 5);
        grid2.add(help, 0, 6);

        selectView.getItems().clear();

        try {
            String queryString = "select * from Patient";

            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            // Iterate through the result and print the student names
            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("pNumber")) {
                        temp += resultSet.getObject(i) + "     ";
                    }
                }
                selectView.getItems().add(temp);
                temp = "";
            }

        } catch (SQLException ex) {
        }

        //specialitiesList.getSelectionModel().getSelectedItem().toString())
        filter.setOnAction(e -> filterListPatient(name.getText()));
        unFilter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                selectView.getItems().clear();
                try {
                    String temp1 = "";
                    String queryString = "select * from Patient";

                    ResultSet resultSet = stmt.executeQuery(queryString);

                    ResultSetMetaData rsMetaData = resultSet.getMetaData();

                    // Iterate through the result and print the student names
                    while (resultSet.next()) {
                        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                            if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("pNumber")) {
                                temp1 += resultSet.getObject(i) + "     ";
                            }
                        }
                        selectView.getItems().add(temp1);
                        temp1 = "";
                    }

                } catch (SQLException ex) {
                }
            }
        });
        select.setOnAction((e -> showPatientContents(Double.parseDouble(selectView.getSelectionModel().getSelectedItem().substring(0, 3)))));
        help.setOnAction((e -> HelpMenu.helpMenu("MainPat")));
        
        pane.setTop(grid);
        pane.setLeft(grid2);
        pane.setCenter(selectView);

        center.getChildren().add(pane);

    }

    //This function clears current center and updates it to the center displaying all doctors
    public void docCenter() {
        BorderPane pane = new BorderPane();
        String temp = "";
        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();
        Button filter = new Button("Filter");
        Button unFilter = new Button("Un-Filter");
        Button select = new Button("Select");
        Button add = new Button("Add Doctor");
        Button help = new Button("Help");

        add.setOnAction(e -> DoctorAdd());

        grid2.setPadding(new Insets(0, 15, 15, 15));
        grid2.setHgap(10);
        grid2.setVgap(10);

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        grid.add(new Label("                                                        "), 0, 0);
        grid.add(new Label("ID"), 1, 0);
        grid.add(new Label("    "), 2, 0);
        grid.add(new Label("Name"), 3, 0);
        grid.add(new Label("                    "), 4, 0);
        grid.add(new Label("Speciality"), 5, 0);

        grid2.add(new Label("Select Speciality"), 0, 0);
        grid2.add(specialitiesList, 0, 1);
        grid2.add(filter, 0, 2);
        grid2.add(unFilter, 0, 3);
        grid2.add(select, 0, 4);
        grid2.add(add, 0, 5);
        grid2.add(help, 0, 6);

        selectView.getItems().clear();

        try {
            String queryString = "select * from Doctor";

            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            // Iterate through the result and print the student names
            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("Speciality")) {
                        temp += resultSet.getObject(i) + "     ";
                    }
                }
                selectView.getItems().add(temp);
                temp = "";
            }

        } catch (SQLException ex) {
        }

        //specialitiesList.getSelectionModel().getSelectedItem().toString())
        filter.setOnAction(e -> filterListDoctor());
        unFilter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                selectView.getItems().clear();
                try {
                    String temp1 = "";
                    String queryString = "select * from Doctor";

                    ResultSet resultSet = stmt.executeQuery(queryString);

                    ResultSetMetaData rsMetaData = resultSet.getMetaData();

                    // Iterate through the result and print the student names
                    while (resultSet.next()) {
                        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                            if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("Speciality")) {
                                temp1 += resultSet.getObject(i) + "     ";
                            }
                        }
                        selectView.getItems().add(temp1);
                        temp1 = "";
                    }

                } catch (SQLException ex) {
                }
            }
        });
        select.setOnAction((e -> showDoctorContents(Double.parseDouble(selectView.getSelectionModel().getSelectedItem().substring(0, 3)))));
        help.setOnAction((e -> HelpMenu.helpMenu("MainDoc")));
        
        pane.setTop(grid);
        pane.setLeft(grid2);
        pane.setCenter(selectView);

        center.getChildren().add(pane);
    }

    public void filterListDoctor() {
        selectView.getItems().clear();
        try {
            String temp = "";
            String queryString = "select * from Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (resultSet.getObject(4).equals(specialitiesList.getValue().toString())) {
                        if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("Speciality")) {
                            temp += resultSet.getObject(i) + "     ";
                        }
                    }
                }
                selectView.getItems().add(temp);
                temp = "";
            }
        } catch (SQLException ex) {
        }

    }
    
    
    public void filterListPatient(String name) {
        selectView.getItems().clear();
        try {
            String temp = "";
            String queryString = "select * from Patient";
            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (resultSet.getObject(3).equals(name)) {
                        if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("pNumber")) {
                            temp += resultSet.getObject(i) + "     ";
                        }
                    }
                }
                selectView.getItems().add(temp);
                temp = "";
            }
        } catch (SQLException ex) {
        }

    }

    private void showDoctorContents(double record) {
        BorderPane pane = new BorderPane();
        TextArea taResult = new TextArea();
        int idNum = (int) Math.round(record);
        Button goBack = new Button("Go Back");
        Button edit = new Button("Edit");
        Button help = new Button("Help");
        GridPane grid = new GridPane();

        taResult.setEditable(false);
        
        grid.add(goBack, 0, 0);
        grid.add(edit, 1, 0);
        grid.add(help, 2, 0);
        
        grid.setPadding(new Insets(15, 15, 15, 15));
        grid.setHgap(10);
        grid.setVgap(10);

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        try {
            String queryString = "select * from Doctor";

            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                taResult.appendText(String.format("%-20s", rsMetaData.getColumnName(i)));
            }
            taResult.appendText("\n");

            // Iterate through the result and print the student names
            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (resultSet.getObject(1).toString().equals(Integer.toString(idNum))) {
                        taResult.appendText(String.format("%-20s", resultSet.getObject(i)));
                    }
                }
            }
        } catch (SQLException ex) {
        }

        pane.setCenter(taResult);
        pane.setBottom(grid);
        center.getChildren().add(pane);

        edit.setOnAction(e -> EditDoctorContenets(record));
        goBack.setOnAction(e -> docCenter());
        help.setOnAction((e -> HelpMenu.helpMenu("ContentDoc")));
    }
    
    private void showPatientContents(double record) {
        BorderPane pane = new BorderPane();
        TextArea taResult = new TextArea();
        int idNum = (int) Math.round(record);
        Button goBack = new Button("Go Back");
        Button edit = new Button("Edit");
        Button appointments = new Button("Appointments");
        Button help = new Button("Help");
        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();

        taResult.setEditable(false);
        
        grid.add(goBack, 0, 0);
        grid.add(edit, 1, 0);
        grid.add(appointments, 2, 0);
        grid.add(help, 3, 0);

        grid.setPadding(new Insets(15, 15, 15, 15));
        grid.setHgap(10);
        grid.setVgap(10);

        grid2.setPadding(new Insets(15, 15, 15, 15));
        grid2.setHgap(10);
        grid2.setVgap(10);
        
        grid2.add(new Label(""), idNum, id);
        
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        try {
            String queryString = "select * from Patient JOIN Appointments ON Patient.ID = Appointments.IDPatient WHERE Patient.ID = " + idNum + " OR Appointments.IDPatient =" + idNum;

            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                if (i != 9 && i != 10 && i != 11)
                grid2.add(new Label(rsMetaData.getColumnName(i)),0,i);
            }

            // Iterate through the result and print the student names
            int x = 1;
            while (resultSet.next()) {
                if (x == 1) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (i != 9 && i != 10 && i != 11)
                    grid2.add(new Label(resultSet.getNString(i)),x,i);
                }
                } else {
                   for (int i = 12; i <= rsMetaData.getColumnCount(); i++) {
                       if (i != 9 && i != 10 && i != 11)
                    grid2.add(new Label(resultSet.getNString(i)),x,i);
                } 
                }
                x++;
            }
        } catch (SQLException ex) {
        }
        
        pane.setCenter(new ScrollPane(grid2));
        pane.setBottom(grid);
        center.getChildren().add(pane);

        edit.setOnAction(e -> EditPatientContenets(record));
        goBack.setOnAction(e -> patCenter());
        appointments.setOnAction(e -> addAppointments(record));
        help.setOnAction((e -> HelpMenu.helpMenu("ContentPat")));
    }

    public void EditDoctorContenets(double record) {
        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Button btSave = new Button("Save");
        Button btDelete = new Button("Delete");
        Button goBack = new Button("Go Back");
        Button help = new Button("Help");
        int idNum = (int) Math.round(record);
        String fName = "";
        String lName = "";

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        try {
            String queryString = "select * from Doctor where (ID = '" + Integer.toString(idNum) + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                fName = resultSet.getString("fName");
                lName = resultSet.getString("lName");
            }

        } catch (SQLException ex) {
        }

        grid.add(lDoctor, 0, 0);
        grid.add(new Label("Dr." + " " + fName + " " + lName), 1, 0);
        grid.add(lMonday, 0, 1);
        grid.add(cbMonday, 1, 1);
        grid.add(lTuesday, 0, 2);
        grid.add(cbTuesday, 1, 2);
        grid.add(lWednesday, 0, 3);
        grid.add(cbWednesday, 1, 3);
        grid.add(lThursday, 0, 4);
        grid.add(cbThursday, 1, 4);
        grid.add(lFriday, 0, 5);
        grid.add(cbFriday, 1, 5);
        grid.add(specialitiesList, 0, 6);
        grid.add(btSave, 0, 10);
        grid.add(goBack, 1, 10);
        grid.add(btDelete, 2, 10);
        grid.add(help, 3, 10);
        
        fillInfoDoctor(record);

        pane.setTop(grid);

        center.getChildren().add(pane);

        btSave.setOnAction(e -> saveActionDoctor());
        goBack.setOnAction(e -> showDoctorContents(record));
        btDelete.setOnAction(e -> deleteActionDoctor());
        help.setOnAction((e -> HelpMenu.helpMenu("EditDoc")));

    }

    public void EditPatientContenets(double record) {
        
        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Button btSave = new Button("Save");
        Button btDelete = new Button("Delete");
        Button goBack = new Button("Go Back");
        Button help = new Button("Help");
        
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        fillInfoPatient(record);
        
        grid.add(lPatient, 0, 0);
        grid.add(patientName, 1, 0);
        grid.add(lAddress, 0, 1);
        grid.add(tfAddress, 1, 1);
        grid.add(lCity, 0, 2);
        grid.add(tfCity, 1, 2);
        grid.add(lState, 0, 3);
        grid.add(tfState, 1, 3);
        grid.add(lpNumber, 0, 4);
        grid.add(tfpNumber, 1, 4);
        grid.add(lZipC, 0, 5);
        grid.add(tfZipC, 1, 5);
        grid.add(btSave, 0 , 10);
        grid.add(goBack, 1 , 10);
        grid.add(btDelete, 2, 10);
        grid.add(help, 3, 10);
        
        btSave.setOnAction(e -> saveActionPatient());
        btDelete.setOnAction(e -> deleteActionPatient());

        fillInfoDoctor(record);

        pane.setTop(grid);

        center.getChildren().add(pane);

        btSave.setOnAction(e -> saveActionPatient());
        goBack.setOnAction(e -> showPatientContents(record));
        btDelete.setOnAction(e -> deleteActionPatient());
        help.setOnAction((e -> HelpMenu.helpMenu("EditPat")));

    }
    
    public void DoctorAdd() {

        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Label lfName = new Label("First Name");
        Label llName = new Label("Last Name");
        Label lSpeciality = new Label("Speciality");
        Button btaddDoctor = new Button("Add");
        Button goBack = new Button("Go Back");
        Button help = new Button("Help");

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(lfName, 0, 1);
        grid.add(tffName, 1, 1);
        grid.add(llName, 0, 2);
        grid.add(tflName, 1, 2);
        grid.add(lSpeciality, 0, 3);
        grid.add(specialitiesList, 1, 3);
        grid.add(lMonday, 0, 4);
        grid.add(cbMonday, 1, 4);
        grid.add(lTuesday, 0, 5);
        grid.add(cbTuesday, 1, 5);
        grid.add(lWednesday, 0, 6);
        grid.add(cbWednesday, 1, 6);
        grid.add(lThursday, 0, 7);
        grid.add(cbThursday, 1, 7);
        grid.add(lFriday, 0, 8);
        grid.add(cbFriday, 1, 8);
        grid.add(btaddDoctor, 0, 9);
        grid.add(goBack, 0, 10);
        grid.add(goBack, 0, 11);

        pane.setCenter(grid);

        center.getChildren().add(pane);

        btaddDoctor.setOnAction(e -> insertDataDoctor());
        goBack.setOnAction(e -> docCenter());
        help.setOnAction((e -> HelpMenu.helpMenu("AddDoc")));
    }

    public void PatientAdd() {

        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Label lfName = new Label("First Name");
        Label llName = new Label("Last Name");
        Label address = new Label("Address");
        Label city = new Label("City");
        Label state = new Label("State");
        Label pNumber = new Label("Phone Number:");
        Label zipCode = new Label("Zip Code:");
        Button btaddDoctor = new Button("Add");
        Button goBack = new Button("Go Back");
        Button help = new Button("Help");

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(lfName, 0, 1);
        grid.add(tffName, 1, 1);
        grid.add(llName, 0, 2);
        grid.add(tflName, 1, 2);
        grid.add(address, 0, 3);
        grid.add(tfAddress, 1, 3);
        grid.add(city, 0, 4);
        grid.add(tfCity, 1, 4);
        grid.add(state, 0, 5);
        grid.add(tfState, 1, 5);
        grid.add(pNumber, 0, 6);
        grid.add(tfpNumber, 1, 6);
        grid.add(zipCode, 0, 7);
        grid.add(tfZipC, 1, 7);
        grid.add(btaddDoctor, 0, 9);
        grid.add(goBack, 0, 10);
        grid.add(help, 0, 10);

        pane.setCenter(grid);

        center.getChildren().add(pane);

        btaddDoctor.setOnAction(e -> insertDataPatient());
        goBack.setOnAction(e -> patCenter());
        help.setOnAction((e -> HelpMenu.helpMenu("HelpPat")));
    }
    
    private void fillSpecialities() {
        try {
            String queryString = "select * from Speciality";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                specialitiesList.getItems().add(resultSet.getString("sName"));
            }
        } catch (SQLException ex) {
        }
    }

    private void insertDataDoctor() {
        String queryString = "insert into Doctor (fName, lName, Speciality, Monday, Tuesday, Wednesday, Thursday, Friday) VALUES ('" + tffName.getText() + "', "
                + "'" + tflName.getText() + "','" + specialitiesList.getSelectionModel().getSelectedItem().toString() + "', " + cbMonday.isSelected() + ", " + cbTuesday.isSelected() + ""
                + ", " + cbWednesday.isSelected() + ""
                + ", " + cbThursday.isSelected() + ""
                + ", " + cbFriday.isSelected() + ")";
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(medicalapplication2.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Inserted");
    }

    private void insertDataPatient() {
        String queryString = "insert into Patient (fName, lName, Address, City, State, pNumber, zipCode) VALUES ('" + tffName.getText() + ""
                + "','" + tflName.getText() + "','" + tfAddress.getText() + "','" + tfCity.getText() + "','" + tfState.getText() + ""
                + "','" + tfpNumber.getText() + ""
                + "','" + tfZipC.getText() + "')";
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(medicalapplication2.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Inserted");
    }
    
    //This function clears current center and updates it to the center displaying the calendar
    public void calCenter() {
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        HBox x = new HBox();
        Button add = new Button("+");
        Button remove = new Button("-");
        x.getChildren().addAll(new Text("Calendar"), add, remove);
        x.getChildren().add(new Text("\n Insert Code for Calendar Here"));
        center.getChildren().add(x);
    }
    
    private void fillInfoDoctor(double record) {

        int idNum = (int) Math.round(record);

        try {
            String queryString = "select * from Doctor where (ID = '" + Integer.toString(idNum) + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                id = idNum;
                cbMonday.setSelected(resultSet.getBoolean("Monday"));
                cbTuesday.setSelected(resultSet.getBoolean("Tuesday"));
                cbWednesday.setSelected(resultSet.getBoolean("Wednesday"));
                cbThursday.setSelected(resultSet.getBoolean("Thursday"));
                cbFriday.setSelected(resultSet.getBoolean("Friday"));
                speciality = resultSet.getString("Speciality");
                chooseSpecialityOption();
                System.out.println(id);
            }

        } catch (SQLException ex) {
        }
        System.out.println("loaded");
    }
    
    private void fillInfoPatient(double record){
        
        int idNum = (int) Math.round(record);
        
        try {
            String queryString = "select * from Patient where ID =" + idNum;
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                id = resultSet.getInt("ID");
                patientName.setText(resultSet.getString("fName") + " " + resultSet.getString("lName"));
                tfAddress.setText(resultSet.getString("Address"));
                tfCity.setText(resultSet.getString("City"));
                tfState.setText(resultSet.getString("State"));
                tfZipC.setText(resultSet.getString("zipCode"));
                tfpNumber.setText(resultSet.getString("pNumber"));
            }
                
        } catch (SQLException ex) {
        }
        System.out.println("loaded");
    }

    private void chooseSpecialityOption() {
        try {
            String queryString = "select * from Speciality WHERE sName ='" + speciality + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                specialitiesList.getSelectionModel().select(resultSet.getInt("ID") - 1);
            }
        } catch (SQLException ex) {
        }
    }

    private void saveActionDoctor() {
        try {
            String queryString2 = "UPDATE Doctor SET "
                    + "Speciality='" + specialitiesList.getSelectionModel().getSelectedItem().toString() + "', "
                    + "Monday='" + cbMonday.isSelected() + "', "
                    + "Tuesday='" + cbTuesday.isSelected() + "', "
                    + "Wednesday='" + cbWednesday.isSelected() + "', "
                    + "Thursday='" + cbThursday.isSelected() + "', "
                    + "Friday='" + cbFriday.isSelected() + "' "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString2);

        } catch (SQLException ex) {
        }
        System.out.println("done");
    }

    private void saveActionPatient(){
        try {
            String queryString2 = "UPDATE Patient SET "
                    + "Address='"+tfAddress.getText()+"', "
                    + "City='"+tfCity.getText()+"', "
                    + "State='"+tfState.getText()+"', "
                    + "pNumber='"+tfpNumber.getText()+"', "
                    + "zipCode='"+tfZipC.getText()+"' "
                    + "WHERE (ID='"+id+"')";
            stmt.executeUpdate(queryString2);
                
        } catch (SQLException ex) {
        }
        System.out.println("done");
    }
    
    private void deleteActionDoctor() {
        try {
            String queryString4 = "DELETE FROM Doctor "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString4);

        } catch (SQLException ex) {
        }
        System.out.println("done");
        docCenter();
    }

    private void deleteActionPatient(){
        try {
            String queryString4 = "DELETE FROM Patient "
                    + "WHERE (ID='"+id+"')";
            stmt.executeUpdate(queryString4);
                
        } catch (SQLException ex) {
        }
        System.out.println("done");
    }
    
    private void addAppointments(double record) {
        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        Button goBack = new Button("Go Back");
        String fName = "";
        String lName = "";
        int idNum = (int) Math.round(record);
        
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);
        
        try {
            String queryString = "select * from Patient where (ID = '" + Integer.toString(idNum) + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                fName = resultSet.getString("fName");
                lName = resultSet.getString("lName");
            }

        } catch (SQLException ex) {
        }
        
        grid.add(lPatient, 0, 1);
        grid.add(new Label(fName + " " + lName), 1, 1);
        grid.add(lDoctor, 0, 2);
        grid.add(doctorList, 1, 2);
        grid.add(lDate, 0, 3);
        grid.add(datePicker, 1, 3);
        grid.add(btNext, 1, 5);
        grid.add(goBack, 2, 5);
        //root.getChildren().add(datePicker);
        
        
        pane.setCenter(grid);

        center.getChildren().add(pane);
        
        doctorList.setOnAction(e -> fillDocAlert());
        goBack.setOnAction(e -> showPatientContents(record));
        btNext.setOnAction(e -> nextAction(record));
    }
    
    
    private void fillDoctors() {
        try {
            String queryString = "select * from Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                String pat = resultSet.getString("fName")+" "+resultSet.getString("lName");
                doctorList.getItems().add(resultSet.getString("fName")+" "+resultSet.getString("lName"));
            }
        } catch (SQLException ex) {
        }
    }
    
    private void fillDocAlert(){
        
        Boolean mon = false;
        Boolean tue = false;
        Boolean wen = false;
        Boolean thu = false;
        Boolean fri = false;
        StringBuilder finalMessage = new StringBuilder();
        String docMessage;
        String fullName = doctorList.getSelectionModel().getSelectedItem().toString();
        finalMessage.append("The doctor ").append(fullName).append("  works on: ");
        int idx = fullName.lastIndexOf(' ');
        if (idx == -1)
            throw new IllegalArgumentException("Only a single name: " + fullName);
        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);
        try {
            String queryString = "select Monday,Tuesday,Wednesday,Thursday,Friday from Doctor where (fName = '"+firstName+"') AND (lName = '"+lastName+"')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                mon = Boolean.valueOf(resultSet.getString("Monday"));
                tue = Boolean.valueOf(resultSet.getString("Tuesday"));
                wen = Boolean.valueOf(resultSet.getString("Wednesday"));
                thu = Boolean.valueOf(resultSet.getString("Thursday"));
                fri = Boolean.valueOf(resultSet.getString("Friday"));      
            }
            
            if(mon)
            {
                finalMessage.append("- Monday -");
            }
            if(tue)
            {
                finalMessage.append("- Tuesday -");
            }
            if(wen)
            {
                finalMessage.append("- Wednesday -");
            }
            if(thu)
            {
                finalMessage.append("- Thursday -");
            }
            if(fri)
            {
                finalMessage.append("- Friday -");
            }

            docMessage = finalMessage.toString();

            docAlert.setTitle("Information Dialog");
            docAlert.setHeaderText(null);
            docAlert.setContentText(docMessage);
            docAlert.showAndWait();
                
        } catch (SQLException ex) {
        }
        
    }
    
    private String doctorDay()
    {
        String dayOfWeek = " ";
        try {
            Date date = new SimpleDateFormat("yyyy-M-d").parse(datePicker.getValue().toString());
            dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        } catch (ParseException ex) {
            Logger.getLogger(AppointmentCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dayOfWeek;
    }
    
    private void nextAction(double record)
    {
        String fName = "";
        String lName = "";
        int idNum = (int) Math.round(record);
        
        try {
            String queryString = "select * from Patient where (ID = '" + Integer.toString(idNum) + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                fName = resultSet.getString("fName");
                lName = resultSet.getString("lName");
            }

        } catch (SQLException ex) {
        }
        
        if(!doctorList.getSelectionModel().isEmpty()&&datePicker.getValue()!=null)
        {
            Boolean works = false;
            String fullName = doctorList.getSelectionModel().getSelectedItem().toString();
            int idx = fullName.lastIndexOf(' ');
            if (idx == -1)
                throw new IllegalArgumentException("Only a single name: " + fullName);
            try {

                String queryString = "select * from Doctor where (fName = '"+fName+"') AND (lName = '"+lName+"')";
                ResultSet resultSet = stmt.executeQuery(queryString);
                while (resultSet.next())
                {
                    works = resultSet.getBoolean(doctorDay());
                }

                if(works)
                {
                    addAppointment(record);
                    AppointmentScheduler newAppointment = new AppointmentScheduler(idNum, getDocID(), datePicker.getValue());
                    newAppointment.display();
                }
                else
                {
                    docAlert.setTitle("Information Dialog");
                    docAlert.setHeaderText(null);
                    docAlert.setContentText("I'm sorry but please recheck your information");
                    docAlert.showAndWait();
                }
            } catch (SQLException ex) {
            }
        }
        else
                {
                    docAlert.setTitle("Information Dialog");
                    docAlert.setHeaderText(null);
                    docAlert.setContentText("I'm sorry but please recheck your information");
                    docAlert.showAndWait();
                }
    }
    
    private int getDocID()
    {
        int docID=0;
        String fullName = doctorList.getSelectionModel().getSelectedItem().toString();
        int idx = fullName.lastIndexOf(' ');
        if (idx == -1)
            throw new IllegalArgumentException("Only a single name: " + fullName);
        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);
        try {
            String queryString = "select * from Doctor where (fName = '"+firstName+"') AND (lName = '"+lastName+"')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                docID = resultSet.getInt("ID");
            }
        } catch (SQLException ex) {
        }
        return docID;
    }
    
    private void addAppointment(double record){
        int idNum = (int) Math.round(record);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
                String queryString2 = "INSERT INTO Appointments (Patient, appDate, Doctor) VALUES ("+ idNum +", #"+formatter.format(datePicker.getValue())+"#,"+getDocID()+")";
        try {
            stmt.executeUpdate(queryString2);
        } catch (SQLException ex) {
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
