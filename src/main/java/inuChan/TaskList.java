package inuChan;

import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;

import inuChan.tasks.Task;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
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
     * Return SAME_STATE if the task is already in the required state.
     *
     * @param index The index of the task.
     * @param isMarked The required state of the task.
     * @return Task index. Or SAME_STATE if the task is already in the required state.
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

    public void deleteTask(int index) {
        tasks.remove(index - 1);
    }

    public void writeData(FileWriter fileWriter) throws IOException {
        for (Task task : tasks) {
            task.writeData(fileWriter);
            fileWriter.write("\n");
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            result += (i + 1) + ". " + tasks.get(i) + "\n";
        }
        return result.trim();
    }
}
