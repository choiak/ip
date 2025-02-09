package inuChan;

import java.util.ArrayList;

import inuChan.tasks.Task;

public class TaskList {
    private ArrayList<Task> tasks;
    private Integer taskCount;

    public TaskList() {
        tasks = new ArrayList<>();
        taskCount = 0;
    }

    public Task getTask(Integer index) {
        return tasks.get(index - 1);
    }

    public Integer getTaskCount() {
        return tasks.size();
    }

    /**
     * Add a new task to the list.
     * Return the task count.
     *
     * @param task The task to be added.
     * @return Task count.
     */
    public Integer addTask(Task task) {
        tasks.add(task);
        return tasks.size();
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
        final boolean IS_BOTH_MARKED = isMarked && tasks.get(index - 1).getIsMarked();
        final boolean IS_BOTH_UNMARKED = !isMarked && !tasks.get(index - 1).getIsMarked();
        if (IS_BOTH_MARKED || IS_BOTH_UNMARKED) {
            return SAME_STATE;
        } else {
            tasks.get(index - 1).markTask(isMarked);
            return index;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < taskCount; i++) {
            result += (i + 1) + ". " + tasks.get(i) + "\n";
        }
        return result.trim();
    }
}
