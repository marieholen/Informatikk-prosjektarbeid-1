## Persistency module

The persistency module handles saving and loading data for the app. It reads and writes both User and Recipe objects to JSON files which is important because it retains the data after the app is closed. These data files can be found in gr2529/data where you have both recipes.json and users.json.

UserPersistence handles User objects and includes the following functions:

- loadUsers()
- saveUsers()
- addUser()
- deleteUser()
- updateUser()

RecipePersistence handles Recipe objects and includes the following functions:

- loadRecipes()
- addRecipe()
- deleteRecipe()
- updateRecipe()
- findById()
- getAllRecipes()
- recipeExists()

The module-info requires Jackson to save and load data as JSON files. It also requires core as persistency methods use the User and Recipe classes defined in the core module. Lastly, the line "exports persistency" lets all other modules use UserPersistence and RecipePersistence.