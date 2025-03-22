package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;


/**
 *   Controller class for managing user interaction in the "Define Office Hour Page"
 *   Handles user action through saving office hours and closing windows
 */

public class OfficeHoursController {
    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private TextField yearTextField;
    @FXML
    private CheckBox mondayCheck, tuesdayCheck, wednesdayCheck, thursdayCheck, fridayCheck;


    /**
     * Button for saving office hour information
     */
    @FXML
    private Button saveButton;

    /**
     * Button for closing the open window and returning to the main
     */
    @FXML
    private Button closeButton;

    /**
     * Saves office hour information
     * implement method to store or process the input data
     */
    @FXML
    private void saveOfficeHours() {

        String semester = semesterComboBox.getValue();
        int year = Integer.parseInt(yearTextField.getText());
        String days = getSelectedDays();


        ArrayList<String> row = new ArrayList<>();
        row.add(semester);
        row.add(String.valueOf(year));
        row.add(days);
        CSVFileManager fileManager = new CSVFileManager("office_hours");
        fileManager.fileWrite(row);

        System.out.println("Saved successfully");

    }
    private String getSelectedDays() {
        ArrayList<String> days = new ArrayList<>();
        if (mondayCheck.isSelected()) days.add("Mon");
        if (tuesdayCheck.isSelected()) days.add("Tue");
        if (wednesdayCheck.isSelected()) days.add("Wed");
        if (thursdayCheck.isSelected()) days.add("Thu");
        if (fridayCheck.isSelected()) days.add("Fri");
        return String.join(", ", days);

}

    /**
     *  Close the open window before returning to the main application window
     */
    @FXML
    private void closeCurrWindow() {
        //closes the current window returns to main window
    }
}
