# Project KnitSpace

[open in Eclipse Che](https://che.stud.ntnu.no/#https://git.ntnu.no/IT1901-2025-groups/gr2529)

The purpose of this application is to have a storage space for all your knit work. In this application it’s possible to sort your recipes based on skill level, types of clothing, size of knitting needles, yarn types. There will also be an overview to track your knitting projects (to do, in progress, done).

The source code is in the `core` and `ui` folders within the `knitspace` folder.

Maven setup for Java 17+ and JavaFX 21, and JUnit 5 (Jupiter) and TestFX for testing.

## Trying it out

This project can be tried out by cd-ing into the `knitspace/ui` folder and using `mvn`:

- Since we are using the multi-module template run `mvn clean install` to allow local dependencies to be available
- compile with `mvn compile`
- test with `mvn test`
- run quality checks with `mvn verify`
- run with `mvn javafx:run`
