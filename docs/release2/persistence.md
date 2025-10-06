# Persistence Model

## Overview
When designing persistence for an application, there are generally two common approaches:

1. **Document metaphor (desktop model):**

    - Users explicitly create, open, save, and close documents. Users have full control over when and what gets saved.   
    - Persistence is manual and under user control. Adds friction to the user experience with extra save/load actions.  
    - Common in traditional desktop software such as text editors or graphics tools. 
    - Risk of data loss if users forget to save.  

2. **Implicit saving (app model):**

    - The application automatically saves data in the background. Smoother user experience, changes are always saved automatically, but provides less explicit control for users, which may cause confusion if they expect a “Save” option. Reduces risk of losing work due to forgetting to save. 
    - Common in modern mobile and web apps, where the focus is on continuous state rather than discrete files. Matches user expectations in modern mobile/web environments. Can make it harder to experiment safely without affecting the stored state.

## Implementation

For this project, we have chosen the **implicit saving (app model)** approach.  

In this project we use the implicit saving model. Data is automatically saved to JSON files whenever entities are added, removed, or updated. The user rarely interacts with the storage. This approach aligns with modern app-like behavior, reducing the risk of data loss by ensuring that user actions are immediately persisted, without requiring explicit save commands.

However, there is an element of explicit storage in this application, as recipies are viewed in pdfs, which have to be uploaded from the users computer. This is explicit storage, and different from how we treat the meta-data of recipes and users.
