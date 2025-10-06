package ui;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import core.*;
import core.enums.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MyRecipesControllerTest extends ApplicationTest {

    private MyRecipesController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyRecipes.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setup() {
        SessionManager.setCurrentUser(new User("tester", "123"));
        WaitForAsyncUtils.waitForFxEvents();
    }

    private Object getPrivateField(String name) throws Exception {
        Field f = MyRecipesController.class.getDeclaredField(name);
        f.setAccessible(true);
        return f.get(controller);
    }


    @Test
    void testInitializeSetUp() throws Exception {
        Method init = MyRecipesController.class.getDeclaredMethod("initialize");
        init.setAccessible(true);
        assertDoesNotThrow(() -> {
            try { init.invoke(controller); } catch (Exception e) { throw new RuntimeException(e); }
        });

        ScrollPane pane = (ScrollPane) getPrivateField("recipesScrollPane");
        assertTrue(pane.isFitToWidth());
        assertTrue(pane.isFitToHeight());
    }

    @Test
    void testCreateRecipeCardStatusColors() throws Exception {
        Method m = MyRecipesController.class.getDeclaredMethod("createRecipeCard", Recipe.class);
        m.setAccessible(true);

        for (ProjectStatus status : ProjectStatus.values()) {
            Recipe r = new Recipe(SessionManager.getCurrentUser().getUserId(),
                    "Title " + status, "description", SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_3_0, status);
            VBox card = (VBox) m.invoke(controller, r);
            assertNotNull(card);
            Label label = (Label) ((HBox) card.getChildren().get(0)).getChildren().get(2);
            assertTrue(label.getStyle().contains("-fx-background-color"));
        }
    }

    @Test
    void testMatchesFilters() throws Exception {
        Method m = MyRecipesController.class.getDeclaredMethod("matchesFilters", Recipe.class);
        m.setAccessible(true);

        ComboBox<ProjectStatus> statusFilter = (ComboBox<ProjectStatus>) getPrivateField("statusFilter");
        ComboBox<SkillLevel> skillFilter = (ComboBox<SkillLevel>) getPrivateField("skillFilter");
        ComboBox<YarnType> yarnFilter = (ComboBox<YarnType>) getPrivateField("yarnFilter");
        ComboBox<NeedleSize> needleFilter = (ComboBox<NeedleSize>) getPrivateField("needleFilter");
        TextField searchField = (TextField) getPrivateField("searchField");

        Recipe recipe = new Recipe(SessionManager.getCurrentUser().getUserId(), "Sweater", "description",
                SkillLevel.ADVANCED, YarnType.COTTON, NeedleSize.SIZE_4_0, ProjectStatus.FINISHED);

        interact(() -> statusFilter.setValue(ProjectStatus.PLANNED));
        assertFalse((boolean) m.invoke(controller, recipe));

        interact(() -> { statusFilter.setValue(null); skillFilter.setValue(SkillLevel.BEGINNER); });
        assertFalse((boolean) m.invoke(controller, recipe));

        interact(() -> { skillFilter.setValue(null); yarnFilter.setValue(YarnType.WOOL); });
        assertFalse((boolean) m.invoke(controller, recipe));

        interact(() -> { yarnFilter.setValue(null); needleFilter.setValue(NeedleSize.SIZE_2_0); });
        assertFalse((boolean) m.invoke(controller, recipe));

        interact(() -> { needleFilter.setValue(null); searchField.setText("xyz"); });
        assertFalse((boolean) m.invoke(controller, recipe));

        interact(() -> searchField.setText("Sweater"));
        assertTrue((boolean) m.invoke(controller, recipe));
    }

    @Test
    void testOpenRecipeDetails() throws Exception {
    Method open = MyRecipesController.class.getDeclaredMethod("openRecipeDetails", ActionEvent.class, Recipe.class);
    open.setAccessible(true);

    Button testBtn = new Button("testBtn");
    interact(() -> ((Pane) stage.getScene().getRoot()).getChildren().add(testBtn));
    WaitForAsyncUtils.waitForFxEvents();

    Recipe r = new Recipe(SessionManager.getCurrentUser().getUserId(), "R", "description",
            SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_2_0, ProjectStatus.PLANNED);
    ActionEvent event = new ActionEvent(testBtn, null);

    interact(() -> {
        try {
            open.invoke(controller, event, r);
        } catch (Exception e) {
            fail(e);
        }
    });
    WaitForAsyncUtils.waitForFxEvents();

    assertTrue(true);
}


    @Test
    void testHandleLogout() throws Exception {
        Method logout = MyRecipesController.class.getDeclaredMethod("handleLogout");
        logout.setAccessible(true);
        assertDoesNotThrow(() -> logout.invoke(controller));
    }

    @Test
    void testHandleNewRecipe() throws Exception {
        Method newRecipe = MyRecipesController.class.getDeclaredMethod("handleNewRecipe");
        newRecipe.setAccessible(true);
        assertDoesNotThrow(() -> newRecipe.invoke(controller));
    }

}
