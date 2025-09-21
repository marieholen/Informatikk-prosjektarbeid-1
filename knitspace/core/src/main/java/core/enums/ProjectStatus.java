package core.enums;

public enum ProjectStatus {
    PLANNED("Planned - not started yet"),
    IN_PROGRESS("In Progress - currently knitting"),
    PAUSED("Paused - project on hold"),
    FINISHED("Finished - project completed!"),
    ABANDONED("Abandoned - unraveled and abandoned (for now...)");

    private final String description;

    ProjectStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
