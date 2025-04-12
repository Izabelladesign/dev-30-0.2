package s25.cs151.application;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Control class for the Office Hours Schedule
 * Handles interaction between user input and office hours schedule
 */

public class OfficeHoursScheduleController {

    @FXML private TextField studentNameField;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private ComboBox<String> timeSlotComboBox;
    @FXML private ComboBox<String> courseComboBox;
    @FXML private TextField reasonField;
    @FXML private TextField commentField;
    @FXML private TableView<ObservableList<String>> scheduleTable;
    @FXML private TableColumn<ObservableList<String>, String> studentColumn;
    @FXML private TableColumn<ObservableList<String>, String> dateColumn;
    @FXML private TableColumn<ObservableList<String>, String> timeSlotColumn;
    @FXML private TableColumn<ObservableList<String>, String> courseColumn;
    @FXML private TableColumn<ObservableList<String>, String> reasonColumn;
    @FXML private TableColumn<ObservableList<String>, String> commentColumn;

    private final CSVFileManager fileManager = new CSVFileManager("OfficeHoursSchedule");

    /**
     * Initializes the Office Hours Schedule form
     * Runs when the office Hours Schedule fxml is loaded
     * Sets the date and fills in the time slot and chooses a course drop down using the CSV File
     */

    @FXML
    public void initialize() {
        studentColumn.setCellValueFactory(getColumnValueFactory(0));
        dateColumn.setCellValueFactory(getColumnValueFactory(1));
        timeSlotColumn.setCellValueFactory(getColumnValueFactory(2));
        courseColumn.setCellValueFactory(getColumnValueFactory(3));
        reasonColumn.setCellValueFactory(getColumnValueFactory(4));
        commentColumn.setCellValueFactory(getColumnValueFactory(5));

        dateColumn.setSortType(TableColumn.SortType.ASCENDING);
        scheduleTable.getSortOrder().add(dateColumn);
        scheduleDatePicker.setValue(LocalDate.now());

        //populate time slots
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

        //populate course options
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

        ArrayList<ArrayList<String>> rows = fileManager.fileRead();
        rows.sort((r1, r2) -> {
            LocalDate d1 = LocalDate.parse(r1.get(1));
            LocalDate d2 = LocalDate.parse(r2.get(1));
            return d1.compareTo(d2); // ascending
        }
        );

        for (int i = 0; i < rows.size(); i++) {
            ObservableList<String> rowData = FXCollections.observableArrayList(rows.get(i));
            scheduleTable.getItems().add(rowData);
        }
    }

    /**
     * Runs when the user clicks the save button
     * Gathers input from the form
     * Checks to make sure all the required fields are filled
     * saves the data to the CSV file and resets for a new entry
     */

    @FXML
    private void handleSaveSchedule() {
        String name = studentNameField.getText().trim();
        LocalDate date = scheduleDatePicker.getValue();
        String timeSlot = timeSlotComboBox.getValue();
        String course = courseComboBox.getValue();
        String reason = reasonField.getText().trim();
        String comment = commentField.getText().trim();

        //checks to see if all the required fields are filled before saving
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

        //Creates a new row with all the input data
        ArrayList<String> row = new ArrayList<>();
        row.add(name);
        row.add(date.toString());
        row.add(timeSlot);
        row.add(course);
        row.add(reason);
        row.add(comment);

        //Saves the data
        fileManager.fileWrite(row);

        ObservableList<String> rowData = FXCollections.observableArrayList(row);
        scheduleTable.getItems().add(rowData);

        scheduleTable.sort();
        //Clear form fields
        studentNameField.clear();
        scheduleDatePicker.setValue(LocalDate.now());
        timeSlotComboBox.getSelectionModel().clearSelection();
        courseComboBox.getSelectionModel().clearSelection();
        reasonField.clear();
        commentField.clear();
    }
    private Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>> getColumnValueFactory(int index) {
        return new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> cellData) {
                String value = (cellData.getValue().size() > index) ? cellData.getValue().get(index) : "";
                return new SimpleStringProperty(value);
            }
        };
    }
}
