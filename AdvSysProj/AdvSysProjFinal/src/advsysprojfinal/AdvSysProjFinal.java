/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advsysprojfinal;

import java.io.FileNotFoundException;
import java.sql.Statement;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

/**
 *
 * @author aasim
 */
public class AdvSysProjFinal extends Application {

    BorderPane root = new BorderPane();
    HBox top, bottom;
    VBox left = new VBox(), right = new VBox(), center = new VBox();

    DocVBox docCenter = new DocVBox();
    PatVBox patCenter = new PatVBox();
    CalVBox calCenter = new CalVBox();
    private final DBConnector dbconn = new DBConnector();
    private final Statement stmt = dbconn.getStmt();
    Boolean signedIn = true;

    @Override
    public void start(Stage primaryStage) {

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
        Menu calView = new Menu("View Calendar");

        menu1.getItems().addAll(docAdd, docRemove, docView);
        menu2.getItems().addAll(patAdd, patRemove, patView);
        menu3.getItems().addAll(calView);
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
        calView.setOnAction((ActionEvent event) -> {
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
        root.setCenter(patCenter);
    }

    //This function clears current center and updates it to the center displaying all doctors
    //
    public void docCenter() {
        root.setCenter(docCenter);
    }

    //
    //This function clears current center and updates it to the center displaying the calendar
    public void calCenter() {
        System.out.println("calCenter launched");
        root.setCenter(calCenter);
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
