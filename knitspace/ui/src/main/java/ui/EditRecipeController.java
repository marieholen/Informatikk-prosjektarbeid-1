package ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import core.Recipe;
import core.SessionManager;
import core.User;
import core.enums.NeedleSize;
import core.enums.ProjectStatus;
import core.enums.SkillLevel;
import core.enums.YarnType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import persistency.RecipePersistence;

public class EditRecipeController {

    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> difficultyComboBox;
    @FXML private ComboBox<String> yarnTypeComboBox;
    @FXML private ComboBox<String> needleSizeComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Button uploadPdfButton;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    // State
    private Recipe currentRecipe;
    private boolean isNewRecipe = false;
    private Path storedPdfPath; // optional: persist later if you add this to Recipe or another store

    @FXML
    private void initialize() {
        for (SkillLevel level : SkillLevel.values()) {
            difficultyComboBox.getItems().add(level.getDescription());
        }
        for (YarnType type : YarnType.values()) {
            yarnTypeComboBox.getItems().add(type.getLabel());
        }
        for (NeedleSize size : NeedleSize.values()) {
            needleSizeComboBox.getItems().add(size.getLabel());
        }
        for (ProjectStatus status : ProjectStatus.values()) {
            statusComboBox.getItems().add(status.getDescription());
        }
    }

    /** Called when opening the screen to create a brand new recipe */
    public void newRecipeMode() {
        isNewRecipe = true;
        currentRecipe = null;

        titleField.clear();
        descriptionField.clear();
        difficultyComboBox.setValue(null);
        yarnTypeComboBox.setValue(null);
        needleSizeComboBox.setValue(null);
        statusComboBox.setValue(null);
        storedPdfPath = null;
        if (uploadPdfButton != null) uploadPdfButton.setText("Upload PDF");
    }

    /** Called when opening the screen to edit an existing recipe */
    public void setSelectedRecipe(Recipe recipe) {
        isNewRecipe = false;
        this.currentRecipe = new Recipe(
            recipe.getRecipeId(),
            recipe.getUserId(),
            recipe.getTitle(),
            recipe.getDescription(),
            recipe.getSkillLevel(),
            recipe.getYarnType(),
            recipe.getNeedleSize(),
            recipe.getProjectStatus()
        );

        titleField.setText(recipe.getTitle());
        descriptionField.setText(recipe.getDescription());
        difficultyComboBox.setValue(recipe.getSkillLevel().getDescription());
        yarnTypeComboBox.setValue(recipe.getYarnType().getLabel());
        needleSizeComboBox.setValue(recipe.getNeedleSize().getLabel());
        statusComboBox.setValue(recipe.getProjectStatus().getDescription());
    }

    @FXML
    private void handleUploadPdf(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select recipe PDF");
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf")
        );

        File docs = Paths.get(System.getProperty("user.home"), "Documents").toFile();
        if (docs.exists()) chooser.setInitialDirectory(docs);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selected = chooser.showOpenDialog(stage);
        if (selected == null) return; // user cancelled

        Path selectedPath = selected.toPath();

        if (!looksLikePdf(selectedPath)) {
            showError("That file doesn’t look like a valid PDF.");
            return;
        }

        try {
            Path appDir = Paths.get(System.getProperty("user.dir"), "..", "..", "data", "pdfs")
                .normalize()
                .toAbsolutePath();
            Files.createDirectories(appDir);

            String title = titleField.getText() == null ? "" : titleField.getText().trim();
            if (title.isEmpty()) {
                showError("Recipe needs a title!");
                return;
            }

            String titleEdited = title.replaceAll("[^a-zA-Z0-9-_ ]", "").replace(" ", "_");
            String pdfName = titleEdited + ".pdf";

            Path dest = appDir.resolve(pdfName);
            Files.copy(selectedPath, dest, StandardCopyOption.REPLACE_EXISTING);

            storedPdfPath = dest;
            uploadPdfButton.setText(pdfName);
        } catch (IOException ex) {
            showError("Could not copy the PDF to the app folder.\n" + ex.getMessage());
        }
    }

    // Minimal file signature check for PDFs
    private boolean looksLikePdf(Path path) {
        try (InputStream in = Files.newInputStream(path)) {
            byte[] header = new byte[5];
            if (in.read(header) == 5) {
                return "%PDF-".equals(new String(header, StandardCharsets.US_ASCII));
            }
        } catch (IOException ignored) { }
        return false;
    }

    @FXML
    private void onSaveClicked() {
        String title = titleField.getText();
        if (title == null || title.isBlank()) {
            showError("Title is required.");
            return;
        }

        // Make sure selections are made to avoid null enums
        if (difficultyComboBox.getValue() == null ||
            yarnTypeComboBox.getValue() == null ||
            needleSizeComboBox.getValue() == null ||
            statusComboBox.getValue() == null) {
            showError("Please select difficulty, yarn type, needle size, and status.");
            return;
        }

        User user = SessionManager.getCurrentUser();
        if (user == null) {
            showError("You are not logged in.");
            return;
        }

        try {
            if (isNewRecipe) {
                if (RecipePersistence.recipeExists(title)) {
                    showError("A recipe with this title already exists.");
                    return;
                }

                Recipe newRecipe = new Recipe(
                    user.getUserId(),
                    title,
                    descriptionField.getText(),
                    getSelectedSkillLevel(),
                    getSelectedYarnType(),
                    getSelectedNeedleSize(),
                    getSelectedProjectStatus()
                );

                RecipePersistence.addRecipe(newRecipe);
                showInfo("Recipe successfully created!");
            } else {
                if (currentRecipe == null) {
                    showError("No recipe selected to update.");
                    return;
                }

                Recipe updated = new Recipe(
                    currentRecipe.getRecipeId(),
                    currentRecipe.getUserId(),
                    title,
                    descriptionField.getText(),
                    getSelectedSkillLevel(),
                    getSelectedYarnType(),
                    getSelectedNeedleSize(),
                    getSelectedProjectStatus()
                );

                RecipePersistence.updateRecipe(currentRecipe.getRecipeId().toString(), updated);
                showInfo("Recipe successfully updated!");
            }

            goToMyRecipesView();

        } catch (Exception e) {
            showError("Error saving recipe: " + e.getMessage());
        }
    }

    @FXML
    private void onBackClicked() {
        goToMyRecipesView();
    }

    private void goToMyRecipesView() {
        try {
            // Use leading slash if your FXML is under resources/ui
            Parent root = FXMLLoader.load(getClass().getResource("/ui/MyRecipes.fxml"));
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to load recipe overview.");
        }
    }

    // Helpers
    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    private SkillLevel getSelectedSkillLevel() {
        String selected = difficultyComboBox.getValue();
        for (SkillLevel level : SkillLevel.values()) {
            if (level.getDescription().equals(selected)) return level;
        }
        return null;
    }

    private YarnType getSelectedYarnType() {
        String selected = yarnTypeComboBox.getValue();
        for (YarnType type : YarnType.values()) {
            if (type.getLabel().equals(selected)) return type;
        }
        return null;
    }

    private NeedleSize getSelectedNeedleSize() {
        String selected = needleSizeComboBox.getValue();
        for (NeedleSize size : NeedleSize.values()) {
            if (size.getLabel().equals(selected)) return size;
        }
        return null;
    }

    private ProjectStatus getSelectedProjectStatus() {
        String selected = statusComboBox.getValue();
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getDescription().equals(selected)) return status;
        }
        return null;
    }
}
