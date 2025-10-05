# Project KnitSpace

This is group 29's project in IT1901. This repo contains a multi-module JavaFX application, modeled after the app [Knit and note](https://knitandnote.com/), an app to keep track of your knitting recipes.

[open in Eclipse Che](https://che.stud.ntnu.no/#https://git.ntnu.no/IT1901-2025-groups/gr2529)

## Technologies used
- [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [**Maven 3.14**](https://maven.apache.org/plugins/maven-compiler-plugin/download.cgi)
- [**JUnit 5.12.0**](https://docs.junit.org/5.12.0/release-notes/)
- [**JaCoCo 0.8.12**](https://www.jacoco.org/jacoco/)
- [**JavaFX 21**](https://openjfx.io/)
- [**Jackson 2.16.1**](https://github.com/FasterXML/jackson?tab=readme-ov-file)
- [**TestFX 4.0.18**](https://mvnrepository.com/artifact/org.testfx/testfx-core/4.0.18)


## Purpose and functionality of the app
The purpose of this application is to have a storage space for all your knit work. In this application it’s possible to sort your recipes based on skill level, types of clothing, size of knitting needles and yarn types. There will also be an overview to track your knitting projects (to do, in progress, done), and support for editing already existing recipes.

## Release documentation
__[- Release 1 -](docs/release1/README.md)  
[- Release 2 -](docs/release2/README.md)__

## Modules
Knitspace is split into three modules, you can read about them here:

[Core module](knitspace/core/README.md)<br>
[Persistency module](knitspace/persistency/README.md)<br>
[UI module](knitspace/ui/README.md)

## Running the app
```bash
# Go to project root (if not already there):
cd knitspace

# Build the project (compile, test and package all modules):
mvn clean install

# Enter ui folder to run JavaFX frontend:
cd ui

# Start app:
mvn javafx:run
```


## Run tests
Our project uses the latest version of JUnit for testing (currently JUnit 5.12.2). To run tests follow the instructions below:
```bash
# Go to project root (if not already there):
cd knitspace

# If you want to run all tests, do:
mvn test

# If you only want to run tests for a specific module, do one of the following:
cd core
cd persistency
cd ui

# Then run tests in folder of your choice by doing:
mvn test
```


## Open JaCoCo test reports
This project uses JaCoCo version 0.8.12 to generate test coverage reports. After running the tests for a specific module, either by running ```mvn test``` in knitspace directory or in one of the modules directories. You can open the test coverage report for module MODULE_NAME by doing:
```bash
# Make sure you are in knitspace directory!

# On windows:
start MODULE_NAME\target\site\jacoco\index.html

# On macOS:
open MODULE_NAME/target/site/jacoco/index.html

# On Linux:
xdg-open MODULE_NAME/target/site/jacoco/index.html
```