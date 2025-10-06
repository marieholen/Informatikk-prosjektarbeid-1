## Core module

The core module contains the logic for the application, which includes information related to User and Recipe. The file User.java represents an individual user where a unique UUID, username and password is given. The file Recipe.java represents a knitting recipe and includes attributes like title, description, skill level, yarn type, needlesize and project status. These types are stored in the enums folder.

Furthermore, to keep track of which user is logged in, we have SessionManager.java. This class is important as we need to know which user is currently logged in allowing us to see the active user.

Lastly we have module-info.java which defines what the core module depends and what it shares with other modules. module-info declares dependencies like the Jackson library which allows us to save objects as JSON and load them back when needed. module-info also exports core and core.enums making them available to other modules. 