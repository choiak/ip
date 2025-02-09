package inuChan.tasks;

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

    @Override
    public String toString() {
        return "[*]" + (isMarked ? "[X]" : "[ ]") + ": " + name;
    }
}
