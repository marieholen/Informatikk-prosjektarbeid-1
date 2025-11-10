# Team Coordination and Project Experience
In release 3, we continued focusing on agile development, mainly following the Scrum methodology. 

## Project Experience

### Scrum
Due to conflicting schedules, we did not have the capacity to meet daily. Therefore, we did not conduct Daily Scrums. Instead, we communicated through Messenger group whenever someone was stuck or needed help. We met physically every Thursday between 12:00–15:00 for focused team alignment.

In Release 3, we planned four one-week Sprints (09.10.25–10.11.25). Because the release deadline was close, we chose shorter Sprints to increase the cadence of inspect-and-adapt cycles. However, there were miscalculations regarding the time estimates for the sprints, which caused us to finish later than expected. We wanted to define all issues for the product backlog at the start of release 3, but due to uncertainty and time pressure during the weekly meetings on Thursdays, this was instead done continuously throughout the release.

Planned Sprint schedule: 
- Sprint 1: 09.10.25–16.10.25
- Sprint 2: 16.10.25–23.10.25
- Sprint 3: 23.10.25–30.10.25
- Sprint 4: 30.10.25–06.11.25
- The remaining time before the due was supposed to be a buffer to go through the entire project.

Our overall release goal was to complete the work by 06.11.25 to allow time for inspection of the increment and overall quality assurance. Due to delays caused by estimation errors and slow code review turnaround time, we were not able to meet this goal.

Based on feedback from Release 2, insights from Sprint Retrospective, and discussions with our teaching assistant (Knut, Product Owner), Sprint 1 was prioritized around technical debt reduction work, mainly removing checkstyle warnings and adding Javadocs to improve maintainability.

Following the Scrum framework, we emphasized adaptability and responsiveness to change. By allowing the Product Owner to adjust the Product Backlog based on new insights, we increased our ability to deliver value that aligns with end-user needs.

In Sprint 2, we attempted a significant architectural refactoring by moving UI logic into a separated frontend–backend structure. The complexity of this work was higher than expected, and therefore consumed a large portion of Sprint 3 as well. Combined with slow pull request reviews, this resulted in cascading delays throughout the release.

Since we already had implemented many of the planned features by the end of release 2, we decided to focus mainly on improving code quality in Sprint 3. We still added a few new features, but the main priority shifted towards refactoring, maintainability and overall code quality.

Sprint 4 was planned to be fully dedicated to documentation work, as it was considered distributable and feasible to complete individually.

We created a new Scrum board on GitHub to visualize and organize our issues systematically, moving them through the workflow as needed. The issue board included the following columns: Product Backlog, Sprint Backlog, In Progress, In Review, and Done. 

## Team Coordination

### Roles
- Elias – Developer, tester and Scrum Master
- Aleksander – Developer, tester and responsible for Github
- Carl – Developer, tester
- Cathrine – Developer, tester, Scrum Master jr.
- Ella – Developer, tester and designer
- Marie – Developer, tester

### Issue Distribution and Planning

Issues were prioritized based on importance. The higher the priority, the sooner the issue needed to be completed. Since many issues depended on one another, the high-priority tasks had to be completed first. Team members assigned issues to themselves until all tasks were taken.

When estimating issues, we continued used verbal planning poker. We discussed the estimated size of each issue in T-shirt sizes. If there were disagreements, the team members who disagreed could explain their reasoning before we voted again. The challenge with verbal planning poker is that people can be easily influenced by others’ opinions, but given our limited time, we felt this was the best approach for us.

### Improvements for Future Collaboration
Throughout the project, we learned the importance of clear communication and early feedback in order to avoid misunderstandings. Although our pull requests could have been handled faster to reduce merge conflicts, we gradually improved our collaboration workflow.

We learned from the previous release that all issues for an ongoing Sprint should be assigned while the whole team is present, to ensure a fair and balanced distribution of work.

Since this was our first experience using Scrum, time estimation was difficult. However, we believe that with repeated practice and more Sprint cycles, our ability to estimate effort and use our time effectively will improve.