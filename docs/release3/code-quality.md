# Code quality

## Style and readability

As in release 2, we used chackstyle in our mvn build. In release 2 we did not use it actively, but in release 3 this has been a large part of our development. This was a requirement for approval. The purpose of checkstyle was to improve the readability of our code, which eases development as agile development. Having no checkstyle warnings was a requirement for approval in our development.

## Testing

We have come a long way in our testing practices. This is further descried in our [Testing documentation](work-practices.md). Testing was a requirement for the approval of a feature implementation. This made all our feature implementations more suited for testing in the first place, since they were made in parallel with their own tests.

The aim of this is to ensure that all features work as expected. This way we can systematically build confidence and verifiability in the performance of the application. In the future it will also help maintainability, so that we can easily discover and target weaker parts of the code base.

We tried to implement unit testing in this project. Unit testing is a method of testing where you check individual components of a program, typically small units like functions, methods, or classes, in isolation from the rest of the system. The idea is to confirm that each piece of code works exactly as intended before integrating it with other parts.

In our test coverage we ave aimed at maximal test-coverage, but been aware that test coverage is not a direct measure of code-quality. We have therefore often been more focused on testing the consequential tests of the projects. For example, the backend is more thoroughly tested. Apart from this, the repository classes in the backed do not need testing to the same degree, as they have been implemented with Lombok automatically.

We have not implemented integration tests. Had we had more time on this project we would have done an effort to include integration testing. This too can help increase code-quality, as we can better 

## Documentation

Release 3 follows the google checkstyle requirements for javadocing. We dedicated some time in sprint 1 to completing our suite of javadocs.

Adding documentation will improve the agile development of the team by making the code understandable, usable, and maintainable for both current developers and anyone who might work on it in the future. In practice, it serves several goals. It communicates what the system does, how it is structured, and how it should be used, so that users can run it correctly and developers can build on it without guesswork.

## Input Validation

We have included some input validation in the controllers of the fxml files. This is done most in the login and signup pages, where the format of the password is somewhat controlled. We also have more extensive input validation in our frontend clients. We have used HTTP status codes as validation in the frontend on our API calls.

## Architecture principles

In release 3 we have followed 3 architecture patterns: 

- REST-API
- Repository pattern
- CRUD

Most of these patterns have mainly affected the backend project and the API calls, as described in the Swagger documentation.

REST-APIs have been a requirement for release 3 in this project. This has generally increased code quality and seperation of conserns in the code base. The effort for this has been valuable for our team, and we really wish that we had implemented this in release 2 as well earlier on.

Repository pattern provides a separation between business logic and data access by abstracting how data is stored in the repository classes. It makes the code maintainable, testable, and eases future implementations. For testing purposes is also allows you to mock classes without affecting the rest of the application, which fulfulls our focus on seperation of conserns.

CRUD consists o four basic operations:

- Create
- Read
- Update
- Delete

They define how data can be operated on in a system. It provides a consistent structure for interacting with data, making applications easier to design, understand, and maintain by standardizing how information is treated. We have two classes, and we have followed the CRUD pattern more thoroughly in the pipeline for the Recipe-class.

We would also like to reflect on one part of our projects that does not follow RESTful principles. **Our treatment of PDFs**. We use PDFs as the content of our recipes. Ideally they are stored in the backend project, and retrieved as PDF objects in the frontend Restfully from the backend through an API. This is not done in our project. We retrieve the path of the PDF from the backend, and we open the PDF directly in the frontend. In other words, the PDFs might as well be stored in the backend, and this is not completely restfull or in CRUD style. This was not ideal, but was done because of a time constraint late in the project. We consider this our largest blemish in the code quality of the project.