package advsysprojfinal;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Dennis
 */
public class HelpMenu {

    public static void helpMenu(String h) {
        Stage secondaryStage = new Stage();
        BorderPane helpM = new BorderPane();
        secondaryStage.setTitle("Help Menu");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        helpM.setCenter(grid);

        helpM.setPadding(new Insets(20, 20, 20, 20));

        helpM.setTop(new Label("Help Menu"));

        if (h.equals("MainDoc")) {
            grid.add(new Label("Select Specialty: "), 0, 0);
            grid.add(new Label("The select speciality gives you a list of all the doctor specializations we have so\nyou can sort through them."), 1, 0);
            grid.add(new Label("Filter Button: "), 0, 1);
            grid.add(new Label("The filter button organizes the list of Doctors by the speciality you selected."), 1, 1);
            grid.add(new Label("Un-Filter Button: "), 0, 2);
            grid.add(new Label("The Un-filter button resets the list back to its original state."), 1, 2);
            grid.add(new Label("Select Button: "), 0, 3);
            grid.add(new Label("The select button chooses the name you have clicked on in the list and shows the details\nof said person"), 1, 3);
            grid.add(new Label("Add Doctor: "), 0, 4);
            grid.add(new Label("The add doctor button allows you to create new doctors to the database."), 1, 4);
        } else if (h.equals("ContentDoc")) {
            grid.add(new Label("Go Back: "), 0, 0);
            grid.add(new Label("This allows you to go back to the previous page."), 1, 0);
            grid.add(new Label("Edit: "), 0, 1);
            grid.add(new Label("This allows you to edit the currently select profile."), 1, 1);
        } else if (h.equals("EditDoc")) {
            grid.add(new Label("Save: "), 0, 0);
            grid.add(new Label("This saves the changes you made for the selected Doctor."), 1, 0);
            grid.add(new Label("Go Back: "), 0, 1);
            grid.add(new Label("This allows you to go back to the previous page."), 1, 1);
            grid.add(new Label("Delete: "), 0, 2);
            grid.add(new Label("This deletes the currently selected Doctor."), 1, 2);
        } else if (h.equals("AddDoc")) {
            grid.add(new Label("Add: "), 0, 0);
            grid.add(new Label("This allows you to add a Doctor to the database."), 1, 0);
            grid.add(new Label("Go Back: "), 0, 1);
            grid.add(new Label("This allows you to go back to the previous page."), 1, 1);
        } else if (h.equals("MainPat")) {
            grid.add(new Label("Type Last Name: "), 0, 0);
            grid.add(new Label("This allows you to sort through the patients via last name."), 1, 0);
            grid.add(new Label("Filter Button: "), 0, 1);
            grid.add(new Label("The filter button organizes the list of Patients with the last name you typed."), 1, 1);
            grid.add(new Label("Un-Filter Button: "), 0, 2);
            grid.add(new Label("The Un-filter button resets the list back to its original state."), 1, 2);
            grid.add(new Label("Select Button: "), 0, 3);
            grid.add(new Label("The select button chooses the name you have clicked on in the list and shows the details\nof said person"), 1, 3);
            grid.add(new Label("Add Patient: "), 0, 4);
            grid.add(new Label("The add patient button allows you to create new patients to the database."), 1, 4);
        } else if (h.equals("ContentPat")) {
            grid.add(new Label("Go Back: "), 0, 0);
            grid.add(new Label("This allows you to go back to the previous page."), 1, 0);
            grid.add(new Label("Edit: "), 0, 1);
            grid.add(new Label("This allwos you to edit the currently select profile."), 1, 1);
            grid.add(new Label("Appointments: "), 0, 1);
            grid.add(new Label("This This allows you to add appointments to the selected Patient."), 1, 1);
        } else if (h.equals("EditPat")) {
            grid.add(new Label("Save: "), 0, 0);
            grid.add(new Label("This saves the changes you made for the selected Patient."), 1, 0);
            grid.add(new Label("Go Back: "), 0, 1);
            grid.add(new Label("This allows you to go back to the previous page."), 1, 1);
            grid.add(new Label("Delete: "), 0, 2);
            grid.add(new Label("This deletes the currently selected Patient."), 1, 2);
        } else if (h.equals("AddPat")) {
            grid.add(new Label("Add: "), 0, 0);
            grid.add(new Label("This allows you to add a Patient to the database."), 1, 0);
            grid.add(new Label("Go Back: "), 0, 1);
            grid.add(new Label("This allows you to go back to the previous page."), 1, 1);
        }

        Scene scene = new Scene(helpM, 750, 300);
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

}
