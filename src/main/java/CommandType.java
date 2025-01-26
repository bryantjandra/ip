public enum CommandType {
    LIST,
    BYE,
    MARK,
    UNKNOWN,
    DEADLINE,
    TODO,
    DELETE,
    EVENT,
    UNMARK;

    public static CommandType parseCommand(String input) {
        if (input == null) {
            return UNKNOWN;
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
            default:
                return UNKNOWN;
        }
    }
}
