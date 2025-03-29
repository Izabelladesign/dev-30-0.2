package s25.cs151.application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Control Class to display "defineCourses" screen in the application
 */
public class CoursesController {

    @FXML
    private TextField codeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField sectionField;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, String> codeColumn;

    @FXML
    private TableColumn<Course, String> nameColumn;

    @FXML
    private TableColumn<Course, String> sectionColumn;

    private final ObservableList<Course> courseList = FXCollections.observableArrayList();

    /**
     * Display the table column with property by initializing
     */
    @FXML
    public void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));

        courseTable.setItems(courseList);
        loadCourses();
    }


    /**
     * Sorts courses in descending order by course codes
     */
    @FXML
    private void handleAddCourse() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        String section = sectionField.getText().trim();

        if (code.isEmpty() || name.isEmpty() || section.isEmpty()) {
            showAlert("Please fill in all course fields.");
            return;
        }

        /**
         * Check for duplicates
         */
        for (Course c : courseList) {
            if (c.getCode().equalsIgnoreCase(code) &&
                    c.getName().equalsIgnoreCase(name) &&
                    c.getSection().equalsIgnoreCase(section)) {
                showAlert("This course entry already exists.");
                return;
            }
        }

        Course newCourse = new Course(code, name, section);
        courseList.add(newCourse);
        saveCourses();
        sortCourses();

        codeField.clear();
        nameField.clear();
        sectionField.clear();
    }

    /**
     * Sort the course codes in descending order
     */
    private void sortCourses() {
        codeColumn.setSortType(TableColumn.SortType.DESCENDING);
        courseTable.getSortOrder().setAll(codeColumn);
        courseList.sort(Comparator.comparing(Course::getCode).reversed());
    }

    /**
     *
     */
    private void saveCourses() {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for (Course c : courseList) {
            ArrayList<String> row = new ArrayList<>();
            row.add(c.getCode());
            row.add(c.getName());
            row.add(c.getSection());
            rows.add(row);
        }

        CSVFileManager fileManager = new CSVFileManager("courses");
        fileManager.overwriteFile(rows);
    }

    /**
     * Show the courses in a row and read from file manager
     */

    private void loadCourses() {
        CSVFileManager fileManager = new CSVFileManager("courses");
        ArrayList<ArrayList<String>> rows = fileManager.fileRead();

        courseList.clear();
        for (List<String> row : rows) {
            if (row.size() >= 3) {
                Course c = new Course(row.get(0), row.get(1), row.get(2));
                courseList.add(c);
            }
        }

        sortCourses();
    }

    /**
     * Check all the required information is filled out in the correct box and string
     * @param msg
     */
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Represents a Course with a code, name, and section
     * JavaFX properties allow the TableView
     * Add getter and property methods
     */

    public static class Course {
        private final StringProperty code;
        private final StringProperty name;
        private final StringProperty section;

        public Course(String code, String name, String section) {
            this.code = new SimpleStringProperty(code);
            this.name = new SimpleStringProperty(name);
            this.section = new SimpleStringProperty(section);
        }

        public String getCode() {
            return code.get();
        }

        public String getName() {
            return name.get();
        }

        public String getSection() {
            return section.get();
        }
    }
}