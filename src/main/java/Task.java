public class Task {
    private final Integer id;
    private String name;
    private Boolean isDone;

    public Task(Integer id, String name) {
        this.id = id;
        this.name = name;
        isDone = false;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void markTask(Boolean isDone) {
        this.isDone = isDone;
    }

    public void printTask() {
        if (isDone) {
            System.out.println(id + ".[X]: " + name);
        } else {
            System.out.println(id + ".[ ]: " + name);
        }
    }
}
