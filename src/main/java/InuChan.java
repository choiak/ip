public class InuChan {
    private static String[] list = new String[100];
    private static int numItems = 0;

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
        list[numItems] = line;
        numItems++;
        showInuSpeak("Item added, WOOF!", false);
        say("Added the following item");
        System.out.println(line + "\n");
    }

    static void printList() {
        showInuSpeak("The list, WOOF!", false);
        say("Here's the list");
        for (int i = 0; i < numItems; i++) {
            System.out.println(i + 1 + ". " + list[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        greet();
        while (true) {
            String command = new java.util.Scanner(System.in).nextLine();
            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                printList();
            } else {
                addToList(command);
            }
        }
        sayBye();
    }
}
