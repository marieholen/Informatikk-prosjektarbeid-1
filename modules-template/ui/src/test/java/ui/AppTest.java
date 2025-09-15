package ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.nio.file.Files.deleteIfExists;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 * TestFX App test suite.
 */
class AppTest extends ApplicationTest {

  private AppController controller;
  private Parent root;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
    root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @Test
  void createFileWhenButtonClicks() throws Exception {
    // create path for recipe.txt
    var path = java.nio.file.Paths.get("recipe.txt");

    // delete if file already exists before running program
    deleteIfExists(path);

    // returns error if file is not deleted
    assertFalse(
        java.nio.file.Files.exists(path),
        "recipe.txt should not exist before the program runs");

    // runs program and clicks the button
    interact(() -> controller.onCreateRecipeButtonClick());

    // waits until program has finished running
    waitForFxEvents();

    // checks if recipe.txt exists
    assertTrue(
        java.nio.file.Files.exists(path), "recipe.txt has not been created");

    // deletes file after test is finished
    deleteIfExists(path);
  }
}
