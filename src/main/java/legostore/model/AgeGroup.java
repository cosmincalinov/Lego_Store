package legostore.model;

public enum AgeGroup {
    TODDLER("1.5+"),
    CHILD("4+"),
    TEEN("13+"),
    ADULT("18+");

    private final String ageRange;

    AgeGroup(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public static AgeGroup fromInput(String input) {
        switch (input.trim().toLowerCase()) {
            case "toddler": return TODDLER;
            case "child":   return CHILD;
            case "teen":    return TEEN;
            case "adult":   return ADULT;
            default: throw new IllegalArgumentException("Unknown age group: " + input);
        }
    }

    @Override
    public String toString() {
        return ageRange;
    }
}