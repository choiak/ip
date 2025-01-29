public class InuChan {
    private static ToDoList toDoList = new ToDoList();

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
    static void showInuSpeak(String line, Boolean isSad) {
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

    static void greet() {
        showInuSpeak("WOOF! WOOF!", false);
        System.out.println("Hi there! I'm Inu-chan, woof!");
        ask("How can I help you");
    }

    static void sayBye() {
        showInuSpeak("AWOO!", true);
        System.out.println("Woof! I will be waiting you! See you, awooooo!");
    }

    /**
     * Print the question being asked with modification to fit Inu-chan's character.
     *
     * @param question the actual question being asked (without any ending punctuation like question mark '?').
     */
    static void ask(String question) {
        System.out.println(question + ", arf arf?");
    }

    /**
     * Print the line being said with modification to fit Inu-chan's character.
     *
     * @param line the actual line being said (without any ending punctuation like period '.').
     */
    static void say(String line) {
        System.out.println(line + ", ruff!");
    }

    static void echo(String line) {
        showInuSpeak(line, false);
    }

    static void addToList(String line) {
        Integer taskId = toDoList.addTask(line);
        if (taskId == -1) {
            showInuSpeak("The list is full, WOOF!", true);
            say("Unable to add the given item to the list as the list is full");
        } else {
            showInuSpeak("Item added, WOOF!", false);
            say("Added the following item as item " + taskId);
            System.out.println(line + "\n");
        }
    }

    static void printList() {
        showInuSpeak("The list, WOOF!", false);
        say("Here's the list");
        toDoList.printList();
    }

    static void markTask(Integer id, boolean isDone) {
        Integer markResult = toDoList.markTask(id, isDone);
        if (markResult == -1) {
            showInuSpeak("It doesn't exist, WOOF!", true);
            say("Unable to " + (isDone ? "mark" : "unmark") + " item " + id + " as it does not exist");
        } else if (markResult == -2) {
            showInuSpeak("It's " + (isDone ? "marked" : "unmarked") +
                    " already, WOOF!", true);
            say("The following item is already " + (isDone ? "marked" : "unmarked"));
            toDoList.printTask(id);
        } else {
            showInuSpeak((isDone ? "Marked" : "Unmarked") + ", WOOF!", false);
            say((isDone ? "Marked" : "Unmarked") + " the following item");
            toDoList.printTask(id);
        }
    }

    public static void main(String[] args) {
        greet();
        while (true) {
            String command = new java.util.Scanner(System.in).nextLine();
            String[] tokenizedCommand = command.split(" ");
            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                printList();
            } else if (tokenizedCommand.length == 2 && tokenizedCommand[0].equals("mark")) {
                markTask(Integer.parseInt(tokenizedCommand[1]), true);
            } else if (tokenizedCommand.length == 2 && tokenizedCommand[0].equals("unmark")) {
                markTask(Integer.parseInt(tokenizedCommand[1]), false);
            } else {
                addToList(command);
            }
        }
        sayBye();
    }
}
