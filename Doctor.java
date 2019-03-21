/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;
/**
 *
 * @author Charles Hulett
 */
import Connector.DBConnector;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Doctor extends VBox
{
    final private ListView<String> doctorSelectView;
    private VBox center;
    
    private BorderPane pane = new BorderPane();
    
    private GridPane grid = new GridPane();
    private GridPane grid2 = new GridPane();
    
    private ComboBox specialtiesList;
    
    private Button filter = new Button("Filter");
    private Button unFilter = new Button("Un-Filter");
    private Button select = new Button("Select");
    private Button add = new Button("Add Doctor");
    //private Button btShowContents;
    
    private String temp = " ";
    private String speciality;
    
    private CheckBox cbMonday = new CheckBox();
    private CheckBox cbTuesday = new CheckBox();
    private CheckBox cbWednesday = new CheckBox();
    private CheckBox cbThursday = new CheckBox();
    private CheckBox cbFriday = new CheckBox();
    
    private Label lDoctor;
    private Label lMonday;
    private Label lTuesday; 
    private Label lWednesday; 
    private Label lThursday;
    private Label lFriday;
    
    private TextField tffName = new TextField();
    private TextField tflName = new TextField();
    
    private int id;
    
    private Statement stmt;
    
    
    public Doctor(ListView doctorSelectView, VBox center, ComboBox specialtiesList,
                  Label lDoctor, Label lMonday, Label lTuesday, Label lWednesday, 
                  Label lThursday, Label lFriday, CheckBox cbMonday, 
                  CheckBox cbTuesday, CheckBox cbWednesday, CheckBox cbThursday, 
                  CheckBox cbFriday, String speciality, int id, 
                  Button btShowContents, Statement stmt)
    {
        this.doctorSelectView = doctorSelectView;
        this.center = center;
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);
        this.specialtiesList = specialtiesList;
        this.lDoctor = lDoctor;
        this.lMonday = lMonday;
        this.lTuesday = lTuesday;
        this.lWednesday = lWednesday; 
        this.lThursday = lThursday;
        this.lFriday = lFriday;
        this.cbMonday = cbMonday;
        this.cbTuesday = cbTuesday;
        this.cbWednesday = cbWednesday;
        this.cbThursday = cbThursday;
        this.cbFriday = cbFriday;
        this.speciality = speciality; 
        this.id = id;
        //this.btShowContents = btShowContents;
        this.stmt = stmt;
    }
    
    public void setDocCenter()
    {
        add.setOnAction(e -> addDoctor());
        
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
        grid2.add(specialtiesList, 0, 1);
        grid2.add(filter, 0, 2);
        grid2.add(unFilter, 0, 3);
        grid2.add(select, 0, 4);
        grid2.add(add, 0, 5);

        doctorSelectView.getItems().clear();
        
        try 
        {
            String queryString = "select * from Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            // Iterate through the result and print the student names
            while (resultSet.next()) 
            {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                    if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("Speciality"))
                        temp += resultSet.getObject(i) + "     ";
                doctorSelectView.getItems().add(temp);
                temp = "";
            }

        } 
        catch (SQLException ex) {}

        //specialitiesList.getSelectionModel().getSelectedItem().toString())
        filter.setOnAction(e -> filterDocList());
        unFilter.setOnAction(new EventHandler<ActionEvent>() 
        {

            @Override
            public void handle(ActionEvent event) 
            {
                doctorSelectView.getItems().clear();
                try 
                {
                    String temp1 = "";
                    String queryString = "select * from Doctor";

                    ResultSet resultSet = stmt.executeQuery(queryString);

                    ResultSetMetaData rsMetaData = resultSet.getMetaData();

                    // Iterate through the result and print the student names
                    while (resultSet.next()) 
                    {
                        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                            if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("Speciality")) {
                                temp1 += resultSet.getObject(i) + "     ";
                            }
                        }
                        doctorSelectView.getItems().add(temp1);
                        temp1 = "";
                    }

                } 
                catch (SQLException ex) 
                {}
            }
        });
        select.setOnAction((e -> showDocContents(Double.parseDouble(doctorSelectView.getSelectionModel().getSelectedItem().substring(0, 3)))));

        pane.setTop(grid);
        pane.setLeft(grid2);
        pane.setCenter(doctorSelectView);

        
        center.getChildren().add(pane);
    }
    
    public void addDoctor() 
    {
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
        grid.add(specialtiesList, 1, 3);
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

        btaddDoctor.setOnAction(e -> insertDocData());
        goBack.setOnAction(e -> setDocCenter());
    }
    
    public void deleteDoctor() 
    {
        try 
        {
            String queryString4 = "DELETE FROM Doctor "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString4);

        } 
        catch (SQLException ex) {}
        System.out.println("done");
        setDocCenter();
    }
    
    public void fillSpecialties() 
    {
        try 
        {
            String queryString = "select * from Speciality";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) 
                specialtiesList.getItems().add(resultSet.getString("sName"));
            
        } 
        catch (SQLException ex) {}
    }
    
    private void chooseDocSpeciality() 
    {
        try 
        {
            String queryString = "select * from Speciality WHERE sName ='" + speciality + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) 
                specialtiesList.getSelectionModel().select(resultSet.getInt("ID") - 1);
            
        } 
        catch (SQLException ex) {}
    }
    
    private void fillDocInfo(double record) 
    {

        int idNum = (int) Math.round(record);

        try 
        {
            String queryString = "select * from Doctor where (ID = '" + Integer.toString(idNum) + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) 
            {
                id = resultSet.getInt("ID");
                cbMonday.setSelected(resultSet.getBoolean("Monday"));
                cbTuesday.setSelected(resultSet.getBoolean("Tuesday"));
                cbWednesday.setSelected(resultSet.getBoolean("Wednesday"));
                cbThursday.setSelected(resultSet.getBoolean("Thursday"));
                cbFriday.setSelected(resultSet.getBoolean("Friday"));
                speciality = resultSet.getString("Speciality");
                chooseDocSpeciality();
                System.out.println(id);
            }
        } 
        catch (SQLException ex) {}
        
        System.out.println("loaded");
    }
    
    private void insertDocData() 
    {
        String queryString = "insert into Doctor (fName, lName, Speciality, Monday, Tuesday, Wednesday, Thursday, Friday) VALUES ('" + tffName.getText() + "', "
                + "'" + tflName.getText() + "','" + specialtiesList.getSelectionModel().getSelectedItem().toString() + "', " + cbMonday.isSelected() + ", " + cbTuesday.isSelected() + ""
                + ", " + cbWednesday.isSelected() + ""
                + ", " + cbThursday.isSelected() + ""
                + ", " + cbFriday.isSelected() + ")";
        try 
        {
            stmt.executeUpdate(queryString);
        } 
        catch (SQLException ex) {}
        
        System.out.println("Inserted");
    }
    
    public void filterDocList() 
    {
        doctorSelectView.getItems().clear();
        try 
        {
            String queryString = "select * from Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);

            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while (resultSet.next()) 
            {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) 
                {
                    if (resultSet.getObject(4).equals(specialtiesList.getValue().toString())) 
                    {
                        if (rsMetaData.getColumnName(i).equals("ID") || rsMetaData.getColumnName(i).equals("fName") || rsMetaData.getColumnName(i).equals("lName") || rsMetaData.getColumnName(i).equals("Speciality"))
                            temp += resultSet.getObject(i) + "     ";
                        
                    }
                }
                doctorSelectView.getItems().add(temp);
                temp = "";
            }
        }
        catch (SQLException ex) {}
    }
    
    private void showDocContents(double record) 
    {
        TextArea taResult = new TextArea();
        int idNum = (int) Math.round(record);
        Button goBack = new Button("Go Back");
        Button edit = new Button("Edit");

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

        try 
        {
            String queryString = "select * from Doctor";
            ResultSet resultSet = stmt.executeQuery(queryString);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                taResult.appendText(rsMetaData.getColumnName(i) + "    ");
            
            taResult.appendText("\n");

            // Iterate through the result and print the student names
            while (resultSet.next()) 
            {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) 
                {
                    if (resultSet.getObject(1).toString().equals(Integer.toString(idNum))) 
                        taResult.appendText(resultSet.getObject(i) + "     ");
                }
            }
        } 
        catch (SQLException ex) {}

        pane.setCenter(taResult);
        pane.setBottom(grid);
        center.getChildren().add(pane);

        edit.setOnAction(e -> editDocContents(record));
        goBack.setOnAction(e -> setDocCenter());
    }
    
    public void editDocContents(double record) 
    {
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

        try 
        {
            String queryString = "select * from Doctor where (ID = '" + Integer.toString(idNum) + "')";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next()) 
            {
                fName = resultSet.getString("fName");
                lName = resultSet.getString("lName");
            }

        } 
        catch (SQLException ex) {}

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
        grid.add(specialtiesList, 0, 6);
        grid.add(btSave, 0, 10);
        grid.add(goBack, 1, 10);
        grid.add(btDelete, 2, 10);

        fillDocInfo(record);

        pane.setTop(grid);

        center.getChildren().add(pane);

        btSave.setOnAction(e -> saveDocAction());
        goBack.setOnAction(e -> showDocContents(record));
        btDelete.setOnAction(e -> deleteDoctor());
    }
    
    private void saveDocAction() 
    {
        try 
        {
            String queryString2 = "UPDATE Doctor SET "
                    + "Speciality='" + specialtiesList.getSelectionModel().getSelectedItem().toString() + "', "
                    + "Monday='" + cbMonday.isSelected() + "', "
                    + "Tuesday='" + cbTuesday.isSelected() + "', "
                    + "Wednesday='" + cbWednesday.isSelected() + "', "
                    + "Thursday='" + cbThursday.isSelected() + "', "
                    + "Friday='" + cbFriday.isSelected() + "' "
                    + "WHERE (ID='" + id + "')";
            stmt.executeUpdate(queryString2);

        } 
        catch (SQLException ex) {}
        
        System.out.println("done");
    }
}
