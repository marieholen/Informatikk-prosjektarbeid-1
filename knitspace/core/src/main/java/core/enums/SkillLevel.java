package core.enums;
/**
 * Represents the different skill levels that can be assigned to a recipe or user.
 */
public enum SkillLevel {
    BEGINNER("Beginner"),
    EASY("Easy"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    EXPERT("Expert");

    private final String description;

/**
 * Constructs a {@code SkillLevel} with the specified description.
 * 
 * @param description a short textual description of the skill level
 */
    SkillLevel(String description) {
        this.description = description;
    }
/**
 * Returns the textual description of the skill level.
 * 
 * @return the description of this skill level
 */
    public String getDescription() {
        return this.description;
    }

/**
 * Returns the textual representation of the skill level instead of its enum name.
 * 
 * @return the description of this skill level.
 */
    @Override
    public String toString() {
        return description; 
    }
}

