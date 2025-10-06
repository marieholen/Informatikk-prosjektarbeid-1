# Code quality

## Style and readability

We have added checkstyle to our mvn build, and recognize the checkstyle errors, but we have not focused on fixing these warnings for this release. This is due to a time constraint, and we will prioritize this in the next release.

The purpose of checkstyle was to improve the readability of our code, which makes development

## Testing

We started implementing comprehensive testing in sprint 3 of release 2. The aim of this is to ensure that all features work as expected. This way we can systematically build confidence and verifiability in the performance of the application.

In the future it will also help maintainability, so that we can easily discover and target weaker parts of the code base.

We tried to implement unit testing in this project. Unit testing is a method of testing where you check individual components of a program, typically small units like functions, methods, or classes, in isolation from the rest of the system. The idea is to confirm that each piece of code works exactly as intended before integrating it with other parts.

But we have not been entirely successful in our implementation, as the persistency module is static, and therefore not passed as arguments to the controller functions. This results in that we are not able to mock the persistency layer when testing the controllers, and the controller tests are therefore not isolated to the controller class. This is not in accordance with the practice of unit testing and will have to be fixed in future releases.

## Documentation

For this release we have not set a target to add code documentation or Javadoc to any code. We aim to add more documentation in the next release.

Adding documentation will improve the agile development of the team by making the code understandable, usable, and maintainable for both current developers and anyone who might work on it in the future. In practice, it serves several goals. It communicates what the system does, how it is structured, and how it should be used, so that users can run it correctly and developers can build on it without guesswork.

## Error handling

Generally, we consider the controllers in the UI module to be satisfactory. The persistency-module, the one most prone to errors delivers its error to the user, so that it can be communicated to the users (and developers) through the UI.

The Core module is not very prone to errors.
