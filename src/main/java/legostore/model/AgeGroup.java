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

    @Override
    public String toString() {
        return ageRange;
    }
}
