
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

    /**
     * Initializes the TableView columns by assigning cell value factories
     */
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

    /**
     * Create a method to handle the search according to the student name
     * Read the data stored in the CSV file
     * Sort the data of time slots and scheduled date in the descending order
     */
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

        matchingSchedules.sort((row1, row2) -> {

            int cmp = row2.get(1).compareTo(row1.get(1));
            if (cmp != 0) return cmp;
            return row2.get(2).compareTo(row1.get(2));
        });

        scheduleTable.setItems(matchingSchedules);

        if (matchingSchedules.isEmpty()) {
            showAlert("No schedules found for: " + searchField.getText());
        }
    }

    /**
     * Returns a cell value factory that maps a row's value to a specific index in the ObservableList.
     * This is used to bind a TableColumn to a specific column of the data in the TableView.
     * @param index
     * @return
     */
    private Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>> getColumnValueFactory(int index) {
        return cellData -> {
            String value = (cellData.getValue().size() > index) ? cellData.getValue().get(index) : "";
            return new SimpleStringProperty(value);
        };
    }

    /**
     * Displays an information alert box with the given message.
     * Used to show feedback to the user when a search result is empty or an action is completed.
     *
     * @param message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Results");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Create a method to delete the schedule
     * Remove from CSV and then rewrite CSV with remaining rows
     */
    @FXML
    private void handleDelete() {
        ObservableList<String> selectedRow = scheduleTable.getSelectionModel().getSelectedItem();

        if (selectedRow == null) {
            showAlert("Please select a schedule to delete.");
            return;
        }

        scheduleTable.getItems().remove(selectedRow);

        ArrayList<ArrayList<String>> allSchedules = fileManager.fileRead();
        ArrayList<ArrayList<String>> updatedSchedules = new ArrayList<>();

        for (ArrayList<String> row : allSchedules) {
            if (!matchRow(row, selectedRow)) {
                updatedSchedules.add(row);
            }
        }

        fileManager.overwriteFile(updatedSchedules);
    }

    /**
     * Create a helper method to exactly match the selected row of the Table inside the full CSV data file
     * @param row
     * @param selectedRow
     * @return
     */
    private boolean matchRow(ArrayList<String> row, ObservableList<String> selectedRow) {
        if (row.size() != selectedRow.size()) return false;

        for (int i = 0; i < row.size(); i++) {
            if (!row.get(i).trim().equals(selectedRow.get(i).trim())) {
                return false;
            }
        }
        return true;
    }

}

