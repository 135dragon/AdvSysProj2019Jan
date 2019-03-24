/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

import Connector.DBConnector;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DoctorReader extends Application {

    private final TextArea taResult = new TextArea();
    private final Button btShowContents = new Button("Show Contents");

    // Statement for executing queries
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(btShowContents);
        hBox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(new ScrollPane(taResult));
        pane.setTop(hBox);
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 500, 200);
        primaryStage.setTitle("Read Doctor"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage  


        btShowContents.setOnAction(e -> showContents());
    }

    private void showContents() {
        try {
            String queryString = "select * from Doctor";

            ResultSet resultSet = stmt.executeQuery(queryString);

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
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
