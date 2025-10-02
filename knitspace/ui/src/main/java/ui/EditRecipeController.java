package ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditRecipeController {
    @FXML private Button uploadPdfButton;
    @FXML private Button saveButton;
    @FXML private TextField titleField;

    private Path storedPdfPath;

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
            Path appDir = Paths.get(System.getProperty("user.dir"), "data", "pdfs");
            Files.createDirectories(appDir);

            String title = titleField.getText().trim();
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

    @FXML
    private void handleSave(ActionEvent event) {
        Alert ok = new Alert(Alert.AlertType.INFORMATION,
                storedPdfPath == null
                        ? "Saved recipe (no PDF selected)."
                        : "Saved recipe with PDF:\n" + storedPdfPath);
        ok.showAndWait();
    }

    // Checks if file is a pdf
    private boolean looksLikePdf(Path path) {
        try (InputStream in = Files.newInputStream(path)) {
            byte[] header = new byte[5];
            if (in.read(header) == 5) {
                return "%PDF-".equals(new String(header, StandardCharsets.US_ASCII));
            }
        } catch (IOException ignored) { }
        return false;
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
