/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advsysprojfinal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import Appointment.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author aasim
 */
public class PatVBox extends VBox {

    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    final private ListView<String> selectView = new ListView();
    private int id;

    //
    private final Label lDate = new Label("Choose Date");
    private final DatePicker datePicker = new DatePicker();
    private final ComboBox doctorList = new ComboBox();
    private final Button btNext = new Button("Next");
    private final Label lDoctor = new Label("Doctor Name");
    ComboBox specialitiesList = new ComboBox();
    TextField tffName = new TextField();
    TextField tflName = new TextField();
    CheckBox cbMonday = new CheckBox();
    CheckBox cbTuesday = new CheckBox();
    CheckBox cbWednesday = new CheckBox();
    CheckBox cbThursday = new CheckBox();
    CheckBox cbFriday = new CheckBox();
    Alert docAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert docDoesntWork = new Alert(Alert.AlertType.INFORMATION);

    //
    private String speciality;

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

    PatVBox() {
        setSelf();
    }

    private void setSelf() {
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

        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

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

        this.getChildren().add(pane);

    }

    private void PatientAdd() {
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

        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

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

        this.getChildren().add(pane);

        btaddDoctor.setOnAction(e -> insertDataPatient());
        goBack.setOnAction(e -> setSelf());
        help.setOnAction((e -> HelpMenu.helpMenu("HelpPat")));

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

        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

        try {
            String queryString = "select * from Patient JOIN Appointments ON Patient.ID = Appointments.IDPatient WHERE Patient.ID = " + idNum + " OR Appointments.IDPatient =" + idNum;

            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                if (i != 9 && i != 10 && i != 11) {
                    grid2.add(new Label(rsMetaData.getColumnName(i)), 0, i);
                }
            }

            // Iterate through the result and print the student names
            int x = 1;
            while (resultSet.next()) {
                if (x == 1) {
                    for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                        if (i != 9 && i != 10 && i != 11) {
                            grid2.add(new Label(resultSet.getNString(i)), x, i);
                        }
                    }
                } else {
                    for (int i = 12; i <= rsMetaData.getColumnCount(); i++) {
                        if (i != 9 && i != 10 && i != 11) {
                            grid2.add(new Label(resultSet.getNString(i)), x, i);
                        }
                    }
                }
                x++;
            }
        } catch (SQLException ex) {
        }

        pane.setCenter(new ScrollPane(grid2));
        pane.setBottom(grid);
        this.getChildren().add(pane);

        edit.setOnAction(e -> EditPatientContenets(record));
        goBack.setOnAction(e -> setSelf());
        help.setOnAction((e -> HelpMenu.helpMenu("ContentPat")));
    }

    private void insertDataPatient() {
        String queryString = "insert into Patient (fName, lName, Address, City, State, pNumber, zipCode) VALUES ('" + tffName.getText() + ""
                + "','" + tflName.getText() + "','" + tfAddress.getText() + "','" + tfCity.getText() + "','" + tfState.getText() + ""
                + "','" + tfpNumber.getText() + ""
                + "','" + tfZipC.getText() + "')";
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(DocVBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Inserted");
    }

    public void EditPatientContenets(double record) {

        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Button btSave = new Button("Save");
        Button btDelete = new Button("Delete");
        Button goBack = new Button("Go Back");
        Button help = new Button("Help");

        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

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
        grid.add(btSave, 0, 10);
        grid.add(goBack, 1, 10);
        grid.add(btDelete, 2, 10);
        grid.add(help, 3, 10);

        btSave.setOnAction(e -> saveActionPatient());
        btDelete.setOnAction(e -> deleteActionPatient());

        fillInfoDoctor(record);

        pane.setTop(grid);

        this.getChildren().add(pane);

        btSave.setOnAction(e -> saveActionPatient());
        goBack.setOnAction(e -> showPatientContents(record));
        btDelete.setOnAction(e -> deleteActionPatient());
        help.setOnAction((e -> HelpMenu.helpMenu("EditPat")));

    }

    private void fillInfoPatient(double record) {
        System.out.println("fillInfoPatient");
        int idNum = (int) Math.round(record);

        try {
            String queryString = "select * from Patient where ID =" + idNum;
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
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

    private void saveActionPatient() {
        System.out.println("SaveActionPat");

        try {
            String queryString2 = "UPDATE Patient SET "
                    + "Address='" + tfAddress.getText() + "', "
                    + "City='" + tfCity.getText() + "', "
                    + "State='" + tfState.getText() + "', "
                    + "pNumber='" + tfpNumber.getText() + "', "
                    + "zipCode='" + tfZipC.getText() + "' "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString2);

        } catch (SQLException ex) {
        }
        System.out.println("done");
    }

    private void deleteActionPatient() {
        try {
            String queryString4 = "DELETE FROM Patient "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString4);

        } catch (SQLException ex) {
        }
        System.out.println("done");
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

    private void nextAction(double record) {
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

        if (!doctorList.getSelectionModel().isEmpty() && datePicker.getValue() != null) {
            Boolean works = false;
            String fullName = doctorList.getSelectionModel().getSelectedItem().toString();
            int idx = fullName.lastIndexOf(' ');
            if (idx == -1) {
                throw new IllegalArgumentException("Only a single name: " + fullName);
            }
            try {

                String queryString = "select * from Doctor where (fName = '" + fName + "') AND (lName = '" + lName + "')";
                ResultSet resultSet = stmt.executeQuery(queryString);
                while (resultSet.next()) {
                    works = resultSet.getBoolean(doctorDay());
                }

                if (works) {

                    AppointmentScheduler newAppointment = new AppointmentScheduler(idNum, getDocID(), datePicker.getValue());
                    newAppointment.display();
                } else {
                    docAlert.setTitle("Information Dialog");
                    docAlert.setHeaderText(null);
                    docAlert.setContentText("I'm sorry but please recheck your information");
                    docAlert.showAndWait();
                }
            } catch (SQLException ex) {
            }
        } else {
            docAlert.setTitle("Information Dialog");
            docAlert.setHeaderText(null);
            docAlert.setContentText("I'm sorry but please recheck your information");
            docAlert.showAndWait();
        }
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

    private String doctorDay() {
        String dayOfWeek = " ";
        try {
            Date date = (Date) new SimpleDateFormat("yyyy-M-d").parse(datePicker.getValue().toString());
            dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        } catch (ParseException ex) {
            Logger.getLogger(PatVBox.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dayOfWeek;
    }

    private int getDocID() {
        int docID = 0;
        String fullName = doctorList.getSelectionModel().getSelectedItem().toString();
        int idx = fullName.lastIndexOf(' ');
        if (idx == -1) {
            throw new IllegalArgumentException("Only a single name: " + fullName);
        }
        String firstName = fullName.substring(0, idx);
        String lastName = fullName.substring(idx + 1);
        try {
            String queryString = "select * from Doctor where (fName = '" + firstName + "') AND (lName = '" + lastName + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                docID = resultSet.getInt("ID");
            }
        } catch (SQLException ex) {
        }
        return docID;
    }

}
