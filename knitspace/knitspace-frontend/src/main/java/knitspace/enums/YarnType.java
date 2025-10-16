package core.enums;
/**
 * Represents the different types of yarn materials that can be used in a recipe or project.
 */
public enum YarnType {
    WOOL("Wool") , 
    COTTON("Cotton"),
    LINEN("Linen"),
    CASHMERE("Cashmere"),
    ALPACA("Alpaca"),
    MERINO("Merino"),
    ACRYLIC("Acrylic");

    private final String label;

    /**
    * Constructs a {@code YarnType} with the specified label.
    * 
    * @param label a short descriptive label for the yarn type
    */
    YarnType(String label) {
        this.label = label;
    }

    /**
    * Returns the decriptive label of the yarn type.

    * @return the label representing this yarn type
    */
    public String getLabel() {
        return this.label;
    }
/**
 * Returns the textual representation of the yarn type.
 * 
 * @return the label of this yarn type
 */
    @Override
    public String toString() {
        return label;
    }
}
