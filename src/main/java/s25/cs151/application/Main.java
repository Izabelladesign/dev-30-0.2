package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *  Main class to show the entry point of the JavaFX application
 *
 */

public class Main extends Application {
    private static Stage mainStage; // main stage is the primary stage

    /**
     *  Displays the home page
     *
     * @param stage the primary stage for this application, onto which the application scene can be set.
     * @throws IOException when the FXML file is unable to load
     */
    @Override
    public void start(Stage stage)throws IOException{
        mainStage = stage;
        showHomePage(); //display the home page
        stage.show(); // show the main stage
        System.out.println();
    }

    /**
     *  The main stage sets and loads the setting for the home page.
     *
     * @throws IOException when the FXML file is unable to laod
     */
    public static void showHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-ClockItMainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        mainStage.setScene(scene); // settings the scene for the main stage
    }

    /**
     *  Opens a new window where office hours can be specified.
     *  If a window is already open, then it shuts before starting another one.
     *
     * @throws IOException when the FXML is unable to load.
     */
    public static void defineOfficeHoursPage() throws IOException {
        if(mainStage !=null){
            mainStage.close();
            //closing the existing window
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("office-hours.fxml")); //load the office hours FXML file
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        //Create a new stage for the office hours
        Stage stage = new Stage();
        stage.setTitle("Define Office Hours");
        stage.setScene(scene);
        stage.show(); //display the new stage

    }

    /**
     * The main method to start the JavaFX application
     *
     * @param args the command-line argument
     */
    public static void main(String[] args) {
        launch(); //launch the JavaFX application
    }
}