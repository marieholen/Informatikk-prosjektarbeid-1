# KnitSpace frontend

This is the frontend application for KnitSpace. 
- It is built with JavaFX and provides the graphical user interface that allows users to log in, create and view knitting recipes, upload PDFs and manage their recipe collection
- The frontend communicates with the KnitSpace backend through HTTP requests handled by UserClient and RecipeClient

---

## Keeping JavaFX
We chose to keep the frontend in JavaFX rather than switching to a web framework because the existing JavaFX structure already integrates with the backend and meets the requirements of the project. Changing frameworks would require rewriting controllers, navigation, and UI components, introducing additional technology such as a local web server, and significantly increasing development time with limited benefit. 

---

## Running the app
```bash
# Go to project root 
cd knitspace

# Build the project 
mvn clean install

# Go to frontend directory
cd knitspace-frontend

# Run the frontend 
mvn javafx:run
```

---

The main directories within knitspace-frontend are inside the `knitspace/` directory which contains:
- `client/`
    - Makes HTTP requests to the backend with RecipeClient and UserClient
    - If the backend is down or not accessible, or if there are any errors the client side handles that as well
- `enums/`
    - Shared value sets used to filter menus 
- `meta/`
    - Contains SessionManager that tracks the currently logged in user 
- `model/`
    - Record types that match backend data
- `CreateAccountController.java`
    - Controller for CreateAccount.fxml that registers a new user and logs them in
- `EditRecipeController.java`
    - Controller for EditRecipeView.fxml that updates/creates a recipe with an attached pdf
- `LoginController.java`
    - Controller for Login.fxml that logs users in and navigates them to MyRecipes.fxml
- `MyRecipesController.java`
    - Controller for MyRecipes.fxml where user can browse, filter and search recipes
- `ViewRecipeController.java`
    - Controller for ViewRecipe.fxml that displays recipe details and allows user to edit or delete recipe. Also allows user to open the pdf recipe

---

## Module info

knitspace-frontend also contains module-info which defines the dependencies required by the frontend. The application relies on JavaFX modules for its graphical interface: javafx.controls for UI components like buttons and lists, and javafx.fxml for loading the view layouts defined in FXML files. Furthermore, to send and receive HTTP requests, we use java.net.http to let the frontend performs actions such as login and recipe creation. Lastly, to convert JSON data to and from Java objects we use Jackson. com.fasterxml.jackson.core provides reading and writing of JSON files, com.fasterxml.jackson.databind converts JSON into Java objects and Java Objects to JSON and com.fasterxml.jackson.annotation provides annotations to control how fields are named

---

## Pom File

Lastly, the `pom.xml` file includes:
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