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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author spyunes
 */
public class AppointmentResult {
    
    private final int idDoct;
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    private final ComboBox oldApp = new ComboBox();
    private final Button btSave = new Button("Save");
    private final Label lResult = new Label("Result");
    private final TextField tfResult = new TextField();
    //private final LocalDate appDate;

    public AppointmentResult(int idDoct) {
        this.idDoct = idDoct;
    }
    
    
    public void display() {

    initializeComboBox();
    
    Stage window = new Stage();
    GridPane pan = new GridPane();
    
    pan.add(oldApp, 0, 0);
    pan.add(lResult, 0, 1);
    pan.add(tfResult, 1, 1);
    pan.add(btSave, 0, 2);
    
    btSave.setOnAction(e -> btSave());
    
    
    Scene scene = new Scene(pan);
    window.setScene(scene);
    window.setTitle("Add Result");
    window.showAndWait();
    }

    private void initializeComboBox() {
        Date today = Calendar.getInstance().getTime();
        System.out.println(idDoct);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "SELECT  Appointments.ID, Patient.fName, Patient.lName, Appointments.appDate " +
                                 "FROM Appointments INNER JOIN Patient ON Appointments.IDPatient = Patient.ID " +
                                 "WHERE Appointments.idDoctor = "+idDoct+" AND Appointments.appDate < Date();";
            //String queryString = "select * from Appointments WHERE IDDoctor = '"+idDoct+"'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                //System.out.println(resultSet.getString("Patient.fName")+" "+resultSet.getString("appDate"));
                oldApp.getItems().addAll(resultSet.getInt("Appointments.ID")+" "+resultSet.getString("Patient.fName")+" "+resultSet.getString("Patient.lName")+" "+resultSet.getString("appDate"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void btSave() {
        
        String ID = oldApp.getSelectionModel().getSelectedItem().toString();
        if(ID.contains(" ")){
            ID= ID.substring(0, ID.indexOf(" ")); 
         }
        try {
            String queryString2 = "UPDATE Appointments SET "
                    + "Result='"+tfResult.getText()+"' "
                    + "WHERE ID="+ID+";";
            stmt.executeUpdate(queryString2);
                
        } catch (SQLException ex) {
        }
        
        System.out.println("updated");

    }
    
}
