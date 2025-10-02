package core.enums;

public enum SkillLevel {
    BEGINNER("Beginner"),
    EASY("Easy"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    EXPERT("Expert");

    private final String description;

    SkillLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return description; 
    }
}

