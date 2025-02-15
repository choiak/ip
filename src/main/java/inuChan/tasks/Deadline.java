package inuChan.tasks;

public class Deadline extends ToDo {
    String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override
    public String formatForFile() {
        return super.formatForFile().replaceFirst("T", "D") + "]|[" + by;
    }
    @Override
    public String toString() {
        return super.toString().replaceFirst("T", "D") + " (by " + by + ")";
    }
}
