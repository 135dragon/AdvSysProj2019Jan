/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appointment;

import Connector.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author spyunes
 */
public class AppointmentCreator extends Application {
    
    private final Label lPatient = new Label("Patient Name");
    private final Label lDoctor = new Label("Doctor Name");
    private final Label lDate = new Label("Choose Date");
    private final DatePicker datePicker = new DatePicker();
    private final ComboBox patientList = new ComboBox();
    private final ComboBox doctorList = new ComboBox();
    private final Button btNext = new Button("Next");
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    Alert docAlert = new Alert(AlertType.INFORMATION);
    Alert docDoesntWork = new Alert(AlertType.INFORMATION);
    
    @Override
    public void start(Stage primaryStage) {
        
        fillDoctors();
        fillPatients();
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        grid.add(lPatient, 0, 1);
        grid.add(patientList, 1, 1);
        grid.add(lDoctor, 0, 2);
        grid.add(doctorList, 1, 2);
        grid.add(lDate, 0, 3);
        grid.add(datePicker, 1, 3);
        grid.add(btNext, 1, 5);
        //root.getChildren().add(datePicker);
        
        
        Scene scene = new Scene(grid, 300, 250);
        
        primaryStage.setTitle("Reservation");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        doctorList.setOnAction(e -> fillDocAlert());
        
        btNext.setOnAction(e -> nextAction());

    }

    private void fillPatients() {
        try {
            String queryString = "select * from Patient";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                String pat = resultSet.getString("fName")+" "+resultSet.getString("lName");
                patientList.getItems().add(resultSet.getString("fName")+" "+resultSet.getString("lName"));
            }
        } catch (SQLException ex) {
        }
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
    
    private void nextAction()
    {
        if(!patientList.getSelectionModel().isEmpty()&&!doctorList.getSelectionModel().isEmpty()&&datePicker.getValue()!=null)
        {
            Boolean works = false;
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
                    works = resultSet.getBoolean(doctorDay());
                }

                if(works)
                {
                    addAppointment();
                    AppointmentScheduler newAppointment = new AppointmentScheduler(getPatID(), getDocID(), datePicker.getValue());
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
    
    private int getPatID()
    {
        int patID=0;
        String fullName = patientList.getSelectionModel().getSelectedItem().toString();
        int idx = fullName.lastIndexOf(' ');
        if (idx == -1)
            throw new IllegalArgumentException("Only a single name: " + fullName);
        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);
        try {
            String queryString = "select * from Patient where (fName = '"+firstName+"') AND (lName = '"+lastName+"')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                patID = resultSet.getInt("ID");
            }
        } catch (SQLException ex) {
        }
        return patID;
    }
    
    private void addAppointment(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
                String queryString2 = "INSERT INTO Appointments (Patient, appDate, Doctor) VALUES ("+getPatID()+", #"+formatter.format(datePicker.getValue())+"#,"+getDocID()+")";
        try {
            stmt.executeUpdate(queryString2);
        } catch (SQLException ex) {
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
