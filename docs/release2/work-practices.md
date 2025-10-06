# Work practices

## Version control

We used GitHub as our primary version control system. Each team member worked on branches, which were later merged into the main branch through pull requests. To keep the commit history clean we squash-merge each branch. This practice allowed us to separate ongoing development from stable code, and ensured that changes could be reviewed before integration. Commit messages were generally descriptive, and we use templates for both issues and pull requests to keep a cosistent format.

## Communication

Our team primarily communicated through Messenger for quick updates and daily coordination. We held weekly meetings on thursdays to align on progress, clarify requirements, and identify blockers. We used sprints to plan each week and help somewhat regular meetings on mondays to have standups and identify new blockers. We allocated tasks by documenting their sizes and priority to spread out the work. This combination of synchronous and asynchronous communication allowed us to stay aligned while working on separate tasks.

We wish to have included larger discussions in GitHub issues, but this may not be doable for this project at all.

## Testing practices

While testing is also covered in the [Code Quality](code-quality.md) section, here we reflect on how we integrated it into our workflow. Testing became a stronger focus from Sprint 3 onwards, and we used Maven’s testing integration to run our JUnit tests regularly. However, our workflow did not fully incorporate test-driven development, and tests were often written after features were implemented. In future sprints, we plan to integrate testing earlier in the development cycle to catch regressions sooner.

## Issue tracking

We used GitHub Issues and a project board to track tasks. Each feature or bug fix was associated with an issue, which was assigned to a responsible developer. Issues were labeled according to type (feature, bug, chore) and linked to pull requests, creating traceability between tasks and code changes. This helped us maintain a clear backlog and prioritize tasks within each sprint. We allocated tasks by documenting their sizes and priority to spread out the work.

## Sprint planning

We organized our work into one-week sprints. At the start of each sprint on thursdays, we held a planning meeting to define goals, break down tasks into issues, and assign responsibilities. At the end of the sprint, we reviewed progress against goals and held a small retrospective to discuss what worked well and what could be improved in our planning. In our sprint reviews we discovered that our first sprint for release 2 was not comprehensive enough, and left too much work to be done in sprint 2, which we did not manage to complete in time.

The goal of sprint 1 was to complete the persistency module, core module and all fxml files. Then we used sprint 2 to write testing and the controllers for the ui module. We did this because the controllers were blocked by the goals of sprint 1. Then sprint 3 focused on the documentation for the delivery of release 2.

## Reflections on effectiveness

Overall, our work practices supported steady progress and collaboration. Version control and issue tracking worked well, and sprint planning gave structure to our workflow. Communication was effective but we could have used GitHub more effectively. Testing practices improved during the project, but we recognize that incorporating tests earlier would have increased confidence in new features. For future iterations, we aim to strengthen our documentation, and refine our testing process.
