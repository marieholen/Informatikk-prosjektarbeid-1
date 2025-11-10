## Release documentation for release one

## Overview
This is the initial release (Sprint 1) of our project. The prototype highlights the core domain layer, a JavaFX user interface, and a basic file-storage solution.

## Feature in this delivery 
- Basic JavaFX app that can be started with maven javafx:run
- A button that creates an empty file recipe.txt with feedback
- Created a test named testFileCreationOnButtonClick. The test checks whether recipe.txt already exists; if not, it creates a new empty file and verifies that the file has been successfully created. It returns false if the file still exists after being deleted, or if the file is missing after being created.
- JaCoCo set up, test coverage 32%
- Documentation added in [`root/docs/release1`](README.md).  
- Documentation about our usage of AI-tools: [`ai-tools.md`](ai-tools.md).  
- [`README.md` in root](../../README.md) with a short description of the application and Maven setup.  
- [`README.md` in source code](../release2/ui/knitspace/README.md) with details about the app functionality, user stories, and illustration screenshots.  

## Code quality

We use Jacoco for test coverage to ensure code quality. We have added testing for the `core` and `ui` logic layer.

To ensure cooperation and good communication within the teem we enforce issue/pull request templates. This gives the team structure.

We also tried to add protection rules to the main branch, but this Pull Request is backlogged because we cannot access NTNU resources.

## Future Plans
We had initially planned to use an issue board in this release, but it was set up incorrectly. We aim to use it more actively in future releases. 
