package core.enums;

public enum ProjectStatus {
    PLANNED("Planned", "Planned - not started yet"),
    IN_PROGRESS("In Progress", "In Progress - currently knitting"),
    PAUSED("Paused", "Paused - project on hold"),
    FINISHED("Finished", "Finished - project completed!"),
    ABANDONED("Abondoned", "Abandoned - unraveled and abandoned (for now...)");

    private final String description;

    ProjectStatus(String label, String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDisplayName() {
        String s = this.name().toLowerCase().replace("_", " ");
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
