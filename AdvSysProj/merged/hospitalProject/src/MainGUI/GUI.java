/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainGUI;

import Appointment.AppointmentCreator;
import Appointment.AppointmentResult;
import Connector.DBConnector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

/**
 *
 * @author aasim
 */
public class GUI extends Application {

    ComboBox specialitiesList = new ComboBox();
    TextField tffName = new TextField();
    TextField tflName = new TextField();
    CheckBox cbMonday = new CheckBox();
    CheckBox cbTuesday = new CheckBox();
    CheckBox cbWednesday = new CheckBox();
    CheckBox cbThursday = new CheckBox();
    CheckBox cbFriday = new CheckBox();
    final private ListView<String> doctorSelectView = new ListView();

    private final Label lDoctor = new Label("Doctor Name");
    private final ComboBox patientList = new ComboBox();
    private final Label lMonday = new Label("Monday");
    private final Label lTuesday = new Label("Tuesday");
    private final Label lWednesday = new Label("Wednesday");
    private final Label lThursday = new Label("Thursday");
    private final Label lFriday = new Label("Friday");

    private String speciality;
    private int id;

    private final Button btShowContents = new Button("Show Contents");
    HBox top, bottom;
    VBox left = new VBox(), right = new VBox(), center = new VBox();
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    Boolean signedIn = true;

    @Override
    public void start(Stage primaryStage) {
        fillSpecialities();
        BorderPane root = new BorderPane();
        //Menu
        Menu menu1 = new Menu("Doctors");
        Menu docAdd = new Menu("Add Doctor");
        Menu docRemove = new Menu("Remove Doctor");
        Menu docView = new Menu("View Doctors");
        Menu menu2 = new Menu("Patients");
        Menu patAdd = new Menu("Add Patient");
        Menu patRemove = new Menu("Remove Patient");
        Menu patView = new Menu("View Patients");
        Menu menu3 = new Menu("Calendar");

        menu1.getItems().addAll(docAdd, docRemove, docView);
        menu2.getItems().addAll(patAdd, patRemove, patView);

        MenuBar menuBar = new MenuBar(menu1, menu2, menu3);
        VBox main = new VBox(menuBar, root);

        try {
            top = createTop();
            root.setTop(top);
        } catch (FileNotFoundException e) {
            System.out.println("Err");
        }
        createCenter();
        root.setBottom(bottom);
        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(main, 1000, 1000);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        patView.setOnAction((ActionEvent event) -> {
            if (signedIn) {
                patCenter();
            } else {
                alert("Please Sign In");
            }

        });
        docView.setOnAction((ActionEvent event) -> {
            if (signedIn) {
                docCenter();
            } else {
                alert("Please Sign In");
            }

        });
        menu3.setOnAction((ActionEvent event) -> {
            if (signedIn) {
                calCenter();
            } else {
                alert("Please Sign In");
            }

        });

    }

    public HBox createTop() throws FileNotFoundException {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #107076;");
        // Image

        // Text
        Text t = new Text("Name");
        t.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        t.setFill(Color.WHITE);
        //

        hbox.getChildren().addAll(t);

        return hbox;
    }   //Done. All I have to do is change the image

    //Sign In
    public void createCenter() {
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);
        HBox user = new HBox();
        Label lUsername = new Label("Username: ");
        TextField tUsername = new TextField();
        user.getChildren().addAll(lUsername, tUsername);
        user.setSpacing(5);
        HBox pass = new HBox();
        Label lPass = new Label("Password: ");
        TextField tPass = new TextField();
        pass.getChildren().addAll(lPass, tPass);
        pass.setSpacing(5);
        Button submit = new Button("Submit");
        VBox form = new VBox(user, pass, submit);
        form.setSpacing(5);
        form.setAlignment(Pos.CENTER_LEFT);

