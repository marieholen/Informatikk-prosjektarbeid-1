package knitspace;

import static org.mockito.Mockito.*;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.any;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.util.WaitForAsyncUtils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knitspace.client.RecipeClient;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.exception.ApiException;
import knitspace.model.Recipe;
import java.net.URI;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.Assertions;
import java.awt.Desktop;

@ExtendWith({MockitoExtension.class, ApplicationExtension.class})
public class ViewRecipeControllerTest {

  private ViewRecipeController controller;


  @Mock
  private RecipeClient mockClient;

  @Mock
  private Recipe mockRecipe;

  @Mock
  private RecipeClient recipeClient;

  @Start
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/ViewRecipe.fxml"));
    Parent root = loader.load();
    controller = loader.getController();

    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  void setupMocks() {
    lenient().when(mockRecipe.recipeId()).thenReturn(UUID.randomUUID().toString());
    lenient().when(mockRecipe.title()).thenReturn("Test Title");
    lenient().when(mockRecipe.description()).thenReturn("Test Description");
    lenient().when(mockRecipe.skillLevel()).thenReturn(SkillLevel.BEGINNER);
    lenient().when(mockRecipe.yarnType()).thenReturn(YarnType.WOOL);
    lenient().when(mockRecipe.needleSize()).thenReturn(NeedleSize.SIZE_8_0);
    lenient().when(mockRecipe.projectStatus()).thenReturn(ProjectStatus.IN_PROGRESS);

    Platform.runLater(() -> controller.setAndLoadRecipe(mockRecipe));
  }

  @Test
  void testLabelsAreUpdatedWithRecipe(FxRobot robot) {
    Platform.runLater(() -> controller.setAndLoadRecipe(mockRecipe));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals("Test Title", controller.getTitleLabel().getText());
    assertEquals("Test Description", controller.getDescriptionLabel().getText());

    assertEquals("Beginner", controller.getSkillLevelLabel().getText());
    assertEquals("Wool", controller.getYarnLabel().getText());
    assertEquals("8.0 mm", controller.getNeedleLabel().getText());
    assertEquals("In Progress - currently knitting", controller.getStatusLabel().getText());
  }

  @Test
  void testHandleEditButtonClick(FxRobot robot) {
    Platform.runLater(() -> controller.setAndLoadRecipe(mockRecipe));
    WaitForAsyncUtils.waitForFxEvents();

    robot.clickOn("#editButton");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat("#saveButton", isVisible());
  }

  @Test
  public void testShowAlertOnError(FxRobot robot) throws InterruptedException {
    Platform.runLater(() -> controller.showAlert("Error", "This is a test alert"));
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".dialog-pane", isVisible());
  }


  @Test
  void testDeleteRecipeCallsClient(FxRobot robot) throws Exception {
    controller.recipeClient = mockClient;

    Platform.runLater(() -> controller.setAndLoadRecipe(mockRecipe));
    WaitForAsyncUtils.waitForFxEvents();

    robot.clickOn("#deleteButton");
    WaitForAsyncUtils.waitForFxEvents();

    verify(mockClient).deleteRecipe(UUID.fromString(mockRecipe.recipeId()));
  }

  @Test
  void testDeleteRecipeShowsAlertOnFailure(FxRobot robot) throws Exception {
    controller.recipeClient = mockClient;

    doThrow(new ApiException("Recipe not found (404)"))
        .when(mockClient).deleteRecipe(any(UUID.class));

    Platform.runLater(() -> controller.setAndLoadRecipe(mockRecipe));
    WaitForAsyncUtils.waitForFxEvents();

    robot.clickOn("#deleteButton");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".dialog-pane", isVisible());
  }
   
  @Test
  public void testHandleBackButtonClick(FxRobot robot) throws ApiException {
    robot.clickOn("#backButton");
    WaitForAsyncUtils.waitForFxEvents();

    robot.clickOn("OK");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat("#myRecipesLabel", isVisible());
  }

  @Test
  public void testHandleOpenPdf() throws Exception {
    String recipeId = "11111111-1111-1111-1111-111111111111";

    Recipe idOnly = mock(Recipe.class);
    when(idOnly.recipeId()).thenReturn(recipeId);

    java.lang.reflect.Field f = ViewRecipeController.class.getDeclaredField("selectedRecipe");
    f.setAccessible(true);
    f.set(controller, idOnly);

    try (MockedStatic<Desktop> desktopStatic = mockStatic(Desktop.class)) {
      Desktop desktopMock = mock(Desktop.class);
      desktopStatic.when(Desktop::isDesktopSupported).thenReturn(true);
      desktopStatic.when(Desktop::getDesktop).thenReturn(desktopMock);

      controller.handleOpenPdf();

      ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
      verify(desktopMock).browse(uriCaptor.capture());

      URI opened = uriCaptor.getValue();
      assertEquals("file", opened.getScheme());
      Assertions.assertTrue(
        opened.getPath().endsWith("/" + recipeId + ".pdf"),
        "Expected URI to end with /" + recipeId + ".pdf, but was: " + opened
      );
    }
  }
}

