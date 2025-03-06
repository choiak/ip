package inuChan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private static TaskList taskList = new TaskList();
    private static boolean isEnded = false;
    private static final String MARKED = "X";

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
    public static void showInuSpeak(String line, Boolean isSad) {
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
    public static void greet() {
        showInuSpeak("WOOF! WOOF!", false);
        System.out.println("Hi there! I'm Inu-chan, woof!");
        ask("How can I help you");
    }

    /**
     * Print goodbye messages when the program ends.
     */
    public static void sayBye() {
        showInuSpeak("AWOO!", true);
        System.out.println("Woof! I will be waiting you! See you, awooooo!");
    }

    /**
     * Print the question being asked with modification to fit Inu-chan's character.
     *
     * @param question the actual question being asked (without any ending punctuation like question mark '?').
     */
    public static void ask(String question) {
        System.out.println(question + ", arf arf?");
    }

    /**
     * Print the line being said with modification to fit Inu-chan's character.
     *
     * @param line the actual line being said (without any ending punctuation like period '.').
     */
    public static void say(String line) {
        System.out.println(line + ", ruff!");
    }

    public static void printAddTaskResult(Integer result) {
        if (result == -1) {
            showInuSpeak("The list is full, AWO!", true);
            say("Unable to add the given item to the list as the list is full");
        } else {
            showInuSpeak("Task added, WOOF!", false);
            say("Added the following task as task " + result);
            System.out.println("\t" + taskList.getTask(result));
        }
    }

    /**
     * Add a to-do task to the list.
     * Then save the data.
     *
     * @param name Name of the to-do task.
     */
    public static void addToDo(String name) {
        printAddTaskResult(taskList.addTask(new ToDo(name)));
        writeData();
    }

    /**
     * Add a deadline task to the list.
     * Then save the data.
     *
     * @param name Name of the deadline task.
     * @param by When the deadline is due.
     */
    public static void addDeadline(String name, String by) {
        printAddTaskResult(taskList.addTask(new Deadline(name, by)));
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
    public static void addEvent(String name, String from, String to) {
        printAddTaskResult(taskList.addTask(new Event(name, from, to)));
        writeData();
    }

    public static void printList() {
        if (taskList.getTaskCount() == 0) {
            showInuSpeak("It's empty, AWO!", true);
            say("The list is empty");
        } else {
            showInuSpeak("The list, WOOF!", false);
            say("Here's the list");
            System.out.println(taskList);
        }
    }

    /**
     * Mark the task of the given index as the required state.
     *
     * @param index The index of the task.
     * @param isMarked The required state of the task.
     */
    public static void markTask(Integer index, boolean isMarked) {
        final int SAME_STATE = -1;
        try {
            int markResult = taskList.markTask(index, isMarked);
            if (markResult == SAME_STATE) {
                showInuSpeak("It's " + (isMarked ? "marked" : "unmarked") +
                        " already, AWO!", true);
                say("The following item is already " + (isMarked ? "marked" : "unmarked"));
                System.out.println("\t" + taskList.getTask(index));
            } else {
                showInuSpeak((isMarked ? "Marked" : "Unmarked") + ", WOOF!", false);
                say((isMarked ? "Marked" : "Unmarked") + " the following item");
                System.out.println("\t" + taskList.getTask(index));
                writeData();
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            showInuSpeak("It doesn't exist, AWO!", true);
            say("Unable to " + (isMarked ? "mark" : "unmark") + " item " + index + " as it does not exist");
        }
    }

    /**
     * Delete the task of the given index.
     * Then save the data.
     * Print the task deleted or error message if the index is invalid.
     *
     * @param index The index of the task.
     */
    public static void deleteTask(int index) {
        try {
            Task task = taskList.getTask(index);

            taskList.deleteTask(index);
            writeData();

            showInuSpeak("deleted, WOOF!", false);
            say("deleted the following item");
            System.out.println("\t" + task);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            showInuSpeak("It doesn't exist, AWO!", true);
            say("Unable to delete item " + index + " as it does not exist");
        }
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
    public static void handleCommand(String command) throws InvalidCommand, InvalidArgument, InvalidArgumentCount {
        try {
            String[] tokenizedCommand = Parser.tokenize(command);

            switch (tokenizedCommand[0]) {
                case "bye", "quit", "exit" -> isEnded = true;
                case "list" -> printList();
                case "mark" -> markTask(Integer.parseInt(tokenizedCommand[1]), true);
                case "unmark" -> markTask(Integer.parseInt(tokenizedCommand[1]), false);
                case "delete", "remove" -> deleteTask(Integer.parseInt(tokenizedCommand[1]));
                case "todo" -> addToDo(tokenizedCommand[1]);
                case "deadline" -> addDeadline(tokenizedCommand[1], tokenizedCommand[2]);
                case "event" -> addEvent(tokenizedCommand[1], tokenizedCommand[2], tokenizedCommand[3]);
                default -> throw new InvalidCommand();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidArgumentCount();
        }
    }

    /**
     *  Read the task list data from file.
     *  If there is no file, create one.
     */
    public static void readData() {
        try {
            Files.createDirectories(Paths.get("./data"));
            File file = new File("./data/InuChan.txt");
            file.createNewFile();

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = getTaskFromLine(line);
                taskList.addTask(task);
            }
        } catch (IOException e) {
            showInuSpeak("Something's wrong, AWO!", true);
            say("File reading went wrong!");
        } catch (RuntimeException e) {
            showInuSpeak("File corrupted, AWO!", true);
            say("Incorrect file format!");
        }
    }

    /**
     * Read the input line and return a Task object.
     *
     * @param line The input line.
     * @return Task object.
     * @throws RuntimeException If the input line is not in the correct format.
     */
    private static Task getTaskFromLine(String line) throws RuntimeException {
        // Split the line into tokens separated by SPLITTER "]\|["
        String[] tokens = line.split("]\\|\\[");

        Task task = switch (tokens[0]) {
            case "T" -> new ToDo(tokens[2]);
            case "D" -> new Deadline(tokens[2], tokens[3]);
            case "E" -> new Event(tokens[2], tokens[3], tokens[4]);
            default -> throw new RuntimeException();
        };

        if (tokens[1].equals(MARKED)) {
            task.markTask(true);
        }
        return task;
    }

    public static void writeData() {
        try {
            FileWriter fileWriter = new FileWriter("./data/InuChan.txt");
            taskList.writeData(fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            showInuSpeak("Something's wrong, AWO!", true);
            say("File writing went wrong!");
        }
    }

    public static void main(String[] args) {
        greet();
        readData();

        Scanner input = new Scanner(System.in);
        while (!isEnded) {
            String command = input.nextLine();
            try {
                handleCommand(command);
            } catch (InvalidArgumentCount e) {
                showInuSpeak("I don't understand, AWOO!", true);
                say("Incorrect number of arguments!");
            } catch (InvalidArgument | NumberFormatException e) {
                showInuSpeak("I don't understand, AWOO!", true);
                say("Illegal command argument!");
            } catch (InvalidCommand e) {
                showInuSpeak("I don't understand, AWOO!", true);
                say("Command does not exist!");
            }
        }

        sayBye();
    }
}
