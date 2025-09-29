package core.enums;

public enum ProjectStatus {
    PLANNED("Planned", "Planned - not started yet"),
    IN_PROGRESS("In Progress", "In Progress - currently knitting"),
    PAUSED("Paused", "Paused - project on hold"),
    FINISHED("Finished", "Finished - project completed!"),
    ABANDONED("Abondoned", "Abandoned - unraveled and abandoned (for now...)");


    private final String label;
    private final String description;

    ProjectStatus(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescription() {
        return this.description;
    }

}
