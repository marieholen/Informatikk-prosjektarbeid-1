package knitspace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for {@code MyRecipes.fxml}.
 * Handles user interaction on the "My recipes" page.  
 */
public class MyRecipesController {
    

    /**
     * Handles the "New Recipe" button click by redirecting to the 
     * {@code EditRecipeView.fxml} page to create a new recipe. 
     * 
     * @param event {@link ActionEvent} triggered by the cutton press
     */
    @FXML
    private void handleNewRecipe(ActionEvent event) {

        System.out.println(getClass().getResource("/knitspace/EditRecipeView.fxml"));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/EditRecipeView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Create New Recipe");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Logout" button click. 
     */
    @FXML
    private void handleLogout() {
        System.out.println("Logout clicked (not implemented)");
    }

}

