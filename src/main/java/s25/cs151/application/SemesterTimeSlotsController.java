package s25.cs151.application;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Control Class to manage the Semester Time Slots in the application
 * User is able to choose time in intervals and handles the action with tables and boxes
 */


public class SemesterTimeSlotsController {


    @FXML private ComboBox<LocalTime> fromComboBox;
    @FXML private ComboBox<LocalTime> toComboBox;
    @FXML private TableView<TimeSlot> slotTableView;
    @FXML private TableColumn<TimeSlot, String> fromCol;
    @FXML private TableColumn<TimeSlot, String> toCol;


    private final CSVFileManager fileManager = new CSVFileManager("Semesters_Time_Slots");
    private static final DateTimeFormatter HourMinFormatter = DateTimeFormatter.ofPattern("HH:mm");


    /**
     * Controlling the Time Dropdown and Table Column by initializing
     */
    @FXML
    public void initialize() {
        createTimeDropdown();
        formatTableColumns();
        slotTableView.setItems(FXCollections.observableArrayList());
    }


    /**
     * Shows the 'From' and 'To' boxes of 15 minutes interval  start from 00:00 to 23:45
     */
    private void createTimeDropdown() {
        List<LocalTime> options = generateTimeIntervals();


        fromComboBox.setItems(FXCollections.observableArrayList(options));
        toComboBox.setItems(FXCollections.observableArrayList(options));


        formatComboBoxDisplay(fromComboBox);
        formatComboBoxDisplay(toComboBox);
    }


    /**
     * Generates the list of times in 15 mins intervals
     *
     * @return list of the local Times that are turned into the intervals
     */
    private List<LocalTime> generateTimeIntervals() {
        List<LocalTime> times = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                times.add(LocalTime.of(hour, minute));
            }
        }
        return times;
    }


    /**
     * Sets the display of the comboBoxes in the Hour to Min format like HH:mm
     *
     * @param comboBox provides the description of the comboBox either fromComboBox or toComboBox
     */
    private void formatComboBoxDisplay(ComboBox<LocalTime> comboBox) {
        comboBox.setCellFactory(cell -> new ListCell<LocalTime>() {
            @Override
            protected void updateItem(LocalTime time, boolean empty) {
                super.updateItem(time, empty);
                if (empty || time == null) {
                    setText(null);
                } else {
                    setText(time.format(HourMinFormatter));
                }
            }
        });


        comboBox.setButtonCell(new ListCell<LocalTime>() {
            @Override
            protected void updateItem(LocalTime time, boolean empty) {
                super.updateItem(time, empty);
                if (empty || time == null) {
                    setText(null);
                } else {
                    setText(time.format(HourMinFormatter));
                }
            }
        });
    }


    /**
     * Formats the Table Column to display the time in HH:mm format
     */
    private void formatTableColumns() {


        fromCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFromTime().format(HourMinFormatter)));


        toCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getToTime().format(HourMinFormatter)));
    }


    /**
     * Saves the time Slots in the table to the CSV file
     * Each row is a time slot starting From a time To ending on a time
     */
    @FXML
    private void saveAllSlots() {
        LocalTime fromTime = fromComboBox.getValue();
        LocalTime toTime = toComboBox.getValue();

        if (fromTime == null) {
            return;
        }

        if (toTime == null) {
            return;
        }

        if (fromTime.compareTo(toTime) >= 0) {
            return;
        }

        TimeSlot newSlot = new TimeSlot(fromTime, toTime);
        slotTableView.getItems().add(newSlot);

        fromComboBox.setValue(null);
        toComboBox.setValue(null);

        List<TimeSlot> currentList = new ArrayList<>(slotTableView.getItems());
        java.util.Collections.sort(currentList, new java.util.Comparator<TimeSlot>() {
            @Override
            public int compare(TimeSlot a, TimeSlot b) {
                return a.getFromTime().compareTo(b.getFromTime());
            }
        });

        slotTableView.getItems().setAll(currentList); // replace with sorted list

        // Save to CSV
        for (TimeSlot slot : currentList) {
            ArrayList<String> dataRow = new ArrayList<>();
            dataRow.add(slot.getFromTime().format(HourMinFormatter));
            dataRow.add(slot.getToTime().format(HourMinFormatter));
            fileManager.fileWrite(dataRow);
        }
    }

    /**
     * Class to show the Time slots 'from' -> 'to' time intervals
     */


    public static class TimeSlot {
        private final LocalTime fromTime;
        private final LocalTime toTime;


        /**
         * Constructor for the TimeSlot
         *
         * @param fromTime which is the starting of the Time Slot
         * @param toTime which is the end of the Time Slot
         *
         */
        public TimeSlot(LocalTime fromTime, LocalTime toTime) {
            this.fromTime = fromTime;
            this.toTime = toTime;
        }


        public LocalTime getFromTime() {
            return fromTime;
        }


        public LocalTime getToTime() {
            return toTime;
        }
    }
}
