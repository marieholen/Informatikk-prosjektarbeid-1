package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the KnitSpace JavaFX application
 * <p>
 * This class initializes and launches the graphical user interface. 
 * It loads the {@code Login.fxml} file as the first scene when the application starts. 
 * </p>
 */
public class App extends Application {

  /**
   * Initiliazes and displays the primary stage of the application. 
   * 
   * @param stage the primaryy {@link Stage} provided by the JavaFX runtime
   * @throws IOExeption if the FXML file cannot be loaded
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
    Parent parent = fxmlLoader.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Entrypoint to Launch application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    launch(args);
  }
}