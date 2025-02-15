package robert.command;

/**
 * Represents all the recognized command types in the Robert chatbot.
 */
public enum CommandType {
    LIST,
    BYE,
    MARK,
    UNKNOWN,
    DEADLINE,
    TODO,
    DELETE,
    EVENT,
    EMPTY,
    FIND,
    UNMARK,
    SORT;

    /**
     * Parses the given input string and returns the matching CommandType.
     *
     * @param input The command word (e.g., "todo", "list", "event").
     * @return The corresponding CommandType, or EMPTY if blank, or UNKNOWN if unrecognized.
     */
    public static CommandType parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return EMPTY;
        }
        switch (input.toLowerCase()) {
        case "bye":
            return BYE;
        case "list":
            return LIST;
        case "mark":
            return MARK;
        case "unmark":
            return UNMARK;
        case "delete":
            return DELETE;
        case "todo":
            return TODO;
        case "deadline":
            return DEADLINE;
        case "event":
            return EVENT;
        case "find":
            return FIND;
        case "sort":
            return SORT;
        default:
            return UNKNOWN;
        }
    }
}
