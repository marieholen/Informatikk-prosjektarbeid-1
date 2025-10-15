package core.enums;

/**
 * Represents the different knitting needle sizes that can be used in a knitting recipe. 
 * Each size corresponds to a spesific needle diameter in millimeters (mm)
 */

public enum NeedleSize {
    SIZE_2_0("2.0 mm") , 
    SIZE_2_5("2.5 mm") , 
    SIZE_3_0("3.5 mm") , 
    SIZE_3_5("3.5 mm") , 
    SIZE_4_0("4.0 mm") , 
    SIZE_4_5("4.5 mm") , 
    SIZE_5_0("5.0 mm") , 
    SIZE_5_5("5.5 mm") , 
    SIZE_6_0("6.0 mm") , 
    SIZE_6_5("6.5 mm") , 
    SIZE_7_0("7.0 mm") , 
    SIZE_7_5("7.5 mm") , 
    SIZE_8_0("8.0 mm") , 
    SIZE_8_5("8.5 mm") , 
    SIZE_9_0("9.0 mm");

    private final String label;

    /**
     * Constructs a {@code NeedleSize} with the specified label
     * 
     * @param label a short descriptive label for the needle size (size in mm)
     */
    NeedleSize(String label) {
        this.label = label;
    }

    /**
     * Returns the descriptive label of the needle size. 
     * 
     * @return the label representing this needle size. 
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the textual representation of the needle size. 
     * 
     * @return the label of the needle size. 
     */
    @Override
    public String toString() {
    return label;
    }

}