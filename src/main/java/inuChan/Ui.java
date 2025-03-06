package inuChan;

import inuChan.tasks.Task;

public class Ui {
    /**
     * Print Inu-chan with the given line next to him like below
     *      __    __
     *      \/----\/
     *       \^  ^/    Line Here
     *       _\  /_
     *     _|  \/  |_
     *    | | |  | | |
     *   _| | |  | | |_
     *  "---|_|--|_|---"
     *
     * @param line the line being said
     * @param isSad whether Inu-chan is sad
     */
    public void showInuSpeak(String line, Boolean isSad) {
        String face = "";
        if (isSad) {
            face = "       \\Q  Q/    ";
        } else {
            face = "       \\^  ^/    ";
        }
        System.out.printf("""
                
                       __    __
                       \\/----\\/
                 %s%s
                        _\\  /_
                      _|  \\/  |_
                     | | |  | | |
                    _| | |  | | |_
                   "---|_|--|_|---"
                %n""",face, line);
    }

    /**
     * Print greeting messages when the program starts.
     */
    public void greet() {
        showInuSpeak("WOOF! WOOF!", false);
        System.out.println("Hi there! I'm Inu-chan, woof!");
        ask("How can I help you");
    }

    /**
     * Print goodbye messages when the program ends.
     */
    public void sayBye() {
        showInuSpeak("AWOO!", true);
        System.out.println("Woof! I will be waiting you! See you, awooooo!");
    }

    /**
     * Print the question being asked with modification to fit Inu-chan's character.
     *
     * @param question the actual question being asked (without any ending punctuation like question mark '?').
     */
    public void ask(String question) {
        System.out.println(question + ", arf arf?");
    }

    /**
     * Print the line being said with modification to fit Inu-chan's character.
     *
     * @param line the actual line being said (without any ending punctuation like period '.').
     */
    public void say(String line) {
        System.out.println(line + ", ruff!");
    }

    public void printAddTaskResult(TaskList taskList, int result) {
        final int TASKLIST_FULL = -1;
        if (result == TASKLIST_FULL) {
            showInuSpeak("The list is full, AWO!", true);
            say("Unable to add the given item to the list as the list is full");
        } else {
            showInuSpeak("Task added, WOOF!", false);
            say("Added the following task as task " + result);
            System.out.println("\t" + taskList.getTask(result));
        }
    }

    public void printMarkTaskResult(TaskList taskList, Integer index, boolean isMarked, int result) {
        final int SAME_STATE = -1;
        if (result == SAME_STATE) {
            showInuSpeak("It's " + (isMarked ? "marked" : "unmarked") +
                    " already, AWO!", true);
            say("The following item is already " + (isMarked ? "marked" : "unmarked"));
            System.out.println("\t" + taskList.getTask(index));
        } else {
            showInuSpeak((isMarked ? "Marked" : "Unmarked") + ", WOOF!", false);
            say((isMarked ? "Marked" : "Unmarked") + " the following item");
            System.out.println("\t" + taskList.getTask(index));
        }
    }

    public void printMarkTaskError(Integer index, boolean isMarked) {
        showInuSpeak("It doesn't exist, AWO!", true);
        say("Unable to " + (isMarked ? "mark" : "unmark") + " item " + index + " as it does not exist");
    }

    public void printDeleteTask(Task task) {
        showInuSpeak("deleted, WOOF!", false);
        say("deleted the following item");
        System.out.println("\t" + task);
    }

    public void printDeleteTaskError(Integer index) {
        showInuSpeak("It doesn't exist, AWO!", true);
        say("Unable to delete item " + index + " as it does not exist");
    }

    public void printWriteDataError() {
        showInuSpeak("Something's wrong, AWO!", true);
        say("File writing went wrong!");
    }

    public void printList(TaskList taskList) {
        if (taskList.getTaskCount() == 0) {
            showInuSpeak("It's empty, AWO!", true);
            say("The list is empty");
        } else {
            showInuSpeak("The list, WOOF!", false);
            say("Here's the list");
            System.out.println(taskList);
        }
    }

    public void printTasksFound(TaskList tasksFound, String target) {
        if (tasksFound.getTaskCount() == 0) {
            showInuSpeak("I can't find anything, AWO!", true);
            say("There is no matching task");
        } else {
            showInuSpeak("I found these, WOOF!", false);
            say("Here's the matching tasks");
            System.out.println(tasksFound);
        }
    }
}
