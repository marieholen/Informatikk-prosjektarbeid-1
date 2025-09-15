package ui;

import java.io.IOException;
import java.nio.file.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import core.KnitSpace;



/**
 * Main controller for the application.
 */
public class AppController {

  @FXML
  private Button createRecipeFileButton;

  @FXML
  private TextField feedbackTextField;

  @FXML
  protected void onCreateRecipeButtonClick() {

    try {

      KnitSpace.createFile();
      feedbackTextField.setText("File created successfully!");

    } catch (FileAlreadyExistsException f) {

      feedbackTextField.setText("File already exists!");

    } catch (IOException e) {

      feedbackTextField.setText("Something went wrong:(");;
      e.printStackTrace();

    }

  }

}
