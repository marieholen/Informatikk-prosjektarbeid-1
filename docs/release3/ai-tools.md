# Ai usage in release 3 
As mentioned in earlier release: All members understand that it is acceptable to use AI to help perform tasks, provided that every solution is fully understood before it is submitted for review. Each member is expected to take ownership and responsibility for the solutions they deliver.  

The primary reason for using AI tools in our group was to increase efficiency and support our workflow. Given the limited time available, AI helped us accelerate certain tasks, especially when we needed a clarification on our work.

## AI-tools used
- ChatGPT
- Copilot
- Github Copilot
- Deepseek


## Some mentions of AI usage in release 3
- We had alot of checkstyle warnings indicating that several lines needed reduced indentation. Using AI to fix this was much faster than adjusting everything manually.

- In issue 187 (PR 188) It was used to plan the architecture for release 3, and served as a valuable sparring partner.

- In issue 225 (PR 285) It helped understand why `alert.showAndWait()` in `MyRecipesController` didn’t work, and why it needed to be `alert.show()` instead. 

- In issue 221 (PR 247) It was used to generate test code for frontend testing, and it performed very well in that area.

- In Issues 286, 287, and 289 PR (288, 294, and 293 ) AI was used to assist with improving the testability and structure of the codebase. Specifically, AI provided suggestions on how to make the RecipeClient/ApiClient and UserClient more test-friendly by introducing a default constructor for regular use and a specialized constructor to make it easier to mock HttpClient responses during testing.


