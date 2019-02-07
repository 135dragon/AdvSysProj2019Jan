/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapplication;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Dennis
 */
public class MedicalApplication extends Application {
    
    BorderPane mainMenu = new BorderPane();
    HBox mainHeader = new HBox();
    HBox mainFooter = new HBox();
    VBox mainLeft = new VBox();
    VBox mainCenter = new VBox();
    VBox mainRight = new VBox();   
    Button registerPatient = new Button();
    Button registerDoctor = new Button();
    Button findPatient = new Button();
    Button findDoctor = new Button();
    Button calander = new Button();
    
    @Override
    public void start(Stage primaryStage) {
        
        mainMenu.setTop(mainHeader);
        mainMenu.setBottom(mainFooter);
        mainMenu.setLeft(mainLeft);
        mainMenu.setCenter(mainCenter);
        mainMenu.setRight(mainRight);
        mainMenu.setPadding(new Insets(20));
        
        MainMenu();
               
        Scene scene = new Scene(mainMenu, 1200, 600);        
        primaryStage.setTitle("Healthcare Oragnization Website");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void MainMenu() {
                
        registerPatient.setText("Register Patient");
        registerDoctor.setText("Regisiter Doctor");
        findPatient.setText("Find Patient");
        findDoctor.setText("Find Doctor");
        calander.setText("Show Calander");
        
        registerPatient.setOnAction((ActionEvent event) -> {
            RegisterPatient();
            System.out.println("Registering Patient");
        });        
        registerDoctor.setOnAction((ActionEvent event) -> {
            RegisterDoctor();
            System.out.println("Registering Doctor");
        });
        findPatient.setOnAction((ActionEvent event) -> {
            PatientSearch();
            System.out.println("Finding Patient");
        });
        findDoctor.setOnAction((ActionEvent event) -> {
            DoctorSearch();
            System.out.println("Finding Doctor");
        });
        calander.setOnAction((ActionEvent event) -> {
            Calander();
            System.out.println("Showing Calander");
        });
                
        registerPatient.setPadding(new Insets(30,80,30,80));
        registerDoctor.setPadding(new Insets(30,80,30,80));
        findPatient.setPadding(new Insets(30,93,30,93));
        findDoctor.setPadding(new Insets(30,93,30,93));
        calander.setPadding(new Insets(30,85,30,85));
        
        mainHeader.setAlignment(Pos.CENTER);
        mainHeader.getChildren().add(new Label("Name of the Organization"));
        
        mainLeft.setAlignment(Pos.CENTER);
        mainLeft.getChildren().add(new Label("Temporary Text"));
        
        mainCenter.setSpacing(20);
        mainCenter.setAlignment(Pos.CENTER);
        mainCenter.getChildren().addAll(registerPatient,registerDoctor);
        mainCenter.getChildren().addAll(findPatient,findDoctor, calander);
        
        mainRight.setAlignment(Pos.CENTER);
        mainRight.getChildren().add(new Label("Temporary Text"));
        
        mainFooter.setAlignment(Pos.CENTER);
        mainFooter.getChildren().add(new Label("Temporary Text"));
    }
    
    public void RegisterPatient() {
        
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        TextField fName = new TextField();
        TextField lName = new TextField();
        TextField pNum = new TextField();
        Button submit = new Button();
        Button goBack = new Button();
        
        goBack.setText("Go Back");
        submit.setText("Submit");
        
        grid.setPadding(new Insets(20,20,20,20));
        grid.setHgap(20);
        grid.setVgap(20);
        
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(new Label("Phone Number: "), 0, 2);
        grid.add(goBack, 0, 3);
        grid.add(fName, 1, 0);
        grid.add(lName, 1, 1);
        grid.add(pNum, 1, 2);
        grid.add(submit, 1, 3);
        
        Scene scene = new Scene(grid, 300, 200);
        stage.setTitle("Register Patient");
        stage.setScene(scene);
        stage.show();        
    }
    
    public void RegisterDoctor() {
        
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        TextField fName = new TextField();
        TextField lName = new TextField();
        TextField pNum = new TextField();
        TextField speciality = new TextField();
        Button submit = new Button();
        Button goBack = new Button();
        
        goBack.setText("Go Back");
        submit.setText("Submit");
        
        grid.setPadding(new Insets(20,20,20,20));
        grid.setHgap(20);
        grid.setVgap(20);
        
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(new Label("Phone Number: "), 0, 2);
        grid.add(new Label("Specialality: "), 0, 3);
        grid.add(goBack, 0, 4);
        grid.add(fName, 1, 0);
        grid.add(lName, 1, 1);
        grid.add(pNum, 1, 2);
        grid.add(speciality, 1, 3);
        grid.add(submit, 1, 4);
        
        Scene scene = new Scene(grid, 300, 250);
        stage.setTitle("Register Doctor");
        stage.setScene(scene);
        stage.show();
    }
    
    public void PatientSearch() {
        
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        TextArea textArea = new TextArea();
        ListView<String> listView = new ListView();
        Button submit = new Button();
        Button showMore = new Button();
        Button goBack = new Button();
        Button delete = new Button();
        
        goBack.setText("Go Back");
        submit.setText("Submit");
        showMore.setText("Show More");
        delete.setText("Delete");
        
        listView.getItems().addAll("Andrew", "Billy", "John");
        
        textArea.setEditable(false);
        
        submit.setOnAction((ActionEvent event) -> {
            textArea.clear();
            textArea.setText(listView.getSelectionModel().getSelectedItem());
        });
               
        showMore.setOnAction((ActionEvent event) -> {
            PatientInfo();
        });
        
        grid.setPadding(new Insets(20,20,20,20));
        grid.setHgap(20);
        grid.setVgap(20);
        
        grid.add(new Label("List of Patients"), 0, 0);
        grid.add(listView, 0, 1);
        grid.add(submit, 0, 2);
        grid.add(goBack, 0, 3);
        grid.add(new Label("Info on selected Patient"), 1, 0);
        grid.add(textArea, 1, 1);
        grid.add(showMore, 1, 2);
        grid.add(delete, 1, 3);
        
        
        Scene scene = new Scene(grid, 800, 400);
        stage.setTitle("Patient Search");
        stage.setScene(scene);
        stage.show();
    }
    
    public void DoctorSearch() {
        
        Stage stage = new Stage();
        BorderPane view = new BorderPane();
        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();
        TextArea textArea = new TextArea();
        ListView<String> listView = new ListView();
        Button submit = new Button();
        Button edit = new Button();
        Button goBack = new Button();
        Button delete = new Button();
        ComboBox speciality = new ComboBox();
        
        goBack.setText("Go Back");
        submit.setText("Submit");
        edit.setText("Show More");
        delete.setText("Delete");
        
        listView.getItems().addAll("Andrew", "Billy", "John");
        
        textArea.setEditable(false);
        
        submit.setOnAction((ActionEvent event) -> {
            textArea.clear();
            textArea.setText(listView.getSelectionModel().getSelectedItem());
        });
        
        grid.setPadding(new Insets(20,20,20,20));
        grid.setHgap(20);
        grid.setVgap(20);
        
        grid2.setPadding(new Insets(20,20,20,20));
        grid2.setHgap(20);
        grid2.setVgap(20);
        
        grid.add(new Label("List of Doctors"), 0, 0);
        grid.add(listView, 0, 1);
        grid.add(submit, 0, 2);
        grid.add(goBack, 0, 3);
        grid.add(new Label("Info on selected Doctor"), 1, 0);
        grid.add(textArea, 1, 1);
        grid.add(edit, 1, 2);
        grid.add(delete, 1, 3);
        
        grid2.add(new Label("Select Doctor By Speciality: "), 0, 0);
        grid2.add(speciality, 0, 1);
        
        view.setCenter(grid);
        view.setLeft(grid2);
        Scene scene = new Scene(view, 800, 400);
        stage.setTitle("Doctor Search");
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void PatientInfo() {
        
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        TextArea textArea1 = new TextArea();
        TextArea textArea2 = new TextArea();
        Button showCalander = new Button();
        Button goBack = new Button();
        
        textArea1.setEditable(false);
        textArea2.setEditable(false);
        
        goBack.setText("Go Back");
        showCalander.setText("Show Calander");
        
        showCalander.setOnAction((ActionEvent event) -> {
            PatientCalander();
        });
        
        grid.add(new Label("Past Visitation information"), 0, 0);
        grid.add(textArea1, 0, 1);
        grid.add(goBack, 0, 2);
        grid.add(new Label("Past Medical information"), 1, 0);
        grid.add(textArea2, 1, 1);
        grid.add(showCalander, 1, 2);
        
        grid.setPadding(new Insets(20,20,20,20));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Scene scene = new Scene(grid, 800, 300);
        stage.setTitle("Patient Info");
        stage.setScene(scene);
        stage.show();
    }
    
    public void PatientCalander() {
        
        BorderPane view = new BorderPane();
        Stage stage = new Stage();
        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();
        ListView<String> listView = new ListView();
        TextArea textArea1 = new TextArea();
        TextArea textArea2 = new TextArea();
        Button delete = new Button();
        Button goBack = new Button();
        Button add = new Button();
        TextField field = new TextField();
        ComboBox combo1 = new ComboBox();
        ComboBox combo2 = new ComboBox();
        
        textArea1.setEditable(false);
        textArea2.setEditable(false);
        field.setEditable(false);
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        
        goBack.setText("Go Back");
        delete.setText("Delete Appointment");
        add.setText("Add Appointment");
        
        combo1.setId("test");
        combo1.getSelectionModel().selectFirst();
        
        grid1.add(new Label("Calander"), 0, 0);
        grid1.add(popupContent, 0, 1);
        grid1.add(goBack, 0, 2);
        grid1.add(new Label("Appointments on selected date"), 1, 0);
        grid1.add(listView, 1, 1);
        grid1.add(delete, 1, 2);
        
        grid1.setPadding(new Insets(20,0,20,20));
        grid1.setHgap(10);
        grid1.setVgap(10);
        
        grid2.setPadding(new Insets(20,20,20,0));
        grid2.setHgap(10);
        grid2.setVgap(10);
        
        grid2.add(new Label("Add Appointments"), 1, 0);
        grid2.add(new Label("Selected Date:"), 0, 1);
        grid2.add(field, 1, 1);
        grid2.add(new Label("Avaliable Times"), 0, 2);
        grid2.add(combo1, 1, 2);
        grid2.add(new Label("Avaliable Doctors"), 0, 3);
        grid2.add(combo2, 1, 3);
        grid2.add(add, 1, 4);
        
        view.setCenter(grid1);
        view.setRight(grid2);
        
        Scene scene = new Scene(view, 800, 300);
        stage.setTitle("Patient Calander");
        stage.setScene(scene);
        stage.show();
    }
    
    public void Calander() {
        
        Stage stage = new Stage();
        GridPane grid1 = new GridPane();
        ListView<String> listView = new ListView();
        TextArea textArea1 = new TextArea();
        TextArea textArea2 = new TextArea();
        Button goBack = new Button();
        TextField field = new TextField();
        
        textArea1.setEditable(false);
        textArea2.setEditable(false);
        field.setEditable(false);
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        
        goBack.setText("Go Back");
        
        grid1.add(new Label("Calander"), 0, 0);
        grid1.add(popupContent, 0, 1);
        grid1.add(goBack, 0, 2);
        grid1.add(new Label("Appointments on selected date"), 1, 0);
        grid1.add(listView, 1, 1);
        
        grid1.setPadding(new Insets(20,0,20,20));
        grid1.setHgap(10);
        grid1.setVgap(10);
        
        Scene scene = new Scene(grid1, 500, 300);
        stage.setTitle("Calander");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }
    
}

