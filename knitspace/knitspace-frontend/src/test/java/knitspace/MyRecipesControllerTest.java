package knitspace;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import knitspace.client.RecipeClient;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.meta.SessionManager;
import knitspace.model.Recipe;
import knitspace.model.User;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class MyRecipesControllerTest {

    @Mock
    private RecipeClient recipeClient;

    private Stage stage;
    private MyRecipesController controller;

    @BeforeEach
    void setup() {
        SessionManager.setCurrentUser(null);
        controller = new MyRecipesController(recipeClient);
        controller.searchField = new TextField();
    }

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));

        FxRobot robot = new FxRobot();
        Platform.runLater(() -> {
            robot.clickOn("OK");
        });

        Scene scene = new Scene(loader.load());
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void loadRecipes_UsesRecipeClientAndReturnsRecipes() throws Exception {
        User user = new User("user1", "tester", "password");
        SessionManager.setCurrentUser(user);

        Recipe r1 = new Recipe("r1", "user1", "Title1", "Desc1", null, null, null, null);
        Recipe r2 = new Recipe("r2", "user1", "Title2", "Desc2", null, null, null, null);

        List<Recipe> expected = List.of(r1, r2);

        when(recipeClient.getRecipesForUser(user)).thenReturn(expected);

        MyRecipesController controller = new MyRecipesController(recipeClient);

        Method m = MyRecipesController.class.getDeclaredMethod("loadRecipes");
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Recipe> actual = (List<Recipe>) m.invoke(controller);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void testNewRecipeButtonRedirectsToEditRecipeView(FxRobot robot) {
        robot.clickOn("#btnNewrecipe");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Create New Recipe", stage.getTitle(),
                "Stage title should change to 'Create New Recipe' after clicking New Recipe button");

        assertNotNull(robot.lookup("#saveButton").query(),
                "EditRecipeView.fxml should be loaded and contain a Save button");
    }

    @Test
    void testMatchesSearchField() {
        try {
            MyRecipesController controller = new MyRecipesController();
            Field sfField = MyRecipesController.class.getDeclaredField("searchField");
            sfField.setAccessible(true);
            TextField tf = new TextField();
            sfField.set(controller, tf);

            Recipe recipe = new Recipe("recipeId", "userId", "Wool Sweater", "A warm sweater", null, null, null, null);

            Method method = MyRecipesController.class.getDeclaredMethod("matchesSearchField", Recipe.class);
            method.setAccessible(true);

            tf.setText("");
            assertTrue((boolean) method.invoke(controller, recipe), "Blank search should match all recipes");

            tf.setText("wool");
            assertTrue((boolean) method.invoke(controller, recipe), "Matching text should return true");

            tf.setText("socks");
            assertFalse((boolean) method.invoke(controller, recipe), "Non-matching text should return false");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testApplyFilters_combinedFilters() throws Exception {
        MyRecipesController controller = new MyRecipesController();

        Field sfField = MyRecipesController.class.getDeclaredField("searchField");
        sfField.setAccessible(true);
        TextField sf = new TextField();
        sfField.set(controller, sf);

        Field skillField = MyRecipesController.class.getDeclaredField("skillFilter");
        skillField.setAccessible(true);
        ComboBox<SkillLevel> skillCb = new ComboBox<>();
        skillCb.setValue(SkillLevel.BEGINNER);
        skillField.set(controller, skillCb);

        Field yarnField = MyRecipesController.class.getDeclaredField("yarnFilter");
        yarnField.setAccessible(true);
        ComboBox<YarnType> yarnCb = new ComboBox<>();
        yarnCb.setValue(YarnType.WOOL);
        yarnField.set(controller, yarnCb);

        Field needleField = MyRecipesController.class.getDeclaredField("needleFilter");
        needleField.setAccessible(true);
        ComboBox<NeedleSize> needleCb = new ComboBox<>();
        needleCb.setValue(NeedleSize.SIZE_2_0);
        needleField.set(controller, needleCb);

        Field statusField = MyRecipesController.class.getDeclaredField("statusFilter");
        statusField.setAccessible(true);
        ComboBox<ProjectStatus> statusCb = new ComboBox<>();
        statusCb.setValue(ProjectStatus.IN_PROGRESS);
        statusField.set(controller, statusCb);

        Recipe rMatch = new Recipe("1", "u", "Match Title", "d", SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_2_0, ProjectStatus.IN_PROGRESS);
        Recipe rSkill = new Recipe("2", "u", "Match Title", "d", SkillLevel.ADVANCED, YarnType.WOOL, NeedleSize.SIZE_2_0, ProjectStatus.IN_PROGRESS);
        Recipe rYarn = new Recipe("3", "u", "Match Title", "d", SkillLevel.BEGINNER, YarnType.COTTON, NeedleSize.SIZE_2_0, ProjectStatus.IN_PROGRESS);
        Recipe rNeedle = new Recipe("4", "u", "Match Title", "d", SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_3_0, ProjectStatus.IN_PROGRESS);
        Recipe rStatus = new Recipe("5", "u", "Match Title", "d", SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_2_0, ProjectStatus.FINISHED);
        Recipe rSearchMismatch = new Recipe("6", "u", "Other Title", "d", SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_2_0, ProjectStatus.IN_PROGRESS);

        List<Recipe> all = List.of(rMatch, rSkill, rYarn, rNeedle, rStatus, rSearchMismatch);

        sf.setText("Match");

        Method m = MyRecipesController.class.getDeclaredMethod("applyFilters", List.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Recipe> filtered = (List<Recipe>) m.invoke(controller, all);

        assertEquals(1, filtered.size(), "Only one recipe should match all filters");
        assertEquals(rMatch, filtered.get(0));
    }

    @Test
    void testLogOutButtonRedirectsToLogin(FxRobot robot) {
        robot.clickOn("#btnLogout");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Log out", stage.getTitle(),
                "Stage title should change to 'Log out' after clicking Log out button");

        assertNotNull(robot.lookup("#loginButton").query(),
                "Login.fxml should be loaded");

        assertEquals(null, SessionManager.getCurrentUser(),
                "Session should be cleared on logout");
    }

    @ParameterizedTest
    @EnumSource(ProjectStatus.class)
    void testThatCreateRecipeCardSwitchStatementWorksCorrectly(ProjectStatus status, FxRobot robot) {
        MyRecipesController myController = new MyRecipesController(recipeClient);
        Recipe testRecipe = new Recipe(
                "status_" + status.getLabel(),
                "userid",
                "some title",
                "some description",
                SkillLevel.BEGINNER,
                YarnType.WOOL,
                NeedleSize.SIZE_2_0,
                status);

        VBox card = myController.createRecipeCard(testRecipe);

        assertNotNull(card, "Card should not be null for project status: " + status.toString());

        HBox row = (HBox) card.getChildren().get(0);
        Label statusLabel = (Label) row.getChildren().get(2);
        assertEquals(status.getLabel(),
                statusLabel.getText(),
                "Card status should equal ProjectStatus status!");
    }

    @Test
    void testDetailsButtonOpensViewRecipe(FxRobot robot) {
        User user = new User("user1", "tester", "password");
        SessionManager.setCurrentUser(user);

        MyRecipesController myController = new MyRecipesController(recipeClient);
        Recipe recipe = new Recipe("r1", "user1", "Title1", "Desc1",
                SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_2_0, ProjectStatus.PLANNED);

        VBox card = myController.createRecipeCard(recipe);

        robot.interact(() -> {
            VBox recipesBox = robot.lookup("#recipesVerticalBox").queryAs(VBox.class);
            recipesBox.getChildren().add(card);
        });
        WaitForAsyncUtils.waitForFxEvents();

        HBox row = (HBox) card.getChildren().get(0);
        Button detailsButton = (Button) row.getChildren().get(4);

        robot.clickOn(detailsButton);
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(stage.getScene().getRoot().lookup("#titleLabel"),
                "ViewRecipe.fxml should be loaded and contain #titleLabel");
    }

    @Test
    void testFillAggregateMetrics_AllFinished() {
        MyRecipesController c = new MyRecipesController();
        // prepare labels via reflection
        try {
            java.lang.reflect.Field f1 = MyRecipesController.class.getDeclaredField("numRecipesLabel");
            f1.setAccessible(true);
            f1.set(c, new Label());
            java.lang.reflect.Field f2 = MyRecipesController.class.getDeclaredField("completionRatioLabel");
            f2.setAccessible(true);
            f2.set(c, new Label());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        Recipe r1 = new Recipe("r1", "user1", "T1", "D1", null, null, null, ProjectStatus.FINISHED);
        Recipe r2 = new Recipe("r2", "user1", "T2", "D2", null, null, null, ProjectStatus.FINISHED);
        Recipe r3 = new Recipe("r3", "user1", "T3", "D3", null, null, null, ProjectStatus.FINISHED);

        c.fillAggregateMetrics(Arrays.asList(r1, r2, r3));

        try {
            java.lang.reflect.Field f1 = MyRecipesController.class.getDeclaredField("numRecipesLabel");
            f1.setAccessible(true);
            Label numLabel = (Label) f1.get(c);
            java.lang.reflect.Field f2 = MyRecipesController.class.getDeclaredField("completionRatioLabel");
            f2.setAccessible(true);
            Label ratioLabel = (Label) f2.get(c);

            assertEquals("3", numLabel.getText(), "Total recipes should be 3");
            assertEquals("100%", ratioLabel.getText(), "Completion ratio should be 100% for all finished");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    void testFillAggregateMetrics_NoneFinished() {
        MyRecipesController c = new MyRecipesController();
        try {
            java.lang.reflect.Field f1 = MyRecipesController.class.getDeclaredField("numRecipesLabel");
            f1.setAccessible(true);
            f1.set(c, new Label());
            java.lang.reflect.Field f2 = MyRecipesController.class.getDeclaredField("completionRatioLabel");
            f2.setAccessible(true);
            f2.set(c, new Label());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        Recipe r1 = new Recipe("r1", "user1", "T1", "D1", null, null, null, ProjectStatus.PLANNED);
        Recipe r2 = new Recipe("r2", "user1", "T2", "D2", null, null, null, ProjectStatus.IN_PROGRESS);

        c.fillAggregateMetrics(Arrays.asList(r1, r2));

        try {
            java.lang.reflect.Field f1 = MyRecipesController.class.getDeclaredField("numRecipesLabel");
            f1.setAccessible(true);
            Label numLabel = (Label) f1.get(c);
            java.lang.reflect.Field f2 = MyRecipesController.class.getDeclaredField("completionRatioLabel");
            f2.setAccessible(true);
            Label ratioLabel = (Label) f2.get(c);

            assertEquals("2", numLabel.getText(), "Total recipes should be 2");
            assertEquals("0%", ratioLabel.getText(), "Completion ratio should be 0% when none finished");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    void testFillAggregateMetrics_EmptyList() {
        MyRecipesController c = new MyRecipesController();
        try {
            java.lang.reflect.Field f1 = MyRecipesController.class.getDeclaredField("numRecipesLabel");
            f1.setAccessible(true);
            f1.set(c, new Label());
            java.lang.reflect.Field f2 = MyRecipesController.class.getDeclaredField("completionRatioLabel");
            f2.setAccessible(true);
            f2.set(c, new Label());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        c.fillAggregateMetrics(Arrays.asList());

        try {
            java.lang.reflect.Field f1 = MyRecipesController.class.getDeclaredField("numRecipesLabel");
            f1.setAccessible(true);
            Label numLabel = (Label) f1.get(c);
            java.lang.reflect.Field f2 = MyRecipesController.class.getDeclaredField("completionRatioLabel");
            f2.setAccessible(true);
            Label ratioLabel = (Label) f2.get(c);

            assertEquals("0", numLabel.getText(), "Total recipes should be 0 for empty list");
            assertEquals("0%", ratioLabel.getText(),
                    "Completion ratio is NaN% for empty list with current implementation");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
