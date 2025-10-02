package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.Recipe;
import core.enums.NeedleSize;
import core.enums.ProjectStatus;
import core.enums.SkillLevel;
import core.enums.YarnType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistency.RecipePersistence;

public class MyRecipesController {

    private static final double CARD_SPACING = 5.0;
    private static final double CARD_PADDING = 10.0;

    
    @FXML private ScrollPane recipesScrollPane;
    @FXML private VBox recipesVBox; 
    @FXML private ComboBox<ProjectStatus> statusFilter;
    @FXML private ComboBox<SkillLevel> skillFilter;
    @FXML private ComboBox<YarnType> yarnFilter;
    @FXML private ComboBox<NeedleSize> needleFilter;
    @FXML private Button btnLogout;
    @FXML private Button btnNewrecipe;
    @FXML List<Recipe> allRecipes = new ArrayList<>();


    @FXML
    public void initialize() {
        setupScrollPane();
        setupFilters();
        loadRecipes();

        Platform.runLater(() -> {
        recipesScrollPane.lookup(".viewport").setStyle("-fx-background-color: #ffffff;");
        });
    }

    private void setupScrollPane() {
        recipesScrollPane.setFitToHeight(true);
        recipesScrollPane.setFitToWidth(true);
    }

    private void setupFilters() {
        statusFilter.getItems().add(null);
        statusFilter.getItems().addAll(ProjectStatus.values());

        skillFilter.getItems().add(null);
        skillFilter.getItems().addAll(SkillLevel.values());

        yarnFilter.getItems().add(null);
        yarnFilter.getItems().addAll(YarnType.values());

        needleFilter.getItems().add(null);
        needleFilter.getItems().addAll(NeedleSize.values());

        statusFilter.setOnAction(e -> applyFilters());
        skillFilter.setOnAction(e -> applyFilters());
        yarnFilter.setOnAction(e -> applyFilters());
        needleFilter.setOnAction(e -> applyFilters());
    }

    private void loadRecipes() {
        try {
            allRecipes = RecipePersistence.loadRecipes();
            System.out.println(allRecipes.size());
            applyFilters();

        } catch (Exception e) {
            System.err.println("Failed to load recipes: " + e.getMessage());
            e.printStackTrace();

        }
    }

    private void applyFilters() {
        recipesVBox.getChildren().clear();

        for (Recipe recipe : allRecipes) {
            if (!matchesFilters(recipe)) {
                continue;
            }
            recipesVBox.getChildren().add(createRecipeCard(recipe));
        }
    }

    private boolean matchesFilters(Recipe recipe) {
        if (statusFilter.getValue() != null && recipe.getProjectStatus() != statusFilter.getValue()) {
            return false;
        }
        if (skillFilter.getValue() != null && recipe.getSkillLevel() != skillFilter.getValue()) {
            return false;
        }
        if (yarnFilter.getValue() != null && recipe.getYarnType() != yarnFilter.getValue()) {
            return false;
        }
        if (needleFilter.getValue() != null && recipe.getNeedleSize() != needleFilter.getValue()) {
            return false;
        }

        return true;
    }
    

    private VBox createRecipeCard(Recipe recipe) {
        final VBox card = new VBox(CARD_SPACING);
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
            recipe.getSkillLevel().getDescription() + "  |  " + recipe.getNeedleSize() + "  |  " + recipe.getYarnType()
        );
        details.setStyle("-fx-text-fill: gray;");

        final Label status = new Label(recipe.getProjectStatus().getLabel());
        status.setStyle(
            "-fx-text-fill: white;" +
            " -fx-padding: 3 8 3 8;" +
            " -fx-background-radius: 10;"
        );

        switch (recipe.getProjectStatus()) {
            case IN_PROGRESS -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(230, 138, 77, 0.5);");
            case FINISHED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(163, 178, 124, 0.65);");
            case PLANNED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(138, 186, 211, 0.7);");
            case PAUSED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(162, 165, 167, 0.7);");
            case ABANDONED -> status.setStyle(status.getStyle() + "-fx-background-color: rgba(211, 138, 138, 0.7);");
        }

        final Button detailsButton = new Button("Details");
        detailsButton.setOnAction(e -> openRecipeDetails(e, recipe));

        final HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT); 
        
        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(title, details, status, spacer, detailsButton);

        card.getChildren().add(row);

        return card;
    }

    private void openRecipeDetails(ActionEvent event, Recipe recipe) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewRecipe.fxml"));
        try {
            Parent root = fxmlLoader.load();

            ViewRecipeController viewRecipeController = fxmlLoader.getController();

            viewRecipeController.setAndLoadRecipe(recipe);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, "Error when loading recipe detail window!:(");
            alert.showAndWait();
        }
    }

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

    @FXML
    private void handleNewRecipe() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/EditRecipeView.fxml"));
            btnNewrecipe.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
