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

        CSVFileManager timeSlotManager = new CSVFileManager("Semesters_Time_Slots");
        ArrayList<ArrayList<String>> timeSlotData = timeSlotManager.fileRead();

        ArrayList<String> timeSlotList = new ArrayList<>();
        for (ArrayList<String> row : timeSlotData) {
            if (row.size() >= 2) {
                String slot = row.get(0) + " – " + row.get(1); // e.g. 4:30 – 4:45
                timeSlotList.add(slot);
            }
        }
        timeSlotComboBox.setItems(FXCollections.observableArrayList(timeSlotList));


        CSVFileManager courseManager = new CSVFileManager("courses");
        ArrayList<ArrayList<String>> courseData = courseManager.fileRead();

        ArrayList<String> courseList = new ArrayList<>();
        for (ArrayList<String> row : courseData) {
            if (row.size() >= 3) {
                String course = row.get(0) + "-" + row.get(2);
                courseList.add(course);
            }
        }
        courseComboBox.setItems(FXCollections.observableArrayList(courseList));
    }

    @FXML
    private void handleSaveSchedule() {
        String name = studentNameField.getText().trim();
        LocalDate date = scheduleDatePicker.getValue();
        String timeSlot = timeSlotComboBox.getValue();
        String course = courseComboBox.getValue();
        String reason = reasonField.getText().trim();
        String comment = commentField.getText().trim();

        if (name.isEmpty()) {
            return;
        }
        if (date == null) {
            return;
        }
        if (timeSlot == null) {
            return;
        }
        if (course == null) {
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