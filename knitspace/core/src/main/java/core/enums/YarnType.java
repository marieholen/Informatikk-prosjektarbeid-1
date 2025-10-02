package core.enums;

public enum YarnType {
    WOOL("Wool") , 
    COTTON("Cotton"),
    LINEN("Linen"),
    CASHMERE("Cashmere"),
    ALPACA("Alpaca"),
    MERINO("Merino"),
    ACRYLIC("Acrylic");

    private final String label;

    YarnType(String label) {
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