        center.getChildren().add(form);

    }

    //This function clears current center and updates it to the center displaying all patients
    public void patCenter() {
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        int currDisplayed = 0;
        ArrayList<HBox> rows = new ArrayList<HBox>(0);
        HBox x = new HBox();
        Button add = new Button("+");
        Button remove = new Button("-");
        x.getChildren().addAll(new Text("Patients"), add, remove);
        x.getChildren().add(new Text("\n Insert Code for Table Here"));
        center.getChildren().add(x);

    }

    //This function clears current center and updates it to the center displaying all doctors
    //
    public void docCenter() {
        BorderPane pane = new BorderPane();
        String temp = "";
        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();
        Button filter = new Button("Filter");
        Button unFilter = new Button("Un-Filter");
        Button select = new Button("Select");
        Button add = new Button("Add Doctor");

        add.setOnAction(e -> DoctorAdd());

        grid2.setPadding(new Insets(0, 15, 15, 15));
        grid2.setHgap(10);
        grid2.setVgap(10);

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

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

        doctorSelectView.getItems().clear();

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
                doctorSelectView.getItems().add(temp);
                temp = "";
            }

        } catch (SQLException ex) {
        }

        //specialitiesList.getSelectionModel().getSelectedItem().toString())
        filter.setOnAction(e -> filterList());
        unFilter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                doctorSelectView.getItems().clear();
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
                        doctorSelectView.getItems().add(temp1);
                        temp1 = "";
                    }

                } catch (SQLException ex) {
                }
            }
        });
        select.setOnAction((e -> showDoctorContents(Double.parseDouble(doctorSelectView.getSelectionModel().getSelectedItem().substring(0, 3)))));

        pane.setTop(grid);
        pane.setLeft(grid2);
        pane.setCenter(doctorSelectView);

        center.getChildren().add(pane);
    }

    public void filterList() {
        doctorSelectView.getItems().clear();
        try {
            String temp = "";
            String queryString = "select * from Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (resultSet.getObject(4).equals(specialitiesList.getValue().toString())) {
                        if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("Speciality")) {
                            temp += resultSet.getObject(i) + "     ";
                        }
                    }
                }
                doctorSelectView.getItems().add(temp);
                temp = "";
            }
        } catch (SQLException ex) {
        }

    }

    private void showDoctorContents(double record) {
        BorderPane pane = new BorderPane();
        TextArea taResult = new TextArea();
        int idNum = (int) Math.round(record);
        Button goBack = new Button("Go Back");
        Button edit = new Button("Edit");
        GridPane grid = new GridPane();

        taResult.setEditable(false);

        grid.add(goBack, 0, 0);
        grid.add(edit, 1, 0);

        grid.setPadding(new Insets(15, 15, 15, 15));
        grid.setHgap(10);
        grid.setVgap(10);

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

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
                    if (resultSet.getObject(1).toString().equals(Integer.toString(idNum))) {
                        taResult.appendText(resultSet.getObject(i) + "     ");
                    }
                }
            }
        } catch (SQLException ex) {
        }

        pane.setCenter(taResult);
        pane.setBottom(grid);
        center.getChildren().add(pane);

        edit.setOnAction(e -> EditDoctorContenets(record));
        goBack.setOnAction(e -> docCenter());
    }

    public void EditDoctorContenets(double record) {
        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Button btSave = new Button("Save");
        Button btDelete = new Button("Delete");
        Button goBack = new Button("Go Back");
        int idNum = (int) Math.round(record);
        String fName = "";
        String lName = "";

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

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

        fillInfo(record);

        pane.setTop(grid);

        center.getChildren().add(pane);

        btSave.setOnAction(e -> saveAction());
        goBack.setOnAction(e -> showDoctorContents(record));
        btDelete.setOnAction(e -> deleteAction());

    }

    public void DoctorAdd() {

        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();
        Label lfName = new Label("First Name");
        Label llName = new Label("Last Name");
        Label lSpeciality = new Label("Speciality");
        Button btaddDoctor = new Button("Add");
        Button goBack = new Button("Go Back");

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

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

        center.getChildren().add(pane);

        btaddDoctor.setOnAction(e -> insertData());
        goBack.setOnAction(e -> docCenter());
    }

    private void fillSpecialities() {
        try {
            String queryString = "select * from Speciality";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                specialitiesList.getItems().add(resultSet.getString("sName"));
            }
        } catch (SQLException ex) {
        }
    }

    private void insertData() {
        String queryString = "insert into Doctor (fName, lName, Speciality, Monday, Tuesday, Wednesday, Thursday, Friday) VALUES ('" + tffName.getText() + "', "
                + "'" + tflName.getText() + "','" + specialitiesList.getSelectionModel().getSelectedItem().toString() + "', " + cbMonday.isSelected() + ", " + cbTuesday.isSelected() + ""
                + ", " + cbWednesday.isSelected() + ""
                + ", " + cbThursday.isSelected() + ""
                + ", " + cbFriday.isSelected() + ")";
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException ex) {

        }
        System.out.println("Inserted");
    }

    private void fillInfo(double record) {

        int idNum = (int) Math.round(record);

        try {
            String queryString = "select * from Doctor where (ID = '" + Integer.toString(idNum) + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
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

    private void saveAction() {
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

    private void deleteAction() {
        try {
            String queryString4 = "DELETE FROM Doctor "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString4);

        } catch (SQLException ex) {
        }
        System.out.println("done");
        docCenter();
    }

    //
    //This function clears current center and updates it to the center displaying the calendar
    public void calCenter() {

        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        int currDisplayed = 0;
        ArrayList<HBox> rows = new ArrayList<HBox>(0);
        HBox mainHBox = new HBox();
        AppointmentResult y = new AppointmentResult(1);
        y.initializeComboBox();

        Button addEvent = new Button("+");
        Button removeEvent = new Button("-");

        mainHBox.getChildren().addAll(y.oldApp, addEvent, removeEvent);
        center.getChildren().addAll(mainHBox);

        //
        addEvent.setOnAction((ActionEvent e) -> {
            AppointmentCreator x = new AppointmentCreator();
            x.display();
            y.initializeComboBox();
        });

        //
    }

    public void alert(String x) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wait a second...");
        alert.setHeaderText(x);
        alert.showAndWait();
    }

    //
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
