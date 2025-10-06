# Release documentation for release two
This is the second release of our project, KnitSpace. The main focus for this release was on modularization, three-layer architecture, data persistence, testing and documentation. 

KnitSpace now supports full user interaction, recipe management, and persistent storage using JSON files. Each user can register, log in, and manage their own collection of knitting recipes. 

## Features in this delivery
- User Management
    - Login and "New user" registration pages
    - Recipes are linked to the logged-in user with a unique userID
- Recipe Management
    - "My Recipes" page showing only the current user´s recipes.
    - Create, edit, view details, and delete recipes. 
    - Confirmation dialog before deleting a recipe
    - Filter recipes by project status, skill level, yarn type, and needle size. 
    - Search recipes by title
- Persistence
    - All user and recipe data stored as JSON-files ([`UserPersistence.json`](/knitspace/persistency/src/main/java/persistency/UserPersistence.java) and [`RecipePersistence.json`](/knitspace/persistency/src/main/java/persistency/RecipePersistence.java))
- Architecture

![alt text](/docs/release2/architecture.png)

    - For the mermaid source code of the architecture diagram, see [`docs/release2/architecture.mmd`](/docs/release2/architecture.mmd).
    - Implemented a three-layer structure:
        - `core` - domain logic and models
        - `persistence` - JSON file storage
        - `ui` - JAVAFX user interface
    - Maven manages dependencies across modules
- Testing and Code Quality
    - Unit tests across modules
    - JaCoCo for testing coverage


## Workflow
(More in depth description can be found in: [`teamwork.md`](../release2/teamwork.md), [`work-practices.md`](../release2/work-practices.md) and [`workflow.md`](../release2/workflow.md))
Our team divided the project work in release 2 into three sprints:

| Sprint | Duration | Focus |
|--------|-----------|--------|
| **Sprint 1** | Sep 15 – Sep 25 | Create FXML layouts and core classes. Complete base architecture and structure. |
| **Sprint 2** | Sep 25 – Oct 2 | Implement logic, controllers, persistence, and testing. Deliver a functional modular app. |
| **Sprint 3** | Oct 2 – Oct 6 | Final documentation, diagrams, and polishing. Complete all required documentation for Release 2. |

### Reflections
- Sprint 2 became too large and complex compared to Sprint 1. 
- Some tasks (editing recipes, filtering recipes by logged-in user and testing) were delayed to Sprint 3 due to time constraints. 
- The weekly Thursday meeting helped coordinate work and reflect on progress.
- Improved issue assignment reduced merge conflicts and confusion. 
- Team communication via Messenger was effective and maintained a low threshold for asking questions 

### Missing or Planned Features
- Uploading and attaching PDF files to recipes.
- Other future plans and reflections are discussed in [`teamwork.md`](../release2/teamwork.md). 


## Documentation
All documentation for Release 2 is located in `/docs/release2/`, including:
- [`workflow.md`](../release2/workflow.md) - sprint process and organization. 
- [`work-practices.md`](../release2/work-practices.md) - coding standards and collaboration approach. 
- [`code-quality.md`](../release2/codequality.md) - testing and quality tools. 
- [`teamwork.md`](../release2/teamwork.md) - reflection on collaboration. 
- [`ai-tools.md`](../release2/ai-tools.md) - use of AI tools.
- [`persistency.md`](../release2/persistency.md) - Discussion of persistency metaphors

Additional documentation: 
- [`REAME.md` in persistency](../../knitspace/persistency/README.md) - Data handling and JSON storage. 
- [`REAME.me`in core](../../knitspace/core/README.md) - Domain logic and main data models.
- [`REAME.md` in ui](../../knitspace/ui/README.md) - JavaFX UI structure and controllers.
- [`README.md` in source code](../../knitspace/ui/src/main/java/ui/README.md) - App features, user stories, and UI screenshots.
- [`README.md` in root](../../README.md) - App overview, technologies, and Maven setup.
