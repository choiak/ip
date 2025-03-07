package inuChan.command;

import inuChan.command.commandexceptions.InvalidArgument;
import inuChan.command.commandexceptions.InvalidArgumentCount;
import inuChan.command.commandexceptions.InvalidCommand;

public class Parser {
    private static final String SPLITTER = "]|[";

    /**
     * Tokenize the input command.
     * Return an array of tokens.
     * Index 0 is the command keyword itself, and the rest are arguments.
     *
     * @param command The input command.
     * @return Array of tokens and its respective arguments.
     * @throws InvalidCommand If there is no such command.
     * @throws InvalidArgument If there is an invalid argument.
     * @throws InvalidArgumentCount If there is an invalid number of arguments.
     */
    public static String[] tokenize(String command) throws InvalidCommand, InvalidArgument, InvalidArgumentCount {
        if (command.contains(SPLITTER)) {
            throw new InvalidArgument();
        }

        String firstWord = command.split(" +")[0];

        return switch (firstWord) {
            case "find", "mark", "unmark", "delete", "remove" -> tokenizeSingleArgCommand(command, firstWord);
            case "todo" -> tokenizeTodo(command);
            case "deadline" -> tokenizeDeadline(command);
            case "event" -> tokenizeEvent(command);
            case "bye", "quit", "exit", "goodbye", "list" -> tokenizeByeList(command, firstWord);
            default -> throw new InvalidCommand();
        };
    }

    /**
     * Tokenize commands with single argument.
     *
     * @param command The input command.
     * @param firstWord The command keyword.
     * @return Array with the command keyword and the argument.
     * @throws InvalidArgumentCount If number of arguments is incorrect.
     */
    private static String[] tokenizeSingleArgCommand(String command, String firstWord) throws InvalidArgumentCount {
        String[] spaceSeparatedToken = command.split(" +");
        int wordCount = spaceSeparatedToken.length;
        if (wordCount != 2) {
            throw new InvalidArgumentCount();
        }
        return new String[]{firstWord, spaceSeparatedToken[1]};
    }

    /**
     * Tokenize commands according to the to-do command pattern.
     *
     * @param command Input command.
     * @return Array with the command keyword and arguments.
     * @throws InvalidArgument If invalid argument is provided.
     * @throws InvalidArgumentCount If number of arguments is incorrect.
     */
    private static String[] tokenizeTodo(String command) throws InvalidArgument, InvalidArgumentCount {
        if (command.contains("/by") || command.contains("/from") || command.contains("/to")) {
            throw new InvalidArgument();
        }

        String todoName = command.substring("todo".length()).strip();
        if (todoName.isEmpty()) {
            throw new InvalidArgumentCount();
        }

        return new String[]{"todo", todoName};
    }

    /**
     * Tokenize commands according to the deadline command pattern.
     *
     * @param command Input command.
     * @return Array with the command keyword and arguments.
     * @throws InvalidArgument If invalid argument is provided.
     * @throws InvalidArgumentCount If number of arguments is incorrect.
     */
    private static String[] tokenizeDeadline(String command) throws InvalidArgument, InvalidArgumentCount {
        int argumentCount = (command + "dummy").split("/by|/from|/to").length - 1;
        if (command.contains("/from") || command.contains("/to")) {
            throw new InvalidArgument();
        } else if (argumentCount != 1) {
            throw new InvalidArgumentCount();
        } else {
            String deadlineName = command.split("/by")[0].substring("deadline".length()).strip();
            String argumentBy = command.split("/by")[1].strip();

            if (deadlineName.isEmpty()) {
                throw new InvalidArgumentCount();
            }

            return new String[]{"deadline",
                    deadlineName,
                    argumentBy};
        }
    }

    /**
     * Tokenize commands according to the event command pattern.
     *
     * @param command Input command.
     * @return Array with the command keyword and arguments.
     * @throws InvalidArgument If invalid argument is provided.
     * @throws InvalidArgumentCount If number of arguments is incorrect.
     */
    private static String[] tokenizeEvent(String command) throws InvalidArgument, InvalidArgumentCount {
        int argumentCount = (command + "DUMMY_TEXT").split("/by|/from|/to").length - 1;

        if (command.contains("/by")) {
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
                throw new InvalidArgumentCount();
            }

            return new String[]{"event",
                    eventName,
                    argumentFrom,
                    argumentTo};
        }
    }

    /**
     * Tokenize list and bye (i.e. exit) commands.
     * Any words that are not "list" will be treated as "bye".
     *
     * @param command Input command.
     * @param firstWord The command keyword.
     * @return Array with the corresponding command keyword.
     * @throws InvalidArgumentCount If number of arguments is incorrect.
     */
    private static String[] tokenizeByeList(String command, String firstWord) throws InvalidArgumentCount {
        if (command.split(" +").length > 1) {
            throw new InvalidArgumentCount();
        }
        return new String[]{(firstWord.equals("list")) ? "list" : "bye"};
    }
}
