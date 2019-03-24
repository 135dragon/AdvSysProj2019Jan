/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

import Connector.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author simonyunes
 */
public class DoctorCreator extends Application {
    
    private final GridPane grid = new GridPane();
    private final Label lfName = new Label("First Name");
    private final Label llName = new Label("Last Name");
    private final Label lSpeciality = new Label("Speciality");
    private final Label lMonday = new Label("Monday");
    private final Label lTuesday = new Label("Tuesday");
    private final Label lWednesday = new Label("Wednesday");
    private final Label lThursday = new Label("Thursday");
    private final Label lFriday = new Label("Friday");
    private final TextField tffName = new TextField();
    private final TextField tflName = new TextField();
    private final CheckBox cbMonday = new CheckBox();
    private final CheckBox cbTuesday = new CheckBox();
    private final CheckBox cbWednesday = new CheckBox();
    private final CheckBox cbThursday = new CheckBox();
    private final CheckBox cbFriday = new CheckBox();
    private final Button btaddDoctor = new Button("Add");
    private final ComboBox specialitiesList = new ComboBox();

    // Statement for executing queries
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    
    @Override
    public void start(Stage primaryStage) {
        
        fillSpecialities();
        
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
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setTitle("Add Doctor");
        primaryStage.setScene(scene); 
        primaryStage.show();

        btaddDoctor.setOnAction(e -> insertData());
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

    /**
     * @param args the command line arguments
     */
        
    private void insertData()
    {
        String queryString = "insert into Doctor (fName, lName, Speciality, Monday, Tuesday, Wednesday, Thursday, Friday) VALUES ('"+tffName.getText()+"', "
                + "'"+tflName.getText()+"','"+specialitiesList.getSelectionModel().getSelectedItem().toString()+"', "+cbMonday.isSelected()+", "+cbTuesday.isSelected()+""
                + ", "+cbWednesday.isSelected()+""
                + ", "+cbThursday.isSelected()+""
                + ", "+cbFriday.isSelected()+")";
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(DoctorCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Inserted");
    }
        
            
    public static void main(String[] args) {
        launch(args);
    }
    
}

