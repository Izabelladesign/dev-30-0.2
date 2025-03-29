package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SemesterTimeSlotsController {

    @FXML private ComboBox<String> fromComboBox;
    @FXML private ComboBox<String> toComboBox;

    @FXML
    public void initialize() {
        System.out.println("SemesterTimeSlotsController initialized correctly!");
    }

    @FXML
    private void addSlot() {
        System.out.println("Add slot clicked");
    }

    @FXML
    private void saveAllSlots() {
        System.out.println("Save all slots clicked");
    }

    @FXML
    private void showTimeSlots() {
        System.out.println("Show slots clicked");
    }
}
