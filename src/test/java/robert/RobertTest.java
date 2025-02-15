package robert;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the main Robert chatbot logic (excluding GUI).
 */
public class RobertTest {

    private Robert robert;

    @BeforeEach
    public void setUp() {
        robert = new Robert("data/testTasks.txt");
    }

    @Test
    public void getResponse_todoCommand_validTaskAdded() {
        String response = robert.getResponse("todo read book");
        assertTrue(response.contains("Certainly, sir. I have added this task:"),
                "Expected polite add-task message in response");
        assertTrue(response.contains("read book"),
                "Should mention the newly added task in the response");
    }

    @Test
    public void getResponse_todoWithoutDescription_showsError() {
        String response = robert.getResponse("todo");
        assertTrue(response.contains("The description of a todo cannot be empty."),
                "Should show an error about empty description.");
    }

    @Test
    public void getResponse_deadlineWithValidDate_showsSuccess() {
        String response = robert.getResponse("deadline return book /by 2025-01-01");
        assertTrue(response.contains("Certainly, sir. I have added this task:"),
                "Expected success message for valid deadline");
        assertTrue(response.contains("return book"),
                "Response should mention the new deadline's description");
    }

    @Test
    public void getResponse_deadlineWithInvalidDate_showsDateError() {
        String response = robert.getResponse("deadline return book /by 2025-02-30");
        assertTrue(response.contains("Sir, the specified deadline date is invalid."),
                "Should detect invalid date (Feb 30) and show an error.");
    }

    @Test
    public void getResponse_unknownCommand_showsError() {
        String response = robert.getResponse("gibberish");
        assertTrue(response.contains("I am afraid I did not understand that command."),
                "Should show unknown command error");
    }
}
