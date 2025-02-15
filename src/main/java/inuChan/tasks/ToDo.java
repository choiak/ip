package inuChan.tasks;

public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String formatForFile() {
        return super.formatForFile().replaceFirst("\\*", "T");
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("\\*", "T");
    }
}
