/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advsysprojfinal;

import Appointment.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author aasim
 */
public class CalVBox extends VBox {

    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    ArrayList<Appointment> list = new ArrayList<>(0);

    CalVBox() {
        fillListsWeek();
        setSelf();
    }

    private void setSelf() {

        BorderPane pane = new BorderPane();
        String temp = "";
        VBox grid2 = new VBox();
        Button filter = new Button("Search Appointment");
        Button unFilter = new Button("This Week's Appointments");
        Button select = new Button("Edit Selected");
        Button add = new Button("Add Appointment");
        Button help = new Button("Help");

        AppointmentCreator x = new AppointmentCreator();
        add.setOnAction(e -> x.start());

        grid2.setPadding(new Insets(0, 15, 15, 15));
        grid2.getChildren().addAll(filter, unFilter, select, add, help);
        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

        //
        TableView tableView = new TableView();
        tableView.setEditable(false);

        TableColumn<Integer, Appointment> column1 = new TableColumn<>("Appointment ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("apptID"));

        TableColumn<Integer, Appointment> column2 = new TableColumn<>("Patient ID");
        column2.setCellValueFactory(new PropertyValueFactory<>("patID"));

        TableColumn<Integer, Appointment> column3 = new TableColumn<>("Doctor ID");
        column2.setCellValueFactory(new PropertyValueFactory<>("docID"));

        TableColumn<Date, Appointment> column4 = new TableColumn<>("Date");
        column2.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<String, Appointment> column5 = new TableColumn<>("Results");
        column2.setCellValueFactory(new PropertyValueFactory<>("result"));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        for (Appointment xaz : list) {
            tableView.getItems().add(xaz);
        }

        pane.setLeft(grid2);
        pane.setCenter(tableView);

        this.getChildren().add(pane);

    }

    private void fillListsWeek() {
        list.clear();
        int ida = 0;
        int idb = 0;
        int idc = 0;
        Date date;
        Times time;
        String result;
        try {
            String queryString = "SELECT * FROM Appointments WHERE (((Appointments.appDate) Between Date()-13 And Date()+13));";
            ResultSet resultSet = stmt.executeQuery(queryString);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 1; i < rsMetaData.getColumnCount(); i = i + 15) {
                    ida = resultSet.getInt(i);
                    idb = resultSet.getInt(i + 1);
                    idc = resultSet.getInt(i + 2);
                    date = resultSet.getDate(i + 3);
//                    time = new Times(resultSet.getBoolean(i + 4), resultSet.getBoolean(i + 5), resultSet.getBoolean(i + 6), resultSet.getBoolean(i + 7), resultSet.getBoolean(i + 8), resultSet.getBoolean(i + 9), resultSet.getBoolean(i + 10), resultSet.getBoolean(i + 11), resultSet.getBoolean(i + 12), resultSet.getBoolean(i + 13), resultSet.getBoolean(i + 14));
                    Times asz = new Times();

                    result = resultSet.getString(i + 15);
                    list.add(new Appointment(ida, idb, idc, date, asz, result));

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CalVBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
