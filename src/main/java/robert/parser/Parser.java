package robert.parser;
import robert.command.CommandType;

public class Parser {
    public static CommandType parse(String fullCommand) {
        if (fullCommand.trim().isEmpty()) {
            return CommandType.UNKNOWN;
        }
        String[] parts = fullCommand.split(" ", 2);
        return CommandType.parseCommand(parts[0]);
    }
}
