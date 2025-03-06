package inuChan;

import java.io.IOException;
import java.util.Scanner;

import inuChan.command.Parser;
import inuChan.command.commandexceptions.InvalidArgument;
import inuChan.command.commandexceptions.InvalidArgumentCount;
import inuChan.command.commandexceptions.InvalidCommand;
import inuChan.tasks.Deadline;
import inuChan.tasks.Event;
import inuChan.tasks.Task;
import inuChan.tasks.ToDo;

public class InuChan {
    private static final String MARKED = "X";

    private TaskList taskList;
    private Storage storage;
    private Ui ui;
    private boolean isEnded = false;

    public static void main(String[] args) {
        new InuChan().run();
    }

    public InuChan() {
        taskList = new TaskList();
        storage = new Storage();
        ui = new Ui();
        initializeData();
    }

    /**
     *  Read the task list data from file.
     *  If there is no file, create one.
     */
    private void initializeData() {
        try {
            taskList = storage.load();
        } catch (IOException e) {
            ui.showInuSpeak("Something's wrong, AWO!", true);
            ui.say("File reading went wrong!");
        } catch (RuntimeException e) {
            ui.showInuSpeak("File corrupted, AWO!", true);
            ui.say("Incorrect file format!");
        }
    }

    public void run() {
        ui.greet();

        Scanner input = new Scanner(System.in);
        while (!isEnded) {
            String command = input.nextLine();
            try {
                handleCommand(command);
            } catch (InvalidArgumentCount e) {
                ui.showInuSpeak("I don't understand, AWOO!", true);
                ui.say("Incorrect number of arguments!");
            } catch (InvalidArgument | NumberFormatException e) {
                ui.showInuSpeak("I don't understand, AWOO!", true);
                ui.say("Illegal command argument!");
            } catch (InvalidCommand e) {
                ui.showInuSpeak("I don't understand, AWOO!", true);
                ui.say("Command does not exist!");
            }
        }

        ui.sayBye();
    }

    /**
     * Handle the input command.
     * Tokenize the command and call the appropriate method.
     *
     * @param command The input command.
     * @throws InvalidCommand If there is no such command.
     * @throws InvalidArgument If there is an invalid argument.
     * @throws InvalidArgumentCount If there is an invalid number of arguments.
     */
    private void handleCommand(String command) throws InvalidCommand, InvalidArgument, InvalidArgumentCount {
        try {
            String[] tokenizedCommand = Parser.tokenize(command);

            switch (tokenizedCommand[0]) {
                case "bye", "quit", "exit" -> isEnded = true;
                case "list" -> ui.printList(taskList);
                case "mark" -> markTask(Integer.parseInt(tokenizedCommand[1]), true);
                case "unmark" -> markTask(Integer.parseInt(tokenizedCommand[1]), false);
                case "delete", "remove" -> deleteTask(Integer.parseInt(tokenizedCommand[1]));
                case "todo" -> addToDo(tokenizedCommand[1]);
                case "deadline" -> addDeadline(tokenizedCommand[1], tokenizedCommand[2]);
                case "event" -> addEvent(tokenizedCommand[1], tokenizedCommand[2], tokenizedCommand[3]);
                case "find" -> find(tokenizedCommand[1]);
                default -> throw new InvalidCommand();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidArgumentCount();
        }
    }

    /**
     * Mark the task of the given index as the required state.
     *
     * @param index The index of the task.
     * @param isMarked The required state of the task.
     */
    private void markTask(Integer index, boolean isMarked) {
        try {
            int markResult = taskList.markTask(index, isMarked);
            ui.printMarkTaskResult(taskList, index, isMarked, markResult);
            writeData();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            ui.printMarkTaskError(index, isMarked);
        }
    }

    private void writeData() {
        try {
            storage.writeData(taskList);
        } catch (IOException e) {
            ui.printWriteDataError();
        }
    }

    /**
     * Delete the task of the given index.
     * Then save the data.
     * Print the task deleted or error message if the index is invalid.
     *
     * @param index The index of the task.
     */
    private void deleteTask(int index) {
        try {
            Task task = taskList.getTask(index);
            taskList.deleteTask(index);
            ui.printDeleteTask(task);
            writeData();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            ui.printDeleteTaskError(index);
        }
    }

    /**
     * Add a to-do task to the list.
     * Then save the data.
     *
     * @param name Name of the to-do task.
     */
    private void addToDo(String name) {
        ui.printAddTaskResult(taskList, taskList.addTask(new ToDo(name)));
        writeData();
    }

    /**
     * Add a deadline task to the list.
     * Then save the data.
     *
     * @param name Name of the deadline task.
     * @param by When the deadline is due.
     */
    private void addDeadline(String name, String by) {
        ui.printAddTaskResult(taskList, taskList.addTask(new Deadline(name, by)));
        writeData();
    }

    /**
     * Add an event task to the list.
     * Then save the data.
     *
     * @param name Name of the event task.
     * @param from When the event starts.
     * @param to When the event ends.
     */
    private void addEvent(String name, String from, String to) {
        ui.printAddTaskResult(taskList, taskList.addTask(new Event(name, from, to)));
        writeData();
    }

    /**
     * Find and print all the tasks that contain target.
     *
     * @param target Keyword to be found in tasks.
     */
    private void find(String target) {
        TaskList tasksFound = taskList.find(target);
        ui.printTasksFound(tasksFound);
    }
}
