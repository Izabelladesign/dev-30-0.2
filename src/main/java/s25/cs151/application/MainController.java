package s25.cs151.application;

import javafx.fxml.FXML;
import java.io.IOException;

/**
 *  Controller class for managing user interaction in the application
 *  Managing navigation between the different scenes
 *
 */

public class MainController {
    /**
     *  Open the "Define Office Hours"
     *  Method triggered by user action, e.g button clicking
     *
     */


    @FXML
    private void goToDefineOfficeHours() throws IOException {
        Main.defineOfficeHoursPage();
    }

}