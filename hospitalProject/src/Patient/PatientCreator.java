/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patient;

import Connector.DBConnector;
import Doctor.DoctorCreator;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author simonyunes
 */
public class PatientCreator extends Application {
    
    private final GridPane grid = new GridPane();
    private final Label lfName = new Label("First Name");
    private final Label llName = new Label("Last Name");
    private final TextField tffName = new TextField();
    private final TextField tflName = new TextField();
    private final Button btaddDoctor = new Button("Add");

    // Statement for executing queries
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    
    @Override
    public void start(Stage primaryStage) {
        
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //grid.add(lID, 0, 1);
        //grid.add(tfID, 1, 1);
        grid.add(lfName, 0, 2);
        grid.add(tffName, 1, 2);
        grid.add(llName, 0, 3);
        grid.add(tflName, 1, 3);
        grid.add(btaddDoctor, 0, 5);
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene); 
        primaryStage.show();

        btaddDoctor.setOnAction(e -> insertData());
    }

    /**
     * @param args the command line arguments
     */
    
        
    private void insertData()
    {
        //String sid = tfID.getText();
        String sfName= tffName.getText();
        String slName= tflName.getText();
        String queryString = "insert into Patient (fName, lName) VALUES ('"+sfName+"', '"+slName+"')";
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
