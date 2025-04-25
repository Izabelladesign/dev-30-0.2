package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import java.util.ArrayList;

public class searchOfficeHoursScheduleController {
    @FXML private TextField searchField;
    @FXML private TableView<ObservableList<String>> scheduleTable;
    @FXML private TableColumn<ObservableList<String>, String> nameColumn;
    @FXML private TableColumn<ObservableList<String>, String> dateColumn;
    @FXML private TableColumn<ObservableList<String>, String> timeSlotColumn;
    @FXML private TableColumn<ObservableList<String>, String> courseColumn;
    @FXML private TableColumn<ObservableList<String>, String> reasonColumn;
    @FXML private TableColumn<ObservableList<String>, String> commentColumn;

    private final CSVFileManager fileManager = new CSVFileManager("OfficeHoursSchedule");

    @FXML
    public void initialize() {
        // Set up table columns
        nameColumn.setCellValueFactory(getColumnValueFactory(0));
        dateColumn.setCellValueFactory(getColumnValueFactory(1));
        timeSlotColumn.setCellValueFactory(getColumnValueFactory(2));
        courseColumn.setCellValueFactory(getColumnValueFactory(3));
        reasonColumn.setCellValueFactory(getColumnValueFactory(4));
        commentColumn.setCellValueFactory(getColumnValueFactory(5));

        // Initialize with empty table
        scheduleTable.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            showAlert("Please enter a student name to search");
            return;
        }

        ArrayList<ArrayList<String>> allSchedules = fileManager.fileRead();
        ObservableList<ObservableList<String>> matchingSchedules = FXCollections.observableArrayList();

        for (ArrayList<String> schedule : allSchedules) {
            if (!schedule.isEmpty() && schedule.get(0).toLowerCase().contains(searchText)) {
                matchingSchedules.add(FXCollections.observableArrayList(schedule));
            }
        }

        scheduleTable.setItems(matchingSchedules);

        if (matchingSchedules.isEmpty()) {
            showAlert("No schedules found for: " + searchField.getText());
        }
    }

    private Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>> getColumnValueFactory(int index) {
        return cellData -> {
            String value = (cellData.getValue().size() > index) ? cellData.getValue().get(index) : "";
            return new SimpleStringProperty(value);
        };
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Results");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}