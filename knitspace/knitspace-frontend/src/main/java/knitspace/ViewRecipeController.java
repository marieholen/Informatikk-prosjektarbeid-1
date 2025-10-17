package knitspace;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for Viewrecipe.fxml.
 * Handles displaying a knitting recipe.
 */
public class ViewRecipeController {
    @FXML
    private Button editButton;

    /**
     * Initialize the controller and set up event handlers.
     */
    @FXML
    private void initialize() {
        editButton.setOnAction(this::handleEditButtonClick);
    }

    /**
     * Handle the Edit Recipe button click.
     * 
     * @param event the ActionEvent triggered by clicking the button.
     */
    @FXML
    private void handleEditButtonClick(ActionEvent event) {
        try {
            goToEditRecipe((Node) event.getSource(), "ViewRecipe");
        } catch (Exception e) {
            showAlert("Navigation error", "Could not open the EditRecipe page." + e.getMessage());
        }
    }

    /**
     * Navigate to the EditRecipeView page.
     * 
     * @param anyNodeInCurrentScene any Node from the current scene to get the Stage.
     */
    @FXML
    public void goToEditRecipe(Node anyNodeInCurrentScene, String source) {
        try {
            if (anyNodeInCurrentScene == null || anyNodeInCurrentScene.getScene() == null){
                throw new IllegalArgumentException("Node cannot be null");
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/EditRecipeView.fxml"));
            Scene scene = new Scene(loader.load());

            EditRecipeController controller = loader.getController();
            controller.setSource(source);

            Stage stage = (Stage) anyNodeInCurrentScene.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation error", "Could not open the EditRecipeView page.");
        }
    } 
    
    /**
     * Show an error alert dialog.
     * 
     * @param title the title of the alert
     * @param message the message to display
     */
    @FXML
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @FXML
    private void handleDeleteButtonClick(ActionEvent event) {
        // TODO: implement later. Currently a stub for TestFX tests.
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        // TODO: implement later. Stub for TestFX tests
    }


}