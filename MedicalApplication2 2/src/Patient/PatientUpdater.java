/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patient;

import Connector.DBConnector;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author spyunes
 */
public class PatientUpdater extends Application {
    
    private final Label lPatient = new Label("Patient Name");
    private final ComboBox patientList = new ComboBox();
    private final Label lAddress = new Label("Address");
    private final Label lCity = new Label("City");
    private final Label lState = new Label("State");
    private final Label lpNumber = new Label("Phone");
    private final Label lZipC = new Label("ZipCode");
    private final TextField tfAddress = new TextField();
    private final TextField tfCity = new TextField();
    private final TextField tfState = new TextField();
    private final TextField tfpNumber = new TextField();
    private final TextField tfZipC = new TextField();
    private final Button btSave = new Button("Save");
    private final Button btHistory = new Button("History");
    private final Button btDelete = new Button("Delete");
    private final TextArea taResult = new TextArea();
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    private int id;
    
    
    @Override
    public void start(Stage primaryStage) {

        
        initializePatients();
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        grid.add(lPatient, 0, 0);
        grid.add(patientList, 1, 0);
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
        grid.add(btHistory, 1 , 10);
        grid.add(btDelete, 2, 10);
        
        Scene scene = new Scene(grid, 300, 350);
        
        primaryStage.setTitle("Reservation");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        patientList.setOnAction(e -> fillInfo());
        btSave.setOnAction(e -> saveAction());
        btHistory.setOnAction(e -> historyAction().showAndWait());
        btDelete.setOnAction(e -> deleteAction());
    
    }
    
    private void initializePatients(){
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

    
    private void fillInfo(){
        
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
                id = resultSet.getInt("ID");
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
    
    private void saveAction(){
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
    
    private Stage historyAction(){
        Stage history = new Stage();
        try {
            ZoneId zonedId = ZoneId.of( "America/Montreal" );
            LocalDate today = LocalDate.now( zonedId );
            String queryString3 = "select appDate, Result from Appointments WHERE (appDate <#"+today+"# AND IDPatient ='"+id+"')";

            ResultSet resultSet = stmt.executeQuery(queryString3);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                taResult.appendText(rsMetaData.getColumnName(i) + "    ");
            }
            taResult.appendText("\n");

            // Iterate through the result and print the student names
            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    taResult.appendText(resultSet.getObject(i) + "     ");
                }
                taResult.appendText("\n");
            }
        } catch (SQLException ex) {
        }
        history.setScene(new Scene(new HBox(4, new ScrollPane(taResult)), 500, 300));
        return history;
    }
    
    private void deleteAction(){
        try {
            String queryString4 = "DELETE FROM Patient "
                    + "WHERE (ID='"+id+"')";
            stmt.executeUpdate(queryString4);
                
        } catch (SQLException ex) {
        }
        System.out.println("done");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
