/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advsysproj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author aasim
 */
public class GUI extends Application {

    HBox top, bottom;
    VBox left = new VBox(), right = new VBox(), center = new VBox();
    ArrayList<String> patients = new ArrayList<String>(0);  //Patients Class            DEMO
    ArrayList<String> doctors = new ArrayList<String>(0);   //Doctors Class             DEMO
    ArrayList<String> events = new ArrayList<String>(0);    //Events Class (for calendar)DEMO

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        try {
            top = createTop();
            root.setTop(top);
        } catch (FileNotFoundException e) {
            System.out.println("Err");
        }

        createLeft();
        createCenter();
        root.setBottom(bottom);
        root.setLeft(left);
        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public void createLeft() {
        left.setPadding(new Insets(15, 12, 15, 12));
        left.setSpacing(10);
        left.setStyle("-fx-background-color: #107076;");
        left.getChildren().clear();
        //Buttons (Patients, Doctors, Calendar)
        Button pat = new Button("Patients");
        Button doc = new Button("Doctors");
        Button cal = new Button("Calendar");
        left.getChildren().addAll(pat, doc, cal);
        pat.setOnAction((ActionEvent event) -> {
            pat.setText("Patients (Selected)");
            doc.setText("Doctors");
            cal.setText("Calendar");
            patCenter();

        });
        doc.setOnAction((ActionEvent event) -> {
            pat.setText("Patients");
            doc.setText("Doctors (Selected)");
            cal.setText("Calendar");
            docCenter();
        });
        cal.setOnAction((ActionEvent event) -> {
            pat.setText("Patients");
            doc.setText("Doctors");
            cal.setText("Calendar (Selected)");
            calCenter();
        });

    }

    //Default center when nothing is selected
    public void createCenter() {
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

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
    public void docCenter() {
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        int currDisplayed = 0;
        ArrayList<HBox> rows = new ArrayList<HBox>(0);
        HBox x = new HBox();
        Button add = new Button("+");
        Button remove = new Button("-");
        x.getChildren().addAll(new Text("Doctors"), add, remove);
        x.getChildren().add(new Text("\n Insert Code for Table Here"));
        center.getChildren().add(x);
    }

    //This function clears current center and updates it to the center displaying the calendar
    public void calCenter() {
        center.getChildren().clear();
        center.setPadding(new Insets(15, 12, 15, 12));
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(5);

        int currDisplayed = 0;
        ArrayList<HBox> rows = new ArrayList<HBox>(0);
        HBox x = new HBox();
        Button add = new Button("+");
        Button remove = new Button("-");
        x.getChildren().addAll(new Text("Calendar"), add, remove);
        x.getChildren().add(new Text("\n Insert Code for Calendar Here"));
        center.getChildren().add(x);
    }

    //
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
