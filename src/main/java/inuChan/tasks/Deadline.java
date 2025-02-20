package inuChan.tasks;

public class Deadline extends ToDo {
    private static final String TASK_CODE_DEADLINE = "D";
    private static final String SPLITTER = "]|[";

    String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override
    public String formatForFile() {
        return super.formatForFile().replaceFirst("T", TASK_CODE_DEADLINE) + SPLITTER + by;
    }
    @Override
    public String toString() {
        return super.toString().replaceFirst("T", TASK_CODE_DEADLINE) + " (by " + by + ")";
    }
}
