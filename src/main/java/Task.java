public class Task {
    private Integer id;
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

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public void printTask() {
        if (isDone) {
            System.out.println(id + ".[X]: " + name);
        } else {
            System.out.println(id + ".[ ]: " + name);
        }
    }
}
