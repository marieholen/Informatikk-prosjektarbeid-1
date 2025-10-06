## UI module

The purpose of the ui module is to handle all user interaction and visuals. It displays data from the core module and allows users to view, create and edit recipes. The module features controller classes as well as fxml files which displays various aspects of our app.

For each fxml file we also have a controller class which has following function:
LoginController.java - Implements logic for Login.fxml that let's user log in or if they don't have an account, redirects user to create account
CreateAccountController.java - Implements logic for CreateAccount.fxml that let's user create an account and then be redirected to Login.fxml
EditRecipeController.java - Implements logic for EditRecipeView.fxml that let's user create or edit an already existing recipe. Also let's user upload a pdf of their recipe
MyRecipesController.java - Implements logic for MyRecipes.fxml that is the home page where a user can see all their recipes, filter them and log out
ViewRecipeController.java - Implements logic for ViewRecipe.fxml that let's view their recipe, delete it or edit it by being redirected to EditRecipeView.fxml

The UI module also has a module-info that depends on core and persistency, as core contains information related to the user and recipe, while persistency is needed for saving and loading recipes and users. Also contains javafx controls, fxml and graphics that allow us to display our intended user interface.