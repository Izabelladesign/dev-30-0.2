package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 *   Controller class for managing user interaction in the "Define Office Hour Page"
 *   Handles user action through saving office hours and closing windows
 */

public class OfficeHoursController {
    /**
     * Button for saving office hour information
     */
    @FXML
    private Button saveButton;

    /**
     *   Button for closing the open window and returning to the main
     */
    @FXML
    private Button closeButton;

    /**
     * Saves office hour information
     *  implement method to store or process the input data
     */
    @FXML
    private void saveOfficeHours() {
        //saves office hour information
    }

    /**
     *  Close the open window before returning to the main application window
     */
    @FXML
    private void closeCurrWindow() {
        //closes the current window returns to main window
    }
}
