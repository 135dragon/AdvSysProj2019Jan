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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author spyunes
 */
public class AppointmentScheduler {

    private final Label ltime7 = new Label("7:00");
    private final Label ltime8 = new Label("8:00");
    private final Label ltime9 = new Label("9:00");
    private final Label ltime10 = new Label("10:00");
    private final Label ltime11 = new Label("11:00");
    private final Label ltime12 = new Label("12:00");
    private final Label ltime13 = new Label("13:00");
    private final Label ltime14 = new Label("14:00");
    private final Label ltime15 = new Label("15:00");
    private final Label ltime16 = new Label("16:00");
    private final Label ltime17 = new Label("17:00");

//    private final Label ltime730 = new Label("7:30");
//    private final Label ltime830 = new Label("8:30");
//    private final Label ltime930 = new Label("9:30");
//    private final Label ltime1030 = new Label("10:30");
//    private final Label ltime1130 = new Label("11:30");
//    private final Label ltime1230 = new Label("12:30");
//    private final Label ltime1330 = new Label("13:30");
//    private final Label ltime1430 = new Label("14:30");
//    private final Label ltime1530 = new Label("15:30");
//    private final Label ltime1630 = new Label("16:30");
    private final CheckBox cbtime7 = new CheckBox();
    private final CheckBox cbtime8 = new CheckBox();
    private final CheckBox cbtime9 = new CheckBox();
    private final CheckBox cbtime10 = new CheckBox();
    private final CheckBox cbtime11 = new CheckBox();
    private final CheckBox cbtime12 = new CheckBox();
    private final CheckBox cbtime13 = new CheckBox();
    private final CheckBox cbtime14 = new CheckBox();
    private final CheckBox cbtime15 = new CheckBox();
    private final CheckBox cbtime16 = new CheckBox();
    private final CheckBox cbtime17 = new CheckBox();

//    private final CheckBox cbtime730 = new CheckBox();
//    private final CheckBox cbtime830 = new CheckBox();
//    private final CheckBox cbtime930 = new CheckBox();
//    private final CheckBox cbtime1030 = new CheckBox();
//    private final CheckBox cbtime1130 = new CheckBox();
//    private final CheckBox cbtime1230 = new CheckBox();
//    private final CheckBox cbtime1330 = new CheckBox();
//    private final CheckBox cbtime1430 = new CheckBox();
//    private final CheckBox cbtime1530 = new CheckBox();
//    private final CheckBox cbtime1630 = new CheckBox();
    private Boolean btime7 = false;
    private Boolean btime8 = false;
    private Boolean btime9 = false;
    private Boolean btime10 = false;
    private Boolean btime11 = false;
    private Boolean btime12 = false;
    private Boolean btime13 = false;
    private Boolean btime14 = false;
    private Boolean btime15 = false;
    private Boolean btime16 = false;
    private Boolean btime17 = false;

//    private Boolean btime730 = false;
//    private Boolean btime830 = false;
//    private Boolean btime930 = false;
//    private Boolean btime1030 = false;
//    private Boolean btime1130 = false;
//    private Boolean btime1230 = false;
//    private Boolean btime1330 = false;
//    private Boolean btime1430 = false;
//    private Boolean btime1530 = false;
//    private Boolean btime1630 = false;
    private final Button btSave = new Button("Save");

    private final LocalDate appDate;
    private final int idDoct;
    private final int idPat;
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();

    public AppointmentScheduler(int p, int d, LocalDate date) {
        appDate = date;
        idDoct = d;
        idPat = p;
    }

