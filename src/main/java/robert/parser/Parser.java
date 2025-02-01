package robert.parser;

import robert.command.CommandType;

/**
 * Parses user commands into CommandType enums.
 */
public class Parser {
    /**
     * Parses a full command string and returns the corresponding CommandType.
     *
     * @param fullCommand The full user input string.
     * @return A CommandType enum representing the command.
     */
    public static CommandType parse(String fullCommand) {
        if (fullCommand.trim().isEmpty()) {
            return CommandType.UNKNOWN;
        }
        String[] parts = fullCommand.split(" ", 2);
        return CommandType.parseCommand(parts[0]);
    }
}
