package ui;

import java.util.List;
import core.Recipe;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import persistency.RecipePersistence;

public class MyRecipesController {

    private static final double CARD_SPACING = 5.0;
    private static final double CARD_PADDING = 10.0;

    
    @FXML
    private ScrollPane recipesScrollPane;

    @FXML
    private VBox recipesVBox;  // <-- definerer recipesVBox


    @FXML
    public void initialize() {

        setupScrollPane();
        loadRecipes();

        Platform.runLater(() -> {
        recipesScrollPane.lookup(".viewport").setStyle("-fx-background-color: #ffffff;");
        });
    }

    private void setupScrollPane() {
        recipesScrollPane.setFitToHeight(true);

    }

    private void loadRecipes() {
        try {
            List<Recipe> recipes = RecipePersistence.loadRecipes();

            for (Recipe recipe : recipes) {
                VBox card = createRecipeCard(recipe);
                recipesVBox.getChildren().add(card);
            }
        } catch (Exception e) {
            System.err.println("Failed to load recipes: " + e.getMessage());
            e.printStackTrace();

        }
    }

    private VBox createRecipeCard(Recipe recipe) {
        VBox card = new VBox(CARD_SPACING);

        card.setPadding(new Insets(CARD_PADDING));
        card.setStyle(
            "-fx-background-color: #ffffff;" + 
            "-fx-border-color: lightgrey;" +
            " -fx-border-radius: 5;"  +
            "-fx-background-radius: 5;" + 
            " -fx-padding: 15;"
        );

        final Label title = new Label(recipe.getTitle());
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        final Label details = new Label(
            recipe.getDescription() + "  |  " + recipe.getSkillLevel().getDescription());
        details.setStyle("-fx-text-fill: gray;");

        final Label status = new Label(recipe.getProjectStatus().getDisplayName());
        status.setStyle(
            "-fx-text-fill: white;" +
            " -fx-padding: 3 8 3 8;" +
            " -fx-background-radius: 10;"
        );

        switch (recipe.getProjectStatus()) {
            case IN_PROGRESS -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(230, 138, 77, 0.5);");
            case FINISHED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(163, 178, 124, 0.65);");
            case PLANNED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(138, 186, 211, 0.7);");
            case PAUSED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(200, 206, 210, 0.7);");
            case ABANDONED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(211, 138, 138, 0.7);");
        }

        final Button detailsButton = new Button("Details");
        detailsButton.setOnAction(e -> openDetails(recipe)); // TODO: unsure of function name, change if needed

        final HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT); 
        
        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(title, details, status, spacer, detailsButton);

        card.getChildren().add(row);
        return card;
    }

    private void openDetails(Recipe recipe) {
        // TODO: load Recipe details
    }
}
