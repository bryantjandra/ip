package robert.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import robert.command.CommandType;

/**
 * Tests the Parser class.
 */
public class ParserTest {

    /**
     * Tests if parse("") returns CommandType.UNKNOWN.
     */
    @Test
    public void parse_emptyString_returnUnknown() {
        CommandType result = Parser.parse("");
        assertEquals(CommandType.UNKNOWN, result);
    }

    /**
     * Tests if parse("todo read book") → CommandType.TODO.
     */
    @Test
    public void parse_todoCommand_returnTodo() {
        CommandType result = Parser.parse("todo read book");
        assertEquals(CommandType.TODO, result);
    }

    /**
     * Example: parse("bye") → BYE.
     */
    @Test
    public void parse_byeCommand_returnBye() {
        CommandType result = Parser.parse("bye");
        assertEquals(CommandType.BYE, result);
    }

    /**
     * Tests that parse does not return null even for random strings.
     */
    @Test
    public void parse_randomInput_returnNotNull() {
        CommandType result = Parser.parse("randomstuff");
        assertNotNull(result);
    }
}
