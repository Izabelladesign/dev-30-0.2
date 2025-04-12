package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.util.ArrayList;

public class OfficeHoursScheduleController {

    @FXML private TextField studentNameField;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private ComboBox<String> timeSlotComboBox;
    @FXML private ComboBox<String> courseComboBox;
    @FXML private TextField reasonField;
    @FXML private TextField commentField;

    private final CSVFileManager fileManager = new CSVFileManager("OfficeHoursSchedule");

    @FXML
    public void initialize() {
        scheduleDatePicker.setValue(LocalDate.now());

        // Load time slots from file
        CSVFileManager timeSlotManager = new CSVFileManager("Semesters_Time_Slots");
        ArrayList<ArrayList<String>> timeSlots = timeSlotManager.fileRead();
        ArrayList<String> formattedSlots = new ArrayList<>();
        for (ArrayList<String> row : timeSlots) {
            if (row.size() >= 2) {
                formattedSlots.add(row.get(0) + " – " + row.get(1));
            }
        }
        timeSlotComboBox.setItems(FXCollections.observableArrayList(formattedSlots));

        // Load course codes + section from file
        CSVFileManager courseManager = new CSVFileManager("courses");
        ArrayList<ArrayList<String>> courses = courseManager.fileRead();
        ArrayList<String> formattedCourses = new ArrayList<>();
        for (ArrayList<String> row : courses) {
            if (row.size() >= 2) {
                formattedCourses.add(row.get(0) + "-" + row.get(2));  // CS151-04
            }
        }
        courseComboBox.setItems(FXCollections.observableArrayList(formattedCourses));
    }

    @FXML
    private void handleSaveSchedule() {
        String name = studentNameField.getText().trim();
        LocalDate date = scheduleDatePicker.getValue();
        String timeSlot = timeSlotComboBox.getValue();
        String course = courseComboBox.getValue();
        String reason = reasonField.getText().trim();
        String comment = commentField.getText().trim();

        if (name.isEmpty() || date == null || timeSlot == null || course == null) {
            return;
        }

        ArrayList<String> row = new ArrayList<>();
        row.add(name);
        row.add(date.toString());
        row.add(timeSlot);
        row.add(course);
        row.add(reason);
        row.add(comment);

        fileManager.fileWrite(row);


        studentNameField.clear();
        scheduleDatePicker.setValue(LocalDate.now());
        timeSlotComboBox.getSelectionModel().clearSelection();
        courseComboBox.getSelectionModel().clearSelection();
        reasonField.clear();
        commentField.clear();
    }
}