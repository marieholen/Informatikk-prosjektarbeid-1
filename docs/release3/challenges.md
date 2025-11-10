# Challenges
## Sprint planning and estimation
One of our biggest challenges throughout the project was sprint-planning and time-estimation. Since several members in the group were new to Scrum and the technologies we used as REST-API, it was difficult to predict how long each issues would take. When reconstucting the project from three-layer-module, to a new architecture with seperated frontend and backend, we noticed that even though most features were implemented in release 2, the process was still much more time consuming than we predicted, due to handling dependencies, data flow, and communication between layers. 

Our first sprints were planned too optimistically, assuming that too many tasks could be completed within one-week cycles, while others were too small. In practice, several sprints overlapped because key issues were delayed or blocked by others. For example, some task in frontend depended on backend endpoints that were not implemented yet. During our planning meeting each thursday, we discussed and labeled issues to estimate their size. Unfortuntly, we sometimes underestimated the integration effort and review time required before merging. 

Over time, we improved by limiting sprint scope, defining smaller and more independent issues, and planning each sprint based on how much time team members had available that week. We also became better at identifying dependencies and potential blockers earlier in the process. 


## Reviewing pull requests
Due to different levels of experience in the group when it came to Git and code handling, it was often those who had the most control who ended up doing most of the code reviews. Those who were less confident tended to hold back because they were afraid of approving something incorrectly. This caused pull request reviews to take unnecessarily long time, which again led to delays in tasks that depended on them, some issues were blocked and could not move forward.

This was brought up in a meeting, and we agreed that more people needed to step up and take reviews. Because we worked during different time periods, there were still several merge conflicts when reviews were deprioritized and new issues were completed in the meantime.
Eventually, we found a good solution: we created a “recipe” describing how to test the features in the pull request. This was added to the PRs, and made more people feel confident about doing reviews. The result was faster reviews and fewer mistakes.

Our learning for next time is that frequent reviews must be prioritized early to create better workflow and avoid merge conflicts in main. Additionally, a shared test recipe should be established so that everyone can test functionality in a simple and standardized way.