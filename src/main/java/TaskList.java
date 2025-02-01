public class TaskList {
    private final Integer capacity;
    private Task[] list;
    private Integer taskCount;

    public TaskList() {
        capacity = 100;
        list = new Task[capacity];
        taskCount = 0;
    }

    public Task getTask(Integer id) {
        return list[id - 1];
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    /**
     * Add a new task to the list.
     * Return the task count.
     * Return -1 if the list is full.
     *
     * @param task The task to be added.
     * @return Task count, -1 if the list is full.
     */
    public Integer addTask(Task task) {
        if (taskCount < capacity) {
            list[taskCount] = task;
            taskCount++;
            return taskCount;
        } else {
            return -1;
        }
    }

    /**
     * Mark the task of the given id as the required state.
     * Return the task id.
     * Return negative value if the id is invalid or the task is already in the required state.
     *
     * @param id The id of the task.
     * @param isDone The required state of the task.
     * @return Task id, -1 if the id is invalid, -2 if the task is already in the required state.
     */
    public Integer markTask(Integer id, Boolean isDone) {
        if (id < 1 || id > taskCount) {
            return -1;
        } else if ((isDone && list[id - 1].getIsMarked()) || (!isDone && !list[id - 1].getIsMarked())) {
            return -2;
        } else {
            list[id - 1].markTask(isDone);
            return id;
        }
    }

    public void printList() {
        for (int i = 0; i < numCurrentItem; i++) {
            list[i].printTask();
        }
    }

    public void printTask(Integer id) {
        list[id - 1].printTask();
    }
}
