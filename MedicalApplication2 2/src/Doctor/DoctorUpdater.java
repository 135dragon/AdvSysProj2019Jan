/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;


import Appointment.AppointmentResult;
import Connector.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author spyunes
 */
public class DoctorUpdater extends Application {
    
    private final Label lDoctor = new Label("Doctor Name");
    private final ComboBox patientList = new ComboBox();
    private final ComboBox specialitiesList = new ComboBox();
    private final Label lMonday = new Label("Monday");
    private final Label lTuesday = new Label("Tuesday");
    private final Label lWednesday = new Label("Wednesday");
    private final Label lThursday = new Label("Thursday");
    private final Label lFriday = new Label("Friday");
    private final CheckBox cbMonday = new CheckBox();
    private final CheckBox cbTuesday = new CheckBox();
    private final CheckBox cbWednesday = new CheckBox();
    private final CheckBox cbThursday = new CheckBox();
    private final CheckBox cbFriday = new CheckBox();
    private final Button btSave = new Button("Save");
    private final Button btDelete = new Button("Delete");
    private final Button btAppResult = new Button("Result");
    private String speciality;
    private int id;
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    
    @Override
    public void start(Stage primaryStage) {

        initializePatients();
        fillSpecialities();
        
        StackPane root = new StackPane();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        grid.add(lDoctor, 0, 0);
        grid.add(patientList, 1, 0);
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
        grid.add(specialitiesList,0, 6);
        grid.add(btSave, 0 , 10);
        grid.add(btAppResult, 1, 10);
        grid.add(btDelete, 2, 10);
        
        Scene scene = new Scene(grid, 300, 350);
        
        primaryStage.setTitle("Update Doctor");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        patientList.setOnAction(e -> fillInfo());
        btSave.setOnAction(e -> saveAction());
        btAppResult.setOnAction(e -> appResultAction());
        btDelete.setOnAction(e -> deleteAction());
    
    }
    
    private void initializePatients(){
        try {
            String queryString = "select * from Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                String pat = resultSet.getString("fName")+" "+resultSet.getString("lName");
                patientList.getItems().add(resultSet.getString("fName")+" "+resultSet.getString("lName"));
            }
        } catch (SQLException ex) {
        }
    }
    
    private void fillInfo(){
        
        String fullName = patientList.getSelectionModel().getSelectedItem().toString();
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
                id = resultSet.getInt("ID");
                cbMonday.setSelected(resultSet.getBoolean("Monday"));
                cbTuesday.setSelected(resultSet.getBoolean("Tuesday"));
                cbWednesday.setSelected(resultSet.getBoolean("Wednesday"));
                cbThursday.setSelected(resultSet.getBoolean("Thursday"));
                cbFriday.setSelected(resultSet.getBoolean("Friday"));
                speciality=resultSet.getString("Speciality");
                chooseSpecialityOption();
                System.out.println(id);
            }
                
        } catch (SQLException ex) {
        }
        System.out.println("loaded");
    }
    
    private void chooseSpecialityOption(){
        try {
                String queryString = "select * from Speciality WHERE sName ='"+speciality+"'";
                ResultSet resultSet = stmt.executeQuery(queryString);
                while (resultSet.next())
                {
                    specialitiesList.getSelectionModel().select(resultSet.getInt("ID")-1);
                }
            } catch (SQLException ex) {
            }
    }
    
    private void fillSpecialities() {
        try {
            String queryString = "select * from Speciality";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next())
            {
                specialitiesList.getItems().add(resultSet.getString("sName"));
            }
        } catch (SQLException ex) {
        }
    }
    
    private void saveAction(){
        try {
            String queryString2 = "UPDATE Doctor SET "
                    + "Speciality='"+specialitiesList.getSelectionModel().getSelectedItem().toString()+"', "
                    + "Monday='"+cbMonday.isSelected()+"', "
                    + "Tuesday='"+cbTuesday.isSelected()+"', "
                    + "Wednesday='"+cbWednesday.isSelected()+"', "
                    + "Thursday='"+cbThursday.isSelected()+"', "
                    + "Friday='"+cbFriday.isSelected()+"' "
                    + "WHERE (ID='"+id+"')";
            stmt.executeUpdate(queryString2);
                
        } catch (SQLException ex) {
        }
        System.out.println("done");
    }
  
    
    private void deleteAction(){
        try {
            String queryString4 = "DELETE FROM Doctor "
                    + "WHERE (ID='"+id+"')";
            stmt.executeUpdate(queryString4);
                
        } catch (SQLException ex) {
        }
        System.out.println("done");
    }
    
    private void appResultAction(){
        AppointmentResult appRes = new AppointmentResult(id);
        appRes.display();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}