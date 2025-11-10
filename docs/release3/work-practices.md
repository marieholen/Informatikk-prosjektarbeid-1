# Work practices

## Version control

We used GitHub as our primary version control system. Each team member worked on branches, which were later merged into the main branch through pull requests. Each branch was usually only worked on by a single person and was associated with one issue. To keep the commit history clean we squash-merge each branch. This practice allowed us to separate ongoing development from stable code, and ensured that changes could be reviewed before integration. Commit messages were generally descriptive, and we use templates for both issues and pull requests to keep a consistent format.

## Communication

Our team primarily communicated through Messenger for quick updates and daily coordination. We held weekly meetings on Thursdays to align on progress, clarify requirements, and identify blockers. We used sprints to plan each week. For the previous releases we held some meetings on mondays to have stand-ups and host co-working sessions, but this was not done for release 3, as schedules were hard to match up. We allocated tasks by documenting their sizes and priority to spread out the work evenly. All the planning we did in the spring planning was documented in github as issues in a kanban board. This combination of synchronous and asynchronous communication allowed us to stay aligned while working on separate tasks.

When discussing our work-practices in release 2 we said that:

> We wish to have included larger discussions in GitHub issues, but this may not be doable for this project at all.

For release 3 we have done this some more. These is still room for improvement, but this has definitely improved. The benefit of this is improved documentation of our development reasoning.

## Testing practices

While testing is also covered in the [Code Quality](code-quality.md) section, here we reflect on how we integrated it into our workflow and development practices.

Testing has become a larger part of release 3 than the earlier releases. In release 2 we implemented a testing suite after all features were implemented. This is not a good testing practice, as faulty features may already have been implemented.

Therefore, we started doing a middle ground between test-driven development (TDD) and our solution in release 2. The testFX tests in the frontend project was architecture-agnostic, and already had complete testing. Therefore we were able to do TDD in these scenarios. In other parts of development, a sufficient test-suite was a prerequisite for approval. We practised this with a test-coverage requirement.

We we're very happy with this from a code-quality perspective, but the development is much slower and approvals may be more time consuming. This caused later problems with blockages and merge conflicts. This is further discussed in our documentaiton on [Challanges](challanges.md).

## Issue tracking

We used GitHub Issues and a project board to track tasks. Each feature or bug fix was associated with an issue, which was assigned to a responsible developer. Issues were labeled according to type (feature, bug, chore) and linked to pull requests, creating traceability between tasks and code changes. This helped us maintain a clear backlog and prioritize tasks within each sprint. We allocated tasks by documenting their sizes and priority to spread out the work.

## Sprint planning

We organized our work into one-week sprints. At the start of each sprint on thursdays, we held a planning meeting to define goals, break down tasks into issues, and assign responsibilities. At the end of the sprint, we reviewed progress against goals and held a small retrospective to discuss what worked well and what could be improved in our planning. In our sprint reviews we discovered that the different sprints we're of very varying size. For example, spring 1 was very small for most contributors, and sprint 3 was too large for a 1 week cycle.

Our goals per sprints were laid out as:

- sprint 1: release 3 setup
- sprint 2 & 3: recreating release 2 features in new architecture
- sprint 4: new features and docs
- sprint 5: polishing

We note that spring planning would have been more efficient in every sprint if we had a stable product (in the new architecture) at the start of the release. We have experienced that sprint planning is more suited for incremental development, and not total reworkings like release 3 has been. This is due to the course requirements. 

## Reflections on effectiveness

Overall, our work practices supported steady progress and collaboration. Version control and issue tracking worked as well as in previous releases, and sprint planning gave structure to our workflow. Though, sprint planning went smoother in release 2 due to the work being better suited. Communication was quite effective. Testing practices improved massively in this release, and there is still room for improvement.
