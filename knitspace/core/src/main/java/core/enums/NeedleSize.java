package core.enums;

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

    NeedleSize(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
    return label;
    }

}