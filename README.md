# Project KnitSpace

This is group 29's project in IT1901. This repo contains a multi-module JavaFX application, modeled after the app [Knit and note](https://knitandnote.com/), an app to keep track of your knitting recipes.

[open in Eclipse Che](https://che.stud.ntnu.no/#https://git.ntnu.no/IT1901-2025-groups/gr2529)

NB: When running in a minimal environment like Eclipse Che online you might get errors when running all frontend tests, as the environment does not support opening pdfs via `java.awt.Desktop.Action.BROWSE`, as well as creating files using `java.nio.file.Files.createDirectories`-method because of missing permissions. If you want to run all tests except the failing one you can run `mvn test -Dtest='!EditRecipeControllerTest'` in `/knitspace/knitspace-frontend/`

## Technologies used
- [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [**Maven 3.9**](https://maven.apache.org/plugins/maven-compiler-plugin/download.cgi)
- [**JUnit 5.12.0**](https://docs.junit.org/5.12.0/release-notes/)
- [**JaCoCo 0.8.12**](https://www.jacoco.org/jacoco/)
- [**JavaFX 21**](https://openjfx.io/)
- [**Jackson 2.16.1**](https://github.com/FasterXML/jackson?tab=readme-ov-file)
- [**TestFX 4.0.18**](https://mvnrepository.com/artifact/org.testfx/testfx-core/4.0.18)
- [**Springdoc OpenAPI 2.5.0**](https://springdoc.org/)


## Purpose and functionality of the app
The purpose of Knitspace is to provide a login-based storage space for knitting recipes. Users can save all key project details, including Title, Description, Skill Level, Yarn, Needle Size, and Status, and attach a PDF (which may contain step-by-step knitting instructions). It offers full CRUD operations, allowing for the creation, editing, and management of all stored projects.

#### User interaction example - User has completed a project and wants to register project as completed:
1. User starts app and logs in
2. Overview of their recipes load. They click on "details" to inspect their recipe.
3. Once inspecting their recipe they click on "edit" to edit recipe details
4. User changes recipe project status from "In Progress" to "Finished"
5. Users logs out happily.

## Release documentation
All three releases of our application is documented in their own document. You can find them here:

[- Release 1 -](docs/release1/README.md)<br>
[- Release 2 -](docs/release2/README.md)<br>
[- Release 3 -](docs/release3/README.md)

## Modules
Knitspace consists of two main conponents: A JavaFX client (`knitspace-frontend`) and a Spring Boot REST server (`knitspace-backend`). You can read more about the two modules in their own README's:

[Backend documentation](knitspace/knitspace-backend/README.md)<br>
[Frontend documentation](knitspace/knitspace-frontend/README.md)

## Running the app
To run this app you have to first run and install the backend:

```bash
# Go to project root (if not already there):
cd knitspace/knitspace-backend

# Build the project (compile, test and package all modules):
mvn clean install

# Start the REST server:
mvn spring-boot:run
```

After the backend is up and running you can start the frontend from a new terminal as so:
```bash
#Go to project root (if not already there):
cd knitspace/knitspace-frontend

# Build the project (compile, test and package all modules):
mvn clean install

# Start the frontend JavaFX client:
mvn javafx:run
```

### Test data:
Our application comes with some example data for both recipes and users. They are located in `recipes.example.json ` and `users.example.json` in `/knitspace/knitspace-backend/data/`. If you wish to use them you simply have to rename them to `recipes.json` and `users.json`.

## Run tests
Our project uses the latest version of JUnit for testing (currently JUnit 5.12.2). To run tests follow the instructions below:

```bash
# Enter either knitspace-frontend or knitspace-backend and run:
mvn test
```


## Open JaCoCo test reports
This project uses JaCoCo version 0.8.12 to generate test coverage reports. After running the tests for the app you can open JaCoCo coverage reports by using one of the commands below. Replace `MODULE_NAME` with either `knitspace-frontend` or `knitspace-backend`.

```bash
# Make sure you are in knitspace directory!

# On windows:
start MODULE_NAME\target\site\jacoco\index.html

# On macOS:
open MODULE_NAME/target/site/jacoco/index.html

# On Linux:
xdg-open MODULE_NAME/target/site/jacoco/index.html
```
