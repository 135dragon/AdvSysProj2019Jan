/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advsysprojfinal;

/**
 *
 * @author Charles Hulett
 */
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DocVBox extends VBox {

    private ComboBox specialitiesList = new ComboBox();
    //
    TextField tffName = new TextField();
    TextField tflName = new TextField();
    CheckBox cbMonday = new CheckBox();
    CheckBox cbTuesday = new CheckBox();
    CheckBox cbWednesday = new CheckBox();
    CheckBox cbThursday = new CheckBox();
    CheckBox cbFriday = new CheckBox();
    private final Label lDoctor = new Label("Doctor Name");
    private final Label lMonday = new Label("Monday");
    private final Label lTuesday = new Label("Tuesday");
    private final Label lWednesday = new Label("Wednesday");
    private final Label lThursday = new Label("Thursday");
    private final Label lFriday = new Label("Friday");
    //
    final private ListView<String> selectView = new ListView();
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();

    private final ComboBox doctorList = new ComboBox();

    private String speciality;
    private int id;

    DocVBox() {
        this.getChildren().add(new Label("Empty Center"));
        fillSpecialities();
        fillDoctors();
        setSelf();
    }

    private void fillDoctors() {
        try {
            String queryString = "SELECT fName, lName FROM Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while (resultSet.next()) {

                doctorList.getItems().add(resultSet.getString("fName") + " " + resultSet.getString("lName"));

            }
        } catch (Exception ex) {
            System.out.println("Error in fillDoctors function in DocVBox.java");
        }
    }

    private void fillSpecialities() {
        try {
            String queryString = "SELECT [sName] FROM Speciality";
            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

//             Iterate through the result and print the student names
            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    specialitiesList.getItems().add(resultSet.getObject(i));
                }
            }
        } catch (Exception ex) {
            System.out.println("Error in fillSpecialities function in DocVBox.java");
        }
    }

    private void setSelf() {

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

        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

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

        this.getChildren().add(pane);

    }

    private void DoctorAdd() {
        HBox x = new HBox();
        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Label lfName = new Label("First Name");
        Label llName = new Label("Last Name");
        Label lSpeciality = new Label("Speciality");
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

        pane.setCenter(grid);
        x.getChildren().add(pane);
        this.getChildren().add(x);

        btaddDoctor.setOnAction(e -> insertDataDoctor());
        goBack.setOnAction(e -> setSelf());
        help.setOnAction((e -> HelpMenu.helpMenu("AddDoc")));

    }

    private void filterListDoctor() {
        selectView.getItems().clear();
        try {
            String temp = new String();
            String spec = specialitiesList.getValue().toString();
            String queryString = "SELECT * FROM Doctor WHERE Speciality = '" + spec + " '";
            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount() - 5; i++) {
                    temp += resultSet.getObject(i) + "     ";
                }
                selectView.getItems().add(temp);
                temp = "";
            }
            //
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

        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

        try {
            String queryString = "SELECT * FROM Doctor";

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
        this.getChildren().add(pane);

        edit.setOnAction(e -> EditDoctorContents(record));
        goBack.setOnAction(e -> setSelf());
        help.setOnAction((e -> HelpMenu.helpMenu("ContentDoc")));
    }

    private void insertDataDoctor() {
        String queryString = "INSERT INTO Doctor (fName, lName, Speciality, Monday, Tuesday, Wednesday, Thursday, Friday) VALUES ('" + tffName.getText() + "', "
                + "'" + tflName.getText() + "','" + specialitiesList.getSelectionModel().getSelectedItem().toString() + "', " + cbMonday.isSelected() + ", " + cbTuesday.isSelected() + ""
                + ", " + cbWednesday.isSelected() + ""
                + ", " + cbThursday.isSelected() + ""
                + ", " + cbFriday.isSelected() + ")";
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(DocVBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Inserted");
    }

    public void EditDoctorContents(double record) {
        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Button btSave = new Button("Save");
        Button btDelete = new Button("Delete");
        Button goBack = new Button("Go Back");
        Button help = new Button("Help");
        int idNum = (int) Math.round(record);
        String fName = "";
        String lName = "";

        this.getChildren().clear();
        this.setPadding(new Insets(15, 12, 15, 12));
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(5);

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
        this.getChildren().add(pane);
        btSave.setOnAction(e -> saveActionDoctor());
        goBack.setOnAction(e -> showDoctorContents(record));
        btDelete.setOnAction(e -> deleteActionDoctor());
        help.setOnAction((e -> HelpMenu.helpMenu("EditDoc")));
    }

    private void fillInfoDoctor(double record) {
        int idNum = (int) Math.round(record);

        try {
            String queryString = "SELECT * FROM Doctor WHERE (ID = '" + Integer.toString(idNum) + "')";
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

    private void deleteActionDoctor() {
        try {
            String queryString4 = "DELETE FROM Doctor "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString4);

        } catch (SQLException ex) {
        }
        System.out.println("done");
        setSelf();
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
}
