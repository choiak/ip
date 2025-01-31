public class TaskList {
    private final Integer capacity;
    private Task[] list;
    private Integer numCurrentItem;

    public TaskList() {
        capacity = 100;
        list = new Task[capacity];
        numCurrentItem = 0;
    }

    public Task getTask(Integer id) {
        return list[id - 1];
    }

    public Integer getNumCurrentItem() {
        return numCurrentItem;
    }

    /**
     * Add a new task to the list.
     * Return the task id.
     * Return -1 if the list is full.
     *
     * @param name The name of the task.
     * @return Task id, -1 if the list is full.
     */
    public Integer addTask(String name) {
        if (numCurrentItem < capacity) {
            list[numCurrentItem] = new Task(numCurrentItem + 1, name);
            numCurrentItem++;
            return numCurrentItem;
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
        if (id < 1 || id > numCurrentItem) {
            return -1;
        } else if ((isDone && list[id - 1].getIsDone()) || (!isDone && !list[id - 1].getIsDone())) {
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