    public Boolean check7Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime7 == false) {
                btime7 = Boolean.valueOf(resultSet.getString("time7"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime7;
    }

    public Boolean check8Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime8 == false) {
                btime8 = Boolean.valueOf(resultSet.getString("time8"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime8;
    }

    public Boolean check9Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime9 == false) {
                btime9 = Boolean.valueOf(resultSet.getString("time9"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime9;
    }

    public Boolean check10Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime10 == false) {
                btime10 = Boolean.valueOf(resultSet.getString("time10"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime10;
    }

    public Boolean check11Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime7 == false) {
                btime11 = Boolean.valueOf(resultSet.getString("time11"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime11;
    }

    public Boolean check12Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime12 == false) {
                btime12 = Boolean.valueOf(resultSet.getString("time12"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime12;
    }

    public Boolean check13Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime13 == false) {
                btime13 = Boolean.valueOf(resultSet.getString("time13"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime13;
    }

    public Boolean check14Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime14 == false) {
                btime14 = Boolean.valueOf(resultSet.getString("time14"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime14;
    }

    public Boolean check15Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime15 == false) {
                btime15 = Boolean.valueOf(resultSet.getString("time15"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime15;
    }

    public Boolean check16Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime16 == false) {
                btime16 = Boolean.valueOf(resultSet.getString("time16"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime16;
    }

    public Boolean check17Hours() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String queryString = "select * from Appointments WHERE appDate =#" + formatter.format(appDate) + "# AND IDDoctor = '" + idDoct + "'";
            ResultSet resultSet = stmt.executeQuery(queryString);
            while (resultSet.next() && btime17 == false) {
                btime17 = Boolean.valueOf(resultSet.getString("time17"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return btime17;
    }

    public void notinUSE() {
//    public Boolean check730Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime730==false)
//            {
//                btime730 = Boolean.valueOf(resultSet.getString("7:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        
//        return btime730;
//    }
//    
//    public Boolean check830Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime830==false)
//            {
//                btime830 = Boolean.valueOf(resultSet.getString("8:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        
//        return btime830;
//    }
//    
//    public Boolean check930Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime930==false)
//            {
//                btime930 = Boolean.valueOf(resultSet.getString("9:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        
//        return btime930;
//    }
//    
//    public Boolean check1030Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime1030==false)
//            {
//                btime1030 = Boolean.valueOf(resultSet.getString("10:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        
//        return btime1030;
//    }
//    
//    public Boolean check1130Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime1130==false)
//            {
//                btime1130 = Boolean.valueOf(resultSet.getString("11:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        
//        return btime1130;
//    }
//    
//    public Boolean check1230Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime1230==false)
//            {
//                btime1230 = Boolean.valueOf(resultSet.getString("12:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return btime1230;
//    }
//    
//    public Boolean check1330Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime1330==false)
//            {
//                btime1330 = Boolean.valueOf(resultSet.getString("13:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return btime1330;
//    }
//    
//    public Boolean check1430Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime1430==false)
//            {
//                btime1430 = Boolean.valueOf(resultSet.getString("14:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return btime1430;
//    }
//    
//    public Boolean check1530Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime1530==false)
//            {
//                btime1530 = Boolean.valueOf(resultSet.getString("15:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return btime1530;
//    }
//    
//    public Boolean check1630Hours()
//    {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
//            System.out.println(formatter.format(appDate));
//            String queryString = "select * from Appointments WHERE appDate =#"+formatter.format(appDate)+"#";
//            ResultSet resultSet = stmt.executeQuery(queryString);
//            while (resultSet.next()&&btime1630==false)
//            {
//                btime1630 = Boolean.valueOf(resultSet.getString("16:30"));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return btime1630;
//    }
    }

    public void initializeComboBox() {
        if (check7Hours()) {
            cbtime7.setSelected(true);
            cbtime7.setDisable(true);
        }
        if (check8Hours()) {
            cbtime8.setSelected(true);
            cbtime8.setDisable(true);
        }

        if (check9Hours()) {
            cbtime9.setSelected(true);
            cbtime9.setDisable(true);
        }
        if (check10Hours()) {
            cbtime10.setSelected(true);
            cbtime10.setDisable(true);
        }
        if (check11Hours()) {
            cbtime11.setSelected(true);
            cbtime11.setDisable(true);
        }
        if (check12Hours()) {
            cbtime12.setSelected(true);
            cbtime12.setDisable(true);
        }
        if (check13Hours()) {
            cbtime13.setSelected(true);
            cbtime13.setDisable(true);
        }
        if (check14Hours()) {
            cbtime14.setSelected(true);
            cbtime14.setDisable(true);
        }
        if (check15Hours()) {
            cbtime15.setSelected(true);
            cbtime15.setDisable(true);
        }
        if (check16Hours()) {
            cbtime16.setSelected(true);
            cbtime16.setDisable(true);
        }
        if (check17Hours()) {
            cbtime17.setSelected(true);
            cbtime17.setDisable(true);
        }

//        if (check730Hours())
//        {
//            cbtime730.setSelected(true);
//            cbtime730.setDisable(true);
//        }
//        if (check830Hours())
//        {
//            cbtime830.setSelected(true);
//            cbtime830.setDisable(true);
//        }
//        if (check930Hours())
//        {
//            cbtime930.setSelected(true);
//            cbtime930.setDisable(true);
//        }
//        if (check1030Hours())
//        {
//        cbtime1030.setSelected(true);
//        cbtime1030.setDisable(true);
//        }
//        if (check1130Hours())
//        {
//        cbtime1130.setSelected(true);
//        cbtime1130.setDisable(true);
//        }
//        if (check1230Hours())
//        {
//        cbtime1230.setSelected(true);
//        cbtime1230.setDisable(true);
//        }
//        if (check1330Hours())
//        {
//        cbtime1330.setSelected(true);
//        cbtime1330.setDisable(true);
//        }
//        if (check1430Hours())
//        {
//        cbtime1430.setSelected(true);
//        cbtime1430.setDisable(true);
//        }
//        if (check1530Hours())
//        {
//        cbtime1530.setSelected(true);
//        cbtime1530.setDisable(true);
//        }
//        if (check1630Hours())
//        {
//        cbtime1630.setSelected(true);
//        cbtime1630.setDisable(true);
//        }
    }

    public void display() {
        //TableView

        //initializeDB();
        initializeComboBox();

        Stage window = new Stage();
        GridPane pan = new GridPane();
        pan.add(ltime7, 0, 2);
        pan.add(cbtime7, 1, 2);
        pan.add(ltime8, 0, 3);
        pan.add(cbtime8, 1, 3);
        pan.add(ltime9, 0, 4);
        pan.add(cbtime9, 1, 4);
        pan.add(ltime10, 0, 5);
        pan.add(cbtime10, 1, 5);
        pan.add(ltime11, 0, 6);
        pan.add(cbtime11, 1, 6);
        pan.add(ltime12, 0, 7);
        pan.add(cbtime12, 1, 7);
        pan.add(ltime13, 0, 8);
        pan.add(cbtime13, 1, 8);
        pan.add(ltime14, 0, 9);
        pan.add(cbtime14, 1, 9);
        pan.add(ltime15, 0, 10);
        pan.add(cbtime15, 1, 10);
        pan.add(ltime16, 0, 11);
        pan.add(cbtime16, 1, 11);
        pan.add(ltime17, 0, 12);
        pan.add(cbtime17, 1, 12);
        pan.add(btSave, 1, 13);

        btSave.setOnAction(e -> btSave());

        Scene scene = new Scene(pan);
        window.setScene(scene);
        window.setTitle("Add Appointment");
        window.showAndWait();
    }

    public void btSave() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
        String queryString = "insert into Appointments (IDPatient, IDDoctor, appDate, time7, time8, time9, time10, time11, time12, time13, time14, time15, time16, time17) VALUES ('" + idPat + "', "
                + "'" + idDoct
                + "',#" + formatter.format(appDate)
                + "#, " + cbtime7.isSelected()
                + ", " + cbtime8.isSelected() + ""
                + ", " + cbtime9.isSelected() + ""
                + ", " + cbtime10.isSelected() + ""
                + ", " + cbtime11.isSelected() + ""
                + ", " + cbtime12.isSelected() + ""
                + ", " + cbtime13.isSelected() + ""
                + ", " + cbtime14.isSelected() + ""
                + ", " + cbtime15.isSelected() + ""
                + ", " + cbtime16.isSelected() + ""
                + ", " + cbtime17.isSelected() + ")";
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException ex) {

        }
        System.out.println("Inserted");
    }

}
