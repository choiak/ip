public class InuChan {
    private static TaskList taskList = new TaskList();

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
            showInuSpeak("The list is full, WOOF!", true);
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
            showInuSpeak("It's empty, WOOF!", true);
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
        Integer markResult = taskList.markTask(index, isMarked);
        if (markResult == -1) {
            showInuSpeak("It doesn't exist, WOOF!", true);
            say("Unable to " + (isMarked ? "mark" : "unmark") + " item " + index + " as it does not exist");
        } else if (markResult == -2) {
            showInuSpeak("It's " + (isMarked ? "marked" : "unmarked") +
                    " already, WOOF!", true);
            say("The following item is already " + (isMarked ? "marked" : "unmarked"));
            System.out.println("\t" + taskList.getTask(index));
        } else {
            showInuSpeak((isMarked ? "Marked" : "Unmarked") + ", WOOF!", false);
            say((isMarked ? "Marked" : "Unmarked") + " the following item");
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
    public static String[] tokenize(String command) {
        String[] spaceSeparatedToken = command.split(" ");
        String[] slashSeparatedToken = command.split("/");
        if (spaceSeparatedToken.length == 1) {
            return new String[] {spaceSeparatedToken[0]};
        } else if (spaceSeparatedToken.length == 2 &&
                (spaceSeparatedToken[0].equals("mark") || spaceSeparatedToken[0].equals("unmark"))) {
            return new String[] {spaceSeparatedToken[0], spaceSeparatedToken[1]};
        } else if (spaceSeparatedToken[0].equals("todo")) {
            return new String[] {spaceSeparatedToken[0], command.substring(4).strip()};
        } else if (spaceSeparatedToken[0].equals("deadline")) {
            return new String[] {spaceSeparatedToken[0],
                    slashSeparatedToken[0].substring(8).strip(),
                    slashSeparatedToken[1].substring(2).strip()};
        } else if (spaceSeparatedToken[0].equals("event")) {
            return new String[] {spaceSeparatedToken[0],
                    slashSeparatedToken[0].substring(6).strip(),
                    slashSeparatedToken[1].substring(4).strip(),
                    slashSeparatedToken[2].substring(2).strip()};
        } else {
            return new String[] {command};
        }
    }


    public static void main(String[] args) {
        greet();
        boolean isEnded = false;
        while (!isEnded) {
            String command = new java.util.Scanner(System.in).nextLine();
            String[] tokenizedCommand = tokenize(command.strip());
            switch (tokenizedCommand[0]) {
            case "bye":
                isEnded = true;
                break;
            case "list":
                printList();
                break;
            case "mark":
                markTask(Integer.parseInt(tokenizedCommand[1]), true);
                break;
            case "unmark":
                markTask(Integer.parseInt(tokenizedCommand[1]), false);
                break;
            case "todo":
                addToDo(tokenizedCommand[1]);
                break;
            case "deadline":
                addDeadline(tokenizedCommand[1], tokenizedCommand[2]);
                break;
            case "event":
                addEvent(tokenizedCommand[1], tokenizedCommand[2], tokenizedCommand[3]);
                break;
            default:
                echo(command);
            }
        }
        sayBye();
    }
}
