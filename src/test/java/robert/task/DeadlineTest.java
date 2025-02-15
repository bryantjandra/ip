package robert.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests the Deadline class.
 */
public class DeadlineTest {

    @Test
    public void constructor_validDate_success() {
        Deadline d = new Deadline("Finish project", "2025-01-10");
        assertEquals(LocalDate.of(2025, 1, 10), d.getByDate());
    }

    @Test
    public void toString_showsCorrectFormat() {
        Deadline d = new Deadline("Submit report", "2024-12-31");
        String expected = "[D][ ] Submit report (by: Dec 31 2024)";
        assertEquals(expected, d.toString());
    }
}
