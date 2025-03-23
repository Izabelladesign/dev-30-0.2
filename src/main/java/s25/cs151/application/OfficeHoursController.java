package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.util.ArrayList;

public class OfficeHoursController {

    @FXML private ComboBox<String> semesterComboBox;
    @FXML private TextField yearTextField;

    @FXML private CheckBox mondayCheck;
    @FXML private CheckBox tuesdayCheck;
    @FXML private CheckBox wednesdayCheck;
    @FXML private CheckBox thursdayCheck;
    @FXML private CheckBox fridayCheck;

    @FXML private TableView<ObservableList<String>> officeHoursTable;
    @FXML private TableColumn<ObservableList<String>, String> semesterColumn;
    @FXML private TableColumn<ObservableList<String>, String> yearColumn;
    @FXML private TableColumn<ObservableList<String>, String> daysColumn;

    private final CSVFileManager fileManager = new CSVFileManager("office_hours");

    @FXML
    public void initialize() {
        semesterColumn.setCellValueFactory(getColumnValueFactory(0));
        yearColumn.setCellValueFactory(getColumnValueFactory(1));
        daysColumn.setCellValueFactory(getColumnValueFactory(2));
    }

    private Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, javafx.beans.value.ObservableValue<String>> getColumnValueFactory(int columnIndex) {
        return cellData -> {
            ObservableList<String> row = cellData.getValue();
            if (row.size() > columnIndex) {
                return new javafx.beans.property.SimpleStringProperty(row.get(columnIndex));
            } else {
                return new javafx.beans.property.SimpleStringProperty("");
            }
        };
    }

    @FXML
    private void saveOfficeHours() {
        String semester = semesterComboBox.getValue();
        String yearText = yearTextField.getText().trim();

        if (semester == null) return;
        if (yearText.isEmpty()) return;

        int year = 0;
        try {
            year = Integer.parseInt(yearText);
        } catch (Exception e) {
            return;
        }

        ArrayList<String> selectedDays = new ArrayList<>();
        if (mondayCheck.isSelected()) selectedDays.add("Monday");
        if (tuesdayCheck.isSelected()) selectedDays.add("Tuesday");
        if (wednesdayCheck.isSelected()) selectedDays.add("Wednesday");
        if (thursdayCheck.isSelected()) selectedDays.add("Thursday");
        if (fridayCheck.isSelected()) selectedDays.add("Friday");

        if (selectedDays.isEmpty()) return;

        String daysString = String.join(", ", selectedDays);

        ArrayList<String> dataRow = new ArrayList<>();
        dataRow.add(semester);
        dataRow.add(String.valueOf(year));
        dataRow.add(daysString);
        fileManager.fileWrite(dataRow);

        ObservableList<String> rowData = FXCollections.observableArrayList(dataRow);
        officeHoursTable.getItems().add(rowData);

        semesterComboBox.setValue(null);
        yearTextField.clear();
        mondayCheck.setSelected(false);
        tuesdayCheck.setSelected(false);
        wednesdayCheck.setSelected(false);
        thursdayCheck.setSelected(false);
        fridayCheck.setSelected(false);
    }

    @FXML
    private void showOfficeHours() {
        officeHoursTable.getItems().clear();

        ArrayList<ArrayList<String>> rows = fileManager.fileRead();
        for (ArrayList<String> row : rows) {
            ObservableList<String> rowData = FXCollections.observableArrayList(row);
            officeHoursTable.getItems().add(rowData);
        }
    }
}