package knitspace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for MyRecipes.fxml.
 */
public class MyRecipesController {
    

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

}

