package inuChan.tasks;

public class ToDo extends Task {
    private static final String TASK_CODE_TODO = "T";

    public ToDo(String name) {
        super(name);
    }

    @Override
    public String formatForFile() {
        return super.formatForFile().replaceFirst("\\*", TASK_CODE_TODO);
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("\\*", TASK_CODE_TODO);
    }
}
