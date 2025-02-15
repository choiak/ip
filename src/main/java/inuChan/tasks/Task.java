package inuChan.tasks;

import java.io.FileWriter;
import java.io.IOException;

public class Task {
    private String name;
    private Boolean isMarked;

    public Task(String name) {
        this.name = name;
        isMarked = false;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsMarked() {
        return isMarked;
    }

    public void markTask(Boolean isMarked) {
        this.isMarked = isMarked;
    }

    public String formatForFile() {
        return "*]|[" + (isMarked ? "1" : "0") + "]|[" + name;
    }

    public void writeData(FileWriter fileWriter) throws IOException {
        fileWriter.write(formatForFile());
    }

    @Override
    public String toString() {
        return "[*]" + (isMarked ? "[X]" : "[ ]") + ": " + name;
    }
}
