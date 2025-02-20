package inuChan.tasks;

public class Event extends Task {
    private static final String TASK_CODE_EVENT = "E";
    private static final String SPLITTER = "]|[";

    String from;
    String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String formatForFile() {
        return super.formatForFile().replaceFirst("\\*", TASK_CODE_EVENT) + SPLITTER + from + SPLITTER + to;
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("\\*", TASK_CODE_EVENT) + " (" + from + " -> " + to + ")";
    }
}
