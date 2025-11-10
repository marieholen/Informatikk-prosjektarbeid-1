# Workflow

## Development cycle

In release 3, we continued working in sprints that lasted one week, to break the project into smaller stages. Each cycle began with sprint planning every Thursday, where we identified goals, created and prioritized GitHub issues, estimated their size using verbal planning-poker, and assigned responsibilities. We worked on separate branches which were all related to specific issues. For each issue we submitted pull requests for review before merging into main. At the end of each sprint, we reviewed progress and made small adjustments based on what worked and what caused delays.

Our workflow was more aligned with agile practices in release 3, but we still experienced friction due to the architectural rewrite required. Release 3 involved large structural changes rather than continuous iteration and using a Scrum board was best suited for incremental work. This made the pacing of sprints uneven, with some sprints being significantly heavier than others.

## Branching strategy

We kept main as our stable branch, and all development happened in feature branches linked to GitHub issues. Each branch was squash-merged through a pull request to maintain a clean history. This strategy gave us structure, traceability, and control over code integration.

However, since the product was not stable at the start of release 3 due to the shift to a new architecture, maintaining a “stable” main branch became challenging. Slow pull-request reviews also created bottlenecks, occasionally delaying merges and causing unnecessary conflicts. Despite this, the branching strategy remained a core strength of our workflow and ensured clarity during development.

## Task flow

We strived for consistency in our task flow. At the start of each sprint meeting we discussed and created issues for all tasks and put them on our "Release 3" scrum board on Github. The task moved across the Scrum board—from sprint backlog -> TODO -> in progress -> in review -> done. We linked branches to issues and submitted pull requests once completed. Code reviews provided quality control, though the review delays sometimes caused blocks in the workflow. We

Even though the review delays caused stagnation in development, enforcing code reviews on every PR allowed each member to practice reviewing code, and increased each members understanding of the entire codebase.

## Testing in the workflow

Testing played a much larger role in release 3 than in release 2. While we did not fully adopt test-driven development, tests were required before pull requests could be approved. TestFX allowed us to use near test-driven development practices for the frontend, while backend features had coverage requirements that had to be met before merging.

This strengthened code quality but slowed development, as writing tests and waiting for reviews increased feedback time. Nonetheless, testing became a fixed step in our workflow rather than an afterthought, marking a clear improvement from previous releases.

## Releases

Release 3 mostly focused on upgrading architecture, improving code quality, increasing test coverage, and completing documentation. Because of these goals, much of the work involved re-implementing existing features rather than creating entirely new ones. This also meant that sprint pacing was affected by technical debt and refactoring complexity.

## Continuous improvement

At the end of each sprint, we reflected on the effectiveness of our workflow. The biggest improvements came from increasing documentation in GitHub issues and including testing into the development cycle. We also identified key challenges, such as underestimated tasks and slow review processes, and found that addressing them early helped reduce flow delays. While time estimation remained difficult, we improved communication and coordination throughout the release.