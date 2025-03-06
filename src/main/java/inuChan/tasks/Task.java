package inuChan.tasks;

import java.io.FileWriter;
import java.io.IOException;

public class Task {
    private static final String TASK_CODE_WILDCARD = "*";
    private static final String SPLITTER = "]|[";
    private static final String MARKED = "X";
    private static final String NOT_MARKED = " ";

    private final String name;
    private Boolean isMarked;

    public Task(String name) {
        this.name = name;
        isMarked = false;
    }

    public Boolean getIsMarked() {
        return isMarked;
    }

    public void markTask(Boolean isMarked) {
        this.isMarked = isMarked;
    }

    public boolean isContain(String target) {
        return toString().contains(target);
    }

    /**
     * Convert task into string in the format that is expected for writing to file.
     *
     * @return String for writing to file.
     */
    protected String formatForFile() {
        return TASK_CODE_WILDCARD + SPLITTER + (isMarked ? MARKED : NOT_MARKED) + SPLITTER + name;
    }

    /**
     * Write the task to file with the given file writer.
     *
     * @param fileWriter The file writer to write to.
     * @throws IOException If there is an error writing to the file.
     */
    public void writeData(FileWriter fileWriter) throws IOException {
        fileWriter.write(formatForFile());
    }

    @Override
    public String toString() {
        return "[" + TASK_CODE_WILDCARD + "]" + (isMarked ? "[X]" : "[ ]") + ": " + name;
    }
}
