public class InuChan {
    private static TaskList taskList = new TaskList();
    private static boolean isEnded = false;

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

    public static void greet() {
        showInuSpeak("WOOF! WOOF!", false);
        System.out.println("Hi there! I'm Inu-chan, woof!");
        ask("How can I help you");
    }

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

    public static void echo(String line) {
        showInuSpeak(line, false);
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
     *
     * @param name Name of the to-do task.
     */
    public static void addToDo(String name) {
        printAddTaskResult(taskList.addTask(new ToDo(name)));
    }

    /**
     * Add a deadline task to the list.
     *
     * @param name Name of the deadline task.
     * @param by When the deadline is due.
     */
    public static void addDeadline(String name, String by) {
        printAddTaskResult(taskList.addTask(new Deadline(name, by)));
    }

    /**
     * Add an event task to the list.
     *
     * @param name Name of the event task.
     * @param from When the event starts.
     * @param to When the event ends.
     */
    public static void addEvent(String name, String from, String to) {
        printAddTaskResult(taskList.addTask(new Event(name, from, to)));
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
     * @param isDone The required state of the task.
     */
    public static void markTask(Integer index, boolean isDone) {
        Integer markResult = taskList.markTask(index, isDone);
        if (markResult == -1) {
            showInuSpeak("It doesn't exist, WOOF!", true);
            say("Unable to " + (isDone ? "mark" : "unmark") + " item " + index + " as it does not exist");
        } else if (markResult == -2) {
            showInuSpeak("It's " + (isDone ? "marked" : "unmarked") +
                    " already, WOOF!", true);
            say("The following item is already " + (isDone ? "marked" : "unmarked"));
            System.out.println("\t" + taskList.getTask(index));
        } else {
            showInuSpeak((isDone ? "Marked" : "Unmarked") + ", WOOF!", false);
            say((isDone ? "Marked" : "Unmarked") + " the following item");
            System.out.println("\t" + taskList.getTask(index));
        }
    }

    /**
     * Tokenize the input command.
     * Return an array of tokens.
     * Index 0 is the command itself, and the rest are arguments.
     *
     * @param command The input command.
     * @return Array of tokens and its respective arguments.
     */
    public static String[] tokenize(String command) throws InvalidCommand, InvalidArgument, InvalidArgumentCount {
        String[] spaceSeparatedToken = command.split(" +");

        String firstWord = spaceSeparatedToken[0];
        int wordCount = spaceSeparatedToken.length;

        boolean hasArgumentBy = command.contains("/by");
        boolean hasArgumentFrom = command.contains("/from");
        boolean hasArgumentTo = command.contains("/to");
        int argumentCount = command.split("/by|/from|/to").length - 1;

        return switch (firstWord) {
            case "mark", "unmark" -> {
                if (wordCount != 2) {
                    throw new InvalidArgumentCount();
                }
                yield new String[]{firstWord, spaceSeparatedToken[1]};
            }
            case "todo" -> {
                if (hasArgumentBy || hasArgumentFrom || hasArgumentTo) {
                    throw new InvalidArgument();
                }
                yield new String[]{firstWord,
                        command.substring("todo".length()).strip()};
            }
            case "deadline" -> {
                if (hasArgumentFrom || hasArgumentTo) {
                    throw new InvalidArgument();
                } else if (argumentCount != 1) {
                    throw new InvalidArgumentCount();
                } else {
                    String[] arguments = command.split("/by");
                    String deadlineName = arguments[0].substring("deadline".length()).strip();
                    String argumentBy = arguments[1].strip();
                    if (deadlineName.isEmpty()) {
                        throw new InvalidArgument();
                    }
                    yield new String[]{firstWord,
                            deadlineName,
                            argumentBy};
                }
            }
            case "event" -> {
                if (hasArgumentBy) {
                    throw new InvalidArgument();
                } else if (argumentCount != 2) {
                    throw new InvalidArgumentCount();
                } else {
                    String eventName = command.split("/from")[0].substring("event".length()).strip();
                    String argumentFrom = command.split("/from")[1].strip();

                    String argumentTo;
                    if (argumentFrom.contains("/to")) {
                        argumentTo = argumentFrom.split("/to")[1].strip();
                        argumentFrom = argumentFrom.split("/to")[0].strip();
                    } else {
                        argumentTo = eventName.split("/to")[1].strip();
                        eventName = eventName.split("/to")[0].strip();
                    }

                    if (eventName.isEmpty()) {
                        throw new InvalidArgument();
                    }

                    yield new String[]{firstWord,
                            eventName,
                            argumentFrom,
                            argumentTo};
                }
            }
            case "bye", "list" -> {
                if (wordCount > 1) {
                    throw new InvalidArgumentCount();
                }
                yield new String[]{firstWord};
            }
            default -> throw new InvalidCommand();
        };
    }

    public static void handleCommand(String command) throws InvalidCommand, InvalidArgument, InvalidArgumentCount {
        String[] tokenizedCommand = tokenize(command);
        switch (tokenizedCommand[0]) {
            case "bye" -> isEnded = true;
            case "list" -> printList();
            case "mark" -> markTask(Integer.parseInt(tokenizedCommand[1]), true);
            case "unmark" -> markTask(Integer.parseInt(tokenizedCommand[1]), false);
            case "todo" -> addToDo(tokenizedCommand[1]);
            case "deadline" -> addDeadline(tokenizedCommand[1], tokenizedCommand[2]);
            case "event" -> addEvent(tokenizedCommand[1], tokenizedCommand[2], tokenizedCommand[3]);
            default -> echo(command);
        }
    }

    public static void main(String[] args) {
        greet();
        while (!isEnded) {
            String command = new java.util.Scanner(System.in).nextLine();
            try {
                handleCommand(command);
            } catch (InvalidArgumentCount | IndexOutOfBoundsException e) {
                showInuSpeak("I don't understand, AWOO!", true);
                say("Incorrect number of arguments!");
            } catch (InvalidArgument | NumberFormatException e) {
                showInuSpeak("I don't understand, AWOO!", true);
                say("Illegal command argument!");
            } catch (InvalidCommand e) {
                showInuSpeak("I don't understand, AWOO!", true);
                say("Illegal command!");
            }
        }
        sayBye();
    }
}
