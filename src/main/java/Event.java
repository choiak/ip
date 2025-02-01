public class Event extends Task {
    String from;
    String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("\\*", "E") + " (" + from + " -> " + to + ")";
    }
}
