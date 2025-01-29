public class ToDoList {
    private final Integer capacity;
    private Task[] list;
    private Integer numCurrentItem;

    public ToDoList() {
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
     * @return task id, -1 if the list is full.
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

    public void markTaskDone(Integer id) {
        list[id - 1].markAsDone();
    }

    public void markTaskNotDone(Integer id) {
        list[id - 1].markAsNotDone();
    }

    public void printList() {
        for (int i = 0; i < numCurrentItem; i++) {
            list[i].printTask();
        }
    }
}
