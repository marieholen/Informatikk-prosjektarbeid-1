# KnitSpace frontend

This is the frontend application for KnitSpace. 
- It is built with JavaFX and provides the graphical user interface that allows users to log in, create and view knitting recipes, upload PDFs and manage their recipe collection
- The frontend communicates with the KnitSpace backend through HTTP requests handled by UserClient and RecipeClient

Why we did not change the frontend framework:
We chose to keep the frontend in JavaFX rather than switching to a web framework because the existing JavaFX structure already integrates with the backend and meets the requirements of the project. Changing frameworks would require rewriting controllers, navigation, and UI components, introducing additional technology such as a local web server, and significantly increasing development time with limited benefit. 

To run the frontend:
1. cd knitspace-frontend
2. mvn javafx:run

The main directories within knitspace-frontend are inside the knitspace/ directory which contains:
- client/
    - Makes HTTP requests to the backend with RecipeClient and UserClient
- enums/
    - Shared value sets used to filter menus 
- meta/
    - Contains SessionManager that tracks the currently logged in user 
- model/
    - Record types that match backend data
- CreateAccountController.java
    - Controller for CreateAccount.fxml that registers a new user and logs them in
- EditRecipeController.java
    - Controller for EditRecipeView.fxml that updates/creates a recipe with an attached pdf
- LoginController.java
    - Controller for Login.fxml that logs users in and navigates them to MyRecipes.fxml
- MyRecipesController.java
    - Controller for MyRecipes.fxml where user can browse, filter and search recipes
- ViewRecipeController.java
    - Controller for ViewRecipe.fxml that displays recipe details and allows user to edit or delete recipe. Also allows user to open the pdf recipe

knitspace-frontend also contains module-info which defines the dependencies required by the frontend
- Requires javafx.controls, javafx.fxml and javafx.graphics to run the UI and load FXML
- Requires java.net.http for sending REST API requests to the backend
- Requires com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.annotation for converting JSON data to and from Java objects

Lastly, the pom.xml file includes:
- JavaFX dependencies
    - javafx-controls and javafx-fxml to run the graphical user interface
- JSON support
    - Jackson converts JSON responses from the backend into Java objects
- Testing libraries
    - JUnit for writing and running tests
    - Mockito for mocking dependencies in unit tests
    - TestFX for testing JavaFX user interface
    - Hamcrest for expressive test assertions
- Build plugins
    - javafx-maven-plugin allows the app to run using mvn javafx:run
    - maven-surefire-plugin runs test
    - checkstyle enforces consistent code formatting
    - spotbugs performs static code analysis to detect potential bugs
    - jacoco generates test coverage reports