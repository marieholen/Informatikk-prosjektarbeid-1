package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyRecipiesController {  

    @FXML private Button btnLogout;  

    @FXML
    private void handleLogout() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/Login.fxml"));

            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Scene scene = stage.getScene();
            if (scene != null) {
                scene.setRoot(root);
            } else {
                stage.setScene(new Scene(root));
            }
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
