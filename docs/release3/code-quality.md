# Code quality

## Style and readability

As in release 2, we used chackstyle in our mvn build. In release 2 we did not use it actively, but in release 3 this has been a large part of our development. This was a requirement for approval. The purpose of checkstyle was to improve the readability of our code, which eases development as agile development. Having no checkstyle warnings was a requirement for approval in our development.

## Testing

We have come a long way in our testing practices. This is further descried in our [Testing documentation](work-practices.md). Testing was a requirement for the approval of a feature implementation. 

The aim of this is to ensure that all features work as expected. This way we can systematically build confidence and verifiability in the performance of the application. In the future it will also help maintainability, so that we can easily discover and target weaker parts of the code base.

We tried to implement unit testing in this project. Unit testing is a method of testing where you check individual components of a program, typically small units like functions, methods, or classes, in isolation from the rest of the system. The idea is to confirm that each piece of code works exactly as intended before integrating it with other parts.

We have not implemented integration tests. Had we had more time on this project we would have done an effort to include integration testing. This too can help increase code-quality, as we can better 

## Documentation

Release 3 follows the google checkstyle requirements for javadocing. We dedicated some time in sprint 1 to completing our suite of javadocs.

For this release we have not set a target to add code documentation or Javadoc to any code. We aim to add more documentation in the next release.

Adding documentation will improve the agile development of the team by making the code understandable, usable, and maintainable for both current developers and anyone who might work on it in the future. In practice, it serves several goals. It communicates what the system does, how it is structured, and how it should be used, so that users can run it correctly and developers can build on it without guesswork.

## Error handling

Generally, we consider the controllers in the UI module to be satisfactory. The persistency-module, the one most prone to errors delivers its error to the user, so that it can be communicated to the users (and developers) through the UI.

The Core module is not very prone to errors.
