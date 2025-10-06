# Workflow

## Development cycle

Our workflow followed a simplified agile approach with one-week sprints. Each cycle began with sprint planning, where tasks were identified, prioritized, size-estimated, and created as GitHub issues. Developers then picked up issues, worked on branches for each issue, and submitted pull requests to merge into the main branch. At the end of each sprint, we reviewed progress and reflected slightly on improvements for the next cycle.

The consepts we have taken from agile development, like KanBan boards and sprint planning is not as compatible with our development methodology of "Waterfall method" where we plan like: requirements → design → implementation → testing → deployment → maintenance. This was useful for the beginning of the project where we had no meaningful codebase, but for the next release we will shift from a waterfall methodology to adapt more of a agile method. This will help our workflow.

## Branching strategy

We used Git as our version control system, with `main` representing the stable version of the application. New features and bug fixes were developed in separate branches, which were merged through pull requests. This process gave us a clear separation between ongoing development and stable releases, while making it easier to manage code reviews and resolve conflicts.

Treating the main branch as the default "stable" branch for this release was somewhat diffucult, because the product has never been stable yet, as is in too early of a stage in production. Therefore our branching strategy and sprint planning will be more purposeful in future releases of the product. This relates to our discussion of the waterfall method above.

## Task flow

Each feature or bug started as a GitHub issue, was assigned to a developer, and linked to a branch. Once the implementation was complete, the developer opened a pull request. Another team member reviewed the changes, and after approval the branch was merged into `main`. This created a consistent path from task creation to completed code in the repository.

## Testing in the workflow

Testing was integrated into the workflow using Maven’s testing framework. Although tests were not always written before code (test-driven-development), we ran tests before merging branches to reduce the risk of introducing fallbacks. Over time, testing became a more regular step in the cycle, particularly from Sprint 3 onwards.

## Releases

The project is organized into 3 releases. These have different goals, and for this release we focused on creating a functioning application to optimize development in preperation for release 3.

## Continuous improvement
At the end of each sprint, we held retrospectives to reflect on how the workflow functioned. We identified bottlenecks (for example, slow reviews or underestimated tasks) and experimented with adjustments in the following sprint. This iterative approach improved our ability to estimate time, integrate testing earlier, and communicate about dependencies.
