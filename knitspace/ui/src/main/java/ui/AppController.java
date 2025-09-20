package ui;

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
    String response = KnitSpace.createFile();
    feedbackTextField.setText(response);
  }
}
