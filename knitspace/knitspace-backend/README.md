# KnitSpace backend

This is the backend service that supports the Knitspace application. The backend exists to:
- Maintain a consistent data model for users and recipes 
- Provide API endpoints that the frontend can call
- Apply logic such as validation and update rules
- Store data in JSON files

## Running the app
```bash
# Go to the backend directory (if user is not in the knitspace-backend, the user can't build or run the backend)
cd knitspace/knitspace-backend

# Build the project 
mvn clean install

# Run the backend 
mvn spring-boot:run
```
---

## Main Directories
- `data/`
- `src/`

## `src/`
Within src are the main classes for the backend which are: `config/`, `controller/`, `enums/`, `model/`, `repository/` and `service/`. Furthermore, there are tests for the controller and service classes which ensures that the API endpoints behave as expected and that rules work correctly in isolation.

### `config/`
Includes 'SwaggerConfig.java' containing Swagger API documentation that displays all our endpoints in a web browser with the following link: http://localhost:8080/swagger-ui/index.html

### `controller/`
Defines REST API endpoints. Translates HTTP requests into service calls and returns structured responses.

### `service/`
Contains the logic on how data should be created, validated, updated or removed.

### `repository/`
Handles data persistence which is stored in the JSON files in the data directory.

### `enums/`
Contains the following predefined value sets used in recipes: 
- NeedleSize
- ProjectStatus 
- SkillLevel 
- YarnType

### `model/`
Contains the core data structures used throughout the backend: 
- User
- Recipe

---

## `data/`
The data directory contains `users.json` and `recipes.json` which is where user-created users and recipes are stored. 

The `users.json` stores: userId, username and password, which are all needed to validate a user and give each user their own personalised recipes. 
The `recipes.json` stores: recipeId, userId, title, description as well as the different enums, which separates the recipes from each other and find recipes more easily.

The data directory also contains the pdfs directory which stores the pdf of the recipe. The pdf file name is the recipeId of the given recipe with .pdf at the end.

---

## `pom.xml`
The backend is built and managed using Maven. The `pom.xml` file:
- Defines dependencies such as Spring Boot, Lombok, Jackson, and Swagger UI
- Configures build plugins, including Checkstyle and JaCoCo test coverage

--- 

## REST API
The backend includes a REST API used by the JavaFX frontend. The REST API includes the following endpoints:
- `RecipesController`
    - PUT `/api/recipes/{recipesId}`: Updates an existing recipe with new field values
    - DELETE `/api/recipes/{recipesId}`: Deletes the recipe with the given recipeId
    - POST `/api/recipes`: Creates a new recipe and saves it 
    - GET `/api/recipes/user/{userId}`: Returns all recipes belonging to the user
- `UserController`
    - POST `/api/users/login`: Authenticates a user using username and password
    - POST `/api/users/create`: Creates a new user