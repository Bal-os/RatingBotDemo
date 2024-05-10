package os.balashov.ratingbot.core.likesrating.votes.ports.entities;

public enum Marks {
    DISLIKE("dislike"),
    LIKE("like");

    private final String data;
    private String symbol;

    Marks(String data) {
        this.data = data;
    }

    public static Marks from(String data) {
        for (Marks mark : Marks.values()) {
            if (mark.data().equals(data)) {
                return mark;
            }
        }
        throw new IllegalArgumentException("Unknown mark data: " + data);
    }

    public String data() {
        return data;
    }

    public String symbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
