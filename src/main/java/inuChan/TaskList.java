package inuChan;

import inuChan.tasks.Task;

public class TaskList {
    private final Integer capacity;
    private Task[] list;
    private Integer taskCount;

    public TaskList() {
        capacity = 100;
        list = new Task[capacity];
        taskCount = 0;
    }

    public Task getTask(Integer index) {
        return list[index - 1];
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
     * Mark the task of the given index as the required state.
     * Return the task index.
     * Return negative value if the index is invalid or the task is already in the required state.
     *
     * @param index The index of the task.
     * @param isMarked The required state of the task.
     * @return Task index, SAME_STATE if the task is already in the required state.
     */
    public int markTask(Integer index, Boolean isMarked) {
        final int SAME_STATE = -1;
        final boolean isBothMarked = isMarked && list[index - 1].getIsMarked();
        final boolean isBothNotMarked = !isMarked && !list[index - 1].getIsMarked();
        if (isBothMarked || isBothNotMarked) {
            return SAME_STATE;
        } else {
            list[index - 1].markTask(isMarked);
            return index;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < taskCount; i++) {
            result += (i + 1) + ". " + list[i] + "\n";
        }
        return result.trim();
    }
}
