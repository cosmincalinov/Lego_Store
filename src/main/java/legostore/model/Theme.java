package legostore.model;

public enum Theme {
    CITY,
    STAR_WARS,
    TECHNIC,
    FRIENDS,
    CREATOR,
    NINJAGO,
    DUPLO,
    BOTANICAL;

    @Override
    public String toString() {
        String fmt = name().toLowerCase().replace('_', ' ');
        String[] words = fmt.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return sb.toString().trim();
    }

    public static Theme fromInput(String input) {
        String normalized = input.trim().toUpperCase().replace(' ', '_');
        return Theme.valueOf(normalized);
    }
}
