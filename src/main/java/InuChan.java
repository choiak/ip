public class InuChan {
    static String inuHappy =
            """
                        __    __
                        \\/----\\/
                         \\^  ^/    WOOF!
                         _\\  /_
                       _|  \\/  |_
                      | | |  | | |
                     _| | |  | | |_
                    "---|_|--|_|---"
                    
                    """;
    static String inuSad =
            """
                        __    __
                        \\/----\\/
                         \\Q  Q/    AWOO!
                         _\\  /_
                       _|  \\/  |_
                      | | |  | | |
                     _| | |  | | |_
                    "---|_|--|_|---"
                    
                    """;

    static void greet() {
        System.out.println(inuHappy + "Hi there! I'm Inu-chan, woof!");
        ask("How can I help you");
    }

    static void sayBye() {
        System.out.println(inuSad + "Woof! I will be waiting you! See you, awooooo!");
    }

    /**
     * Print the question being asked with modification to fit Inu-chan's character.
     *
     * @param question the actual question being asked (without any ending punctuation like question mark '?').
     */
    static void ask(String question) {
        System.out.println(question + ", arf arf?");
    }

    public static void main(String[] args) {
        greet();
        sayBye();
    }
}
