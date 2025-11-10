# Release documentation for release 3
This is the third release of our project, **KnitSpace**. The main focus in this release has been to introduce a proper client-server architecture using a REST API, improve modularity, increase code quality, and expand documentation. 

The application is now split into a JavaFX client and a Spring Boot backend. Communtication between the two is performed over HTTP using JSON, which enables a more scalable and flexible architecture. 

## Features in this delivery

### Architecutre improvements
- The application is now divided into:
    - A **Spring Boot backend** exposing REST endpoints
    - A **JavaFX client** communicating with the backend using HTTP/JSON
- Three-layer backend design:
    - Controller layer (REST endpoints)
    - Service layer (business logic)
    - Repository layer (storage handling)
- Recipe data is now fetched, stored, updated and deleted through the REST API. 


### Recipe Functionality and IU improvements
- Full CRUD operations for recipes executed via REST API
- Redirects and improved user flow between views 
- Improved UI responsiveness and consistency after the architectural changes
- New UI feature: total recipe count shown in *MyRecipes*
- Added support for opening a PDF linked to a recipe.
- Filtering and searching functionality maintained from Release 2


### Quality improvements
- Refactor controllers and shared logic to support REST
- Added missing button logic and fixed `onAction` issues
- Checkstyle and Spotbugs warnings resolved
- Improved consistency in enums and shared domain models
- Javadoc documentation added widely across the codebase
- Expanded tests and JaCoCo coverage

### Documentation
- Updated READMEs in:
    - Root directory
    - Backend module
    - Frontend module
- REST API documented (supported endpoints and example responses)
- Additional required documentation in release 3 added:
    - `ai-tools.md`
    - `teamwork.md`
    - `work-practices.md`
    - `code-quality.md`
    - `workflowy.md`
    - `sustainability.md`
    - `contribution.md`
    - `challenges.md`
    - Class, package and sequence diagrams

## Workflow
We continued to follow an issue-driven workflow and organized the work into sprints: 

| Sprint | Duration | Focus | Highligthts |
|--------|------------|--------|----------|
| **Sprint 1** | Oct 6 – Oct 16 | Documentation and quality setup | Javadoc added across project, SpotBugs fixes, initial REST project structure prepared |
| **Sprint 2** | Sep 16 – Oct 23 | Migrate to REST + major feature logic | CRUD refactor, navigation improvements, filtering/search UI and backend endpoints |
| **Sprint 3** | Oct 23 – Oct 30 | Feature polish + PDF support | PDF endpoint and functionality, recipe count, bug fixes | 
| **Sprint 4** | Oct 30 – Nov 06 | Documentation and diagrams | Created all required diagrams + updated READMEs + sustainability/AI-tools/challenges/contribution docs |

The remaining time before the due was supposed to be a buffer to go through the entire project.

### Sprint Reflections
- The REST migration took longer than expected and impacted many UI files. 
- Improved workflow with issues, branches and PRs made collaboration smoother and more structured. 
- Code reviews and Checkstyle helped maintain consistent code quality. 

More detailed workflow reflections can be found in [`teamwork.md`](../release3/teamwork.md), [`work-practices.md`](../release3/work-practices.md) and [`workflow.md`](../release3/workflow.md). 


## Documentation Overview

All documentation for Release 3 can be found in `/docs/release3/`:
- [`teamwork.md`](../release3/teamwork.md) - reflection on collaboration. 
- [`work-practices.md`](../release3/work-practices.md) - coding standards and collaboration approach. 
- [`code-quality.md`](../release3/code-quality.md) - testing and quality tools.
- [`workflowy.md`](../release3/workflow.md) - Git workflow and organization.
- [`ai-tools.md`](../release3/ai-tools.md) - reflection on AI usage ine the project.
- [`sustainability.md`](../release3/sustainability.md) - sustainability reflection. 
- [`contribution.md`](../release3/contribution.md) - each member´s contribution summery. 
- [`challenges.md`](../release3/challenges.md) - key obstacles and learning. 

#### UML diagrams
- [`class-diagram.svg`](../release3/figures/class-diagram.svg) - key classes and relationships in Release 3. 
- [`package-diagram.png`](../release3/figures/package-diagram.png) - documentation of main package structure. 
- [`sequence-diagram.png`](../release3/figures/create-recipe_sequence-diagram.png) - interaction flow for a core use case. 

#### Additional READMEs and API docs
- [`README.md` in frontend module](../../knitspace/knitspace-frontend/README.md) - describes structure, views, and key controllers. 
- [`README.md` in backend module](../../knitspace/knitspace-backend/README.md) - describes layers, key components, and the REST API endpoints. 
- [`README.md` in root](../../README.md) - App overview, technologies, and Maven setup.

## Regarding runnable product in `run.ps1`

We only noticed the requirement for the runnable product right before the deadline, so this method, as described in the root-level readme mihgt be unstable or wrong. This has not been tested sufficiently.